package com.sumavision.talktv4.ui.iview;

import com.sumavision.talktv4.model.entity.Gank;
import com.sumavision.talktv4.ui.iview.base.IBaseView;

import java.util.List;

/**
 * Created by sharpay on 16-5-24.
 */
public interface IGanHuoView extends IBaseView{
    void showProgressBar();
    void hideProgressBar();
    void showErrorView();
    void showNoMoreData();
    void showListView(List<Gank> gankList);
}
