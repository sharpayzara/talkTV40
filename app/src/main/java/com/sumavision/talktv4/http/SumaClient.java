package com.sumavision.talktv4.http;

import android.util.Log;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jiongbull.jlog.JLog;
import com.sumavision.talktv4.BaseApp;
import com.sumavision.talktv4.http.interceptor.HttpCacheInterceptor;
import com.sumavision.talktv4.http.interceptor.LoggingInterceptor;
import com.sumavision.talktv4.model.entity.decor.BaseData;
import com.sumavision.talktv4.util.CryptTool;
import com.sumavision.talktv4.util.NetworkUtil;

import java.io.File;
import java.io.FilterInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.internal.DiskLruCache;
import okhttp3.internal.io.FileSystem;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 *  desc 获取retrofit实力
 *  @author  yangjh
 *  created at  16-5-23 上午10:50
 */
public class SumaClient<E extends BaseData>{
    public static final String HOST = "http://172.16.40.88:81";
    private static Object sumaRetrofit;
    protected static final Object monitor = new Object();
    private static Retrofit retrofit;
    public static OkHttpClient okHttpClient;
    private static File cacheFile;
    private SumaClient(){
    }

    static {
        initOkHttpClient();
        Gson gson = new GsonBuilder()
                .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                .create();
        retrofit = new Retrofit.Builder()
                .baseUrl(HOST)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();
    }

    private static void initOkHttpClient() {
        if (okHttpClient == null) {
            // 因为BaseUrl不同所以这里Retrofit不为静态，但是OkHttpClient配置是一样的,静态创建一次即可
            cacheFile = new File(BaseApp.getContext().getCacheDir(),
                    "HttpCache"); // 指定缓存路径
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); // 指定缓存大小100Mb
            // 云端响应头拦截器，用来配置缓存策略
            okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(15, TimeUnit.SECONDS)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .readTimeout(12,TimeUnit.SECONDS)
                    .retryOnConnectionFailure(false)
                    .addNetworkInterceptor(new StethoInterceptor())//chrome工具调试的中间件
                    .addInterceptor(new HttpCacheInterceptor())
                    .addInterceptor(new LoggingInterceptor())
                    //.addInterceptor(new HttpLoggingInterceptor())
                    .cache(cache)
                    .build();
        }
    }



    public static <T> T getRetrofitInstance(Class<T> cls) {
        synchronized (monitor) {
            if (sumaRetrofit == null) {
                sumaRetrofit = retrofit.create(cls);
            }
            return (T) sumaRetrofit;
        }
    }

    public static <T extends BaseData> Subscription subscribe(Callable<Observable<T>> callable, final Action1<T> action, final Action1<Throwable> throwableAction, final Class cls) {
        Subscription subscription = null;
        try {
            final Observable<T> observable = callable.call();
            subscription = observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(action ,new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            if(throwable.toString().contains("HTTP 304 Not Modified")){
                                Field[] fields = throwable.getClass().getDeclaredFields();
                                String url = "";
                                for(Field field : fields){
                                    field.setAccessible(true);
                                    if(field.getName().equalsIgnoreCase("response")){
                                        try {
                                            Response res = (Response) field.get(throwable);
                                            url = res.raw().request().url().toString();
                                            break;
                                        } catch (IllegalAccessException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                                T t = (T) new Gson().fromJson(getCacheContent(url),cls);
                                t.setCacheSource(true);
                                action.call(t);
                            }else{
                                throwableAction.call(throwable);
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subscription;
    }

    public static String getCacheContent(String url){
        String path = url;
        Scanner sc = null;
        try {
            sc = new Scanner(getFromCache(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
        StringBuilder str= new StringBuilder();
        String s;
        while(sc.hasNext() && (s=sc.nextLine())!=null) {
            str.append(s);
        }

        return str.toString();
    }

    public static String getCacheControl() {
        return NetworkUtil.isConnectedByState(BaseApp.getContext()) ? HttpCacheInterceptor.CACHE_CONTROL_NETWORK : HttpCacheInterceptor.CACHE_CONTROL_CACHE;
    }


    public static FilterInputStream getFromCache(String url) throws Exception {
        DiskLruCache cache = DiskLruCache.create(FileSystem.SYSTEM, cacheFile,
                201105, 2, 1024 * 1024 * 100);
        cache.flush();
        String key = CryptTool.md5Digest(url);
        final DiskLruCache.Snapshot snapshot;
        try {
            snapshot = cache.get(key);
            if (snapshot == null) {
                return null;
            }
        } catch (IOException e) {
            return null;
        }
        okio.Source source = snapshot.getSource(1) ;
        BufferedSource metadata = Okio.buffer(source);
        FilterInputStream bodyIn = new FilterInputStream(metadata.inputStream()) {
            @Override
            public void close() throws IOException {
                snapshot.close();
                super.close();
            }
        };
        return bodyIn ;
    }


}
