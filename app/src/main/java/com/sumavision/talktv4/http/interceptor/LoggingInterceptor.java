package com.sumavision.talktv4.http.interceptor;

import android.util.Log;

import com.jiongbull.jlog.JLog;
import com.sumavision.talktv4.BaseApp;
import com.sumavision.talktv4.dao.RxDao;
import com.sumavision.talktv4.model.entity.HttpCache;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 *  desc  网络请求的拦截
 *  @author  yangjh
 *  created at  16-5-25 下午7:23
 */
public class LoggingInterceptor implements Interceptor {
    private static RxDao httpCache = new RxDao(BaseApp.getContext(), HttpCache.class);
    private static Map<String,String> map = new HashMap<String,String>();
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        long t1 = System.nanoTime();
        JLog.e(String.format("Sending request %s by %s%n%s",
                request.url(), request.method(), request.headers()));
        Response response = chain.proceed(request);
        JLog.e("thread",Thread.currentThread().getName());

        long t2 = System.nanoTime();
        JLog.e(String.format("Received response for %s in %.1fms%n%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        return response;
    }


}
