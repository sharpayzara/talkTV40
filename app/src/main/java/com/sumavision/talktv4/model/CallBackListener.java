package com.sumavision.talktv4.model;

import com.sumavision.talktv4.ui.iview.base.IBaseView;

/**
 * Created by sharpay on 16-5-26.
 */
public interface CallBackListener<T> {
    void onSuccess(T t);
    void onFailure(Throwable throwable);

}
