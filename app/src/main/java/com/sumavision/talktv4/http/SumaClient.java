package com.sumavision.talktv4.http;

import com.facebook.stetho.okhttp3.StethoInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jiongbull.jlog.JLog;
import com.sumavision.talktv4.BaseApp;
import com.sumavision.talktv4.http.interceptor.HttpCacheInterceptor;
import com.sumavision.talktv4.http.interceptor.LoggingInterceptor;
import com.sumavision.talktv4.util.NetworkUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 *  desc 获取retrofit实力
 *  @author  yangjh
 *  created at  16-5-23 上午10:50
 */
public class SumaClient {
    public static final String HOST = "http://gank.io/api/";
    private static SumaRetrofit sumaRetrofit;
    protected static final Object monitor = new Object();
    private static Retrofit retrofit;
    private static OkHttpClient okHttpClient;


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
            File cacheFile = new File(BaseApp.getContext().getCacheDir(),
                    "HttpCache"); // 指定缓存路径
            Cache cache = new Cache(cacheFile, 1024 * 1024 * 100); // 指定缓存大小100Mb
            // 云端响应头拦截器，用来配置缓存策略
            okHttpClient = new OkHttpClient.Builder()
                    .readTimeout(15, TimeUnit.SECONDS)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(false)
                    .addNetworkInterceptor(new StethoInterceptor())//chrome工具调试的中间件
                    .addNetworkInterceptor(new HttpCacheInterceptor())
                    .addInterceptor(new LoggingInterceptor())
                    //.addInterceptor(new HttpLoggingInterceptor())
                    .cache(cache)
                    .build();
        }
    }



    public static SumaRetrofit getRetrofitInstance() {
        synchronized (monitor) {
            if (sumaRetrofit == null) {
                sumaRetrofit = retrofit.create(SumaRetrofit.class);
            }
            return sumaRetrofit;
        }
    }
    public static <T> Subscription subscribe(Callable<Observable<T>> callable, Action1<T> action, Action1<Throwable> throwableAction) {
        Subscription subscription = null;
        try {
            Observable<T> observable = callable.call();
            subscription = observable
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(action,throwableAction);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return subscription;
    }


    public static String getCacheControl() {
        return NetworkUtil.isConnectedByState(BaseApp.getContext()) ? HttpCacheInterceptor.CACHE_CONTROL_NETWORK : HttpCacheInterceptor.CACHE_CONTROL_CACHE;
    }
}
