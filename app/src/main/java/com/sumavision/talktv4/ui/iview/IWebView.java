package com.sumavision.talktv4.ui.iview;

import com.sumavision.talktv4.ui.iview.base.IBaseView;

/**
 * Created by sharpay on 16-5-24.
 */
public interface IWebView extends IBaseView {

    void showProgressBar(int progress);

    void setWebTitle(String title);

    void openFailed();

}

