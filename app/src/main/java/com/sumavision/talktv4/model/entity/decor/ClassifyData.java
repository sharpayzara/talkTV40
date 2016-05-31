package com.sumavision.talktv4.model.entity.decor;


import com.google.gson.annotations.SerializedName;
import com.sumavision.talktv4.model.entity.ClassifyItem;

import java.util.ArrayList;

/**
 * 后台分类数据模型
 * Created by zhoutao on 2016/5/27.
 */
public class ClassifyData extends BaseData{
    @SerializedName("items")
    public ArrayList<ClassifyItem> results;
    @Override
    public String toString() {
        return "ClassifyData{" +
                "results=" + results +
                '}';
    }
}
