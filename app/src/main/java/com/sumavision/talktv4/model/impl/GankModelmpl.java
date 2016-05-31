package com.sumavision.talktv4.model.impl;

import com.sumavision.talktv4.http.SumaClient;
import com.sumavision.talktv4.http.HomeRetrofit;
import com.sumavision.talktv4.model.CallBackListener;
import com.sumavision.talktv4.model.GankModel;
import com.sumavision.talktv4.model.entity.decor.GanHuoData;

import java.util.concurrent.Callable;

import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;

/**
 * Created by sharpay on 16-5-26.
 */
public class GankModelmpl  implements GankModel{
    Subscription subscription;
    @Override
    public void loadGanks(final String type, final int page, final CallBackListener listener) {
         subscription = SumaClient.subscribe(new Callable<Observable<GanHuoData>>() {
            @Override
            public Observable<GanHuoData> call() {
                return SumaClient.getRetrofitInstance(HomeRetrofit.class).getGanHuoData(SumaClient.getCacheControl(),type,page);
            }
        }, new Action1<GanHuoData>() {
            @Override
            public void call(GanHuoData ganHuoData) {
                listener.onSuccess(ganHuoData);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                listener.onFailure(throwable);
            }
        },GanHuoData.class);
    }

    @Override
    public void release() {
        if(subscription != null){
            subscription.unsubscribe();
        }
    }
}
