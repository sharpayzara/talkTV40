package com.sumavision.talktv4.model;

/**
 * Created by sharpay on 16-5-26.
 */
public interface HomeModel extends BaseModel{
    void loadClassifys(CallBackListener listener);
    void loadClassifyUpdatas(CallBackListener listener);
}
