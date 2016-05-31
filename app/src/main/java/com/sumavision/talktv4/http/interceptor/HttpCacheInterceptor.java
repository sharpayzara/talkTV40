package com.sumavision.talktv4.http.interceptor;

import com.jiongbull.jlog.JLog;
import com.sumavision.talktv4.BaseApp;
import com.sumavision.talktv4.dao.RxDao;
import com.sumavision.talktv4.http.SumaClient;
import com.sumavision.talktv4.http.SumaRetrofit;
import com.sumavision.talktv4.model.entity.HttpCache;
import com.sumavision.talktv4.util.NetworkUtil;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * 云端响应头拦截器，用来配置缓存策略
 * Dangerous interceptor that rewrites the server's cache-control header.
 */

public class HttpCacheInterceptor implements Interceptor {
    private static RxDao httpCache = new RxDao(BaseApp.getContext(), HttpCache.class);
    private static Map<String,String> map = new HashMap<String,String>();
    //设缓存有效期为两天
    public static final long CACHE_STALE_SEC = 60 * 60 * 24 * 2;
    //查询缓存的Cache-Control设置，为if-only-cache时只查询缓存而不会请求服务器，max-stale可以配合设置缓存失效时间
    public static final String CACHE_CONTROL_CACHE = "only-if-cached, max-stale=" + CACHE_STALE_SEC;
    //查询网络的Cache-Control设置，头部Cache-Control设为max-age=0时则不会使用缓存而请求服务器
    public static final String CACHE_CONTROL_NETWORK = "max-age=0";

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        if (!NetworkUtil.isConnectedByState(BaseApp.getContext())) {
            request = request.newBuilder()
                    .cacheControl(CacheControl.FORCE_CACHE).build();
            JLog.e("no network");
        }else{
            request =  setHeaderCache(request.url().toString(),request);
        }

        Response originalResponse = chain.proceed(request);
        if (NetworkUtil.isConnectedByState(BaseApp.getContext())) {
            //有网的时候读接口上的@Headers里的配置，你可以在这里进行统一的设置
            if(originalResponse.code() == 200){
                saveHeaderCache(request.url().toString(),originalResponse.header("Last-Modified"),originalResponse.header("ETag"));
            }
            String cacheControl = request.cacheControl().toString();
            return originalResponse.newBuilder()
                    .header("Cache-Control", cacheControl)
                    .removeHeader("Pragma").build();
        } else {
            return originalResponse.newBuilder().header("Cache-Control",
                    "public, only-if-cached," + CACHE_STALE_SEC)
                    .removeHeader("Pragma").build();
        }

    }

    private Request setHeaderCache(final String urlStr,Request request) {
        map.put("urlStr",urlStr);
        List<HttpCache> list = httpCache.queryByCondition(map);
        if(list.size() > 0){
            HttpCache cache = list.get(0);
            Request.Builder requestBuilder = request.newBuilder();
            requestBuilder.header("If-Modified-Since",cache.getIfModifiedSince()+"");
            requestBuilder.header("If-None-Match",cache.getIfNoneMatch()+"");
            return requestBuilder.build();
        }

        return request;
    }

    private void saveHeaderCache(final String urlStr, final String ifModifiedSince, final String ifNoneMatch) {
        Observable.just("").subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io()).subscribe(
                new Action1<String>() {
                    @Override
                    public void call(String s) {
                        map.clear();
                        map.put("urlStr",urlStr);
                        List<HttpCache> list = httpCache.queryByCondition(map);
                        if(list.size() > 0){
                            HttpCache cache = list.get(0);
                            cache.setIfNoneMatch(ifNoneMatch);
                            cache.setIfModifiedSince(ifModifiedSince);
                            httpCache.update(cache);
                        }else{
                            HttpCache cache = new HttpCache();
                            cache.setUrlStr(urlStr);
                            cache.setIfModifiedSince(ifModifiedSince);
                            cache.setIfNoneMatch(ifNoneMatch);
                            httpCache.insert(cache);
                        }
                    }
                }
        );
    }
}
