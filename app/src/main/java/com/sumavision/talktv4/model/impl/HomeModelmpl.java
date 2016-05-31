package com.sumavision.talktv4.model.impl;

import com.sumavision.talktv4.http.HomeRetrofit;
import com.sumavision.talktv4.http.SumaClient;
import com.sumavision.talktv4.model.CallBackListener;
import com.sumavision.talktv4.model.HomeModel;
import com.sumavision.talktv4.model.entity.decor.ClassifyData;
import com.sumavision.talktv4.model.entity.decor.ClassifyUpdataData;

import java.util.concurrent.Callable;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by zhoutao on 16-5-26.
 */
public class HomeModelmpl implements HomeModel {
    Subscription subscription;
    @Override
    public void loadClassifys(final CallBackListener listener) {
         subscription = SumaClient.subscribe(new Callable<Observable<ClassifyData>>() {
            @Override
            public Observable<ClassifyData> call() {
                //网络请求
                return SumaClient.getRetrofitInstance(HomeRetrofit.class).getClassifyData();
            }
        }, new Action1<ClassifyData>() {
            @Override
            public void call(ClassifyData classifyData) {
                listener.onSuccess(classifyData);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {

            }
        },ClassifyData.class);
    }

    @Override
    public void loadClassifyUpdatas(final CallBackListener listener){
       subscription = SumaClient.subscribe(new Callable<Observable<ClassifyUpdataData>>() {
           @Override
           public Observable<ClassifyUpdataData> call() throws Exception {
               return SumaClient.getRetrofitInstance(HomeRetrofit.class).getClassifyUpdateData(SumaClient.getCacheControl());
           }
       }, new Action1<ClassifyUpdataData>() {
           @Override
           public void call(ClassifyUpdataData classifyUpdataData) {
               listener.onSuccess(classifyUpdataData);
           }
       }, new Action1<Throwable>() {
           @Override
           public void call(Throwable throwable) {
               listener.onFailure(throwable);
           }
       },ClassifyUpdataData.class);
    }

    @Override
    public void release() {
        if(subscription != null){
            subscription.unsubscribe();
        }
    }
}
