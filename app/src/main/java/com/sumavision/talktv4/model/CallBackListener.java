package com.sumavision.talktv4.model;

/**
 * Created by sharpay on 16-5-26.
 */
public interface CallBackListener<T> {
    void onSuccess(T t);
    void onFailure(Throwable throwable);

}
