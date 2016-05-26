package com.sumavision.talktv4.presenter;

import android.content.Context;

import com.sumavision.talktv4.model.CallBackListener;
import com.sumavision.talktv4.model.GankModel;
import com.sumavision.talktv4.model.entity.decor.GanHuoData;
import com.sumavision.talktv4.model.impl.GankModelmpl;
import com.sumavision.talktv4.presenter.base.BasePresenter;
import com.sumavision.talktv4.ui.iview.IGanHuoView;

/**
 * Created by sharpay on 16-5-24.
 */
public class GanHuoFragmentPresenter extends BasePresenter<IGanHuoView> {
    GankModel model;

    public GanHuoFragmentPresenter(Context context, IGanHuoView iView) {
        super(context, iView);
        model = new GankModelmpl();
    }

    @Override
    public void release() {
        model.release();
    }

    public void loadGank(final String type, final int page){
     model.loadGanks(type, page, new CallBackListener<GanHuoData>() {
         @Override
         public void onSuccess(GanHuoData ganHuoData) {
             iView.hideProgressBar();
             if (ganHuoData.results.size() == 0){
                 iView.showNoMoreData();
             }else {
                 iView.showListView(ganHuoData.results);
             }
         }

         @Override
         public void onFailure(Throwable throwable) {
             iView.hideProgressBar();
             iView.showErrorView();
         }
     });
    }

}
