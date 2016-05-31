package com.sumavision.talktv4.presenter.base;

import android.content.Context;

import com.sumavision.talktv4.ui.iview.base.IBaseView;

/**
 *  desc  Presenter基类
 *  @author  yangjh
 *  created at  16-5-18 下午3:50
 */
public abstract class BasePresenter<T extends IBaseView>  {

    protected Context context;
    protected T iView;

    public BasePresenter(Context context, T iView) {
        this.context = context;
        this.iView = iView;
    }

    public void init(){
        iView.initView();
    }

    public abstract void release();

}