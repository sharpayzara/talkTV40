package com.sumavision.talktv4.http;

import com.jiongbull.jlog.JLog;
import com.sumavision.talktv4.model.CallBackListener;
import com.sumavision.talktv4.model.HomeModel;
import com.sumavision.talktv4.model.entity.decor.ClassifyUpdataData;
import com.sumavision.talktv4.model.impl.HomeModelmpl;

/**
 * Created by sharpay on 16-5-30.
 */
public class TestHttp {
    public static void main(String[] args) {
    //    new TestHttp().run304();
    }

    public void run304(){
        HomeModel homeModel = new HomeModelmpl();
        homeModel.loadClassifyUpdatas(new CallBackListener<ClassifyUpdataData>() {
            @Override
            public void onSuccess(ClassifyUpdataData data) {
                JLog.i(data.toString());
            }

            @Override
            public void onFailure(Throwable throwable) {
                JLog.i(throwable.toString());
            }
        });
    }
}
