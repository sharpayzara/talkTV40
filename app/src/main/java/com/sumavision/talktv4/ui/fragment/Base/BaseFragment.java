package com.sumavision.talktv4.ui.fragment.Base;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.sumavision.talktv4.presenter.base.BasePresenter;

import butterknife.ButterKnife;
/**
 *  desc  fragment基类
 *  @author  yangjh
 *  created at  16-5-24 下午8:57
 */
public abstract class BaseFragment<T extends BasePresenter> extends Fragment {

    protected T presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(getLayoutResId(), container, false);
        ButterKnife.bind(this, view);
        initPresenter();
        return view;
    }

    protected abstract int getLayoutResId();

    protected abstract void initPresenter();

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
       // App.getRefWatcher(getContext()).watch(this);
    }
}
