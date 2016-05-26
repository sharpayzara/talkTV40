package com.sumavision.talktv4.model;

/**
 * Created by sharpay on 16-5-26.
 */
public interface GankModel extends BaseModel{
    void loadGanks(String type,int page,CallBackListener listener);
}
