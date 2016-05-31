package com.sumavision.talktv4.presenter;

import android.content.Context;
import android.util.Log;

import com.jiongbull.jlog.JLog;
import com.sumavision.talktv4.model.CallBackListener;
import com.sumavision.talktv4.model.HomeModel;
import com.sumavision.talktv4.model.entity.decor.ClassifyData;
import com.sumavision.talktv4.model.entity.decor.ClassifyUpdataData;
import com.sumavision.talktv4.model.impl.HomeModelmpl;
import com.sumavision.talktv4.presenter.base.BasePresenter;
import com.sumavision.talktv4.ui.iview.IGanHuoView;

public class GanHuoPresenter extends BasePresenter<IGanHuoView> {
    HomeModel homeModel;
    Context mContext ;
    public GanHuoPresenter(Context context, IGanHuoView iView) {
        super(context, iView);
        homeModel = new HomeModelmpl();
        mContext = context;
    }

    @Override
    public void release() {
        homeModel.release();
    }
    public void getClassifyData(){
        homeModel.loadClassifys(new CallBackListener<ClassifyData>() {

            @Override
            public void onSuccess(ClassifyData classifyData) {
                if(classifyData == null){
                    //说明服务器没有分类的全量数据
                    iView.showClassifyView(null);
                    JLog.d("talkTV4.0", "当前未获取到全量数据");
                }else{
                    iView.showClassifyView(classifyData.results);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {
                JLog.d("talkTV4.0", "访问异常");
            }
        });
    }
    public void getClassifyUpdataData(){
        homeModel.loadClassifyUpdatas(new CallBackListener<ClassifyUpdataData>() {
            @Override
            public void onSuccess(ClassifyUpdataData classifyUpdataData) {
                if(classifyUpdataData == null || classifyUpdataData.isCacheSource()){
                    // 说明服务器没有更新
                    //这里需要去走本地获取分类列表数据
                    iView.updataClassifyView(null);
                    Log.d("talkTV4.0", "当前未获取到分类更新数据");
                }else{
                    //说明服务器有更新
                    iView.updataClassifyView(classifyUpdataData.results);
                }
            }

            @Override
            public void onFailure(Throwable throwable) {

            }
        });
    }
}

