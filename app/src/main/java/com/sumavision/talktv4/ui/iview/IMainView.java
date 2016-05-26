package com.sumavision.talktv4.ui.iview;
import com.sumavision.talktv4.ui.iview.base.IBaseView;

/**
 * Created by xybcoder on 2016/3/1.
 */
public interface IMainView extends IBaseView {
    void showProgress();
    void hideProgress();
    void showErrorView();
    void showNoMoreData();
}
