package com.sumavision.talktv4.model.entity.decor;


import com.google.gson.annotations.SerializedName;
import com.sumavision.talktv4.model.entity.ClassifyUpdataItem;

import java.util.ArrayList;

/**
 * 分类更新数据模型
 * Created by zhoutao on 2016/5/27.
 */
public class ClassifyUpdataData extends BaseData{
    @SerializedName("items")
    public ArrayList<ClassifyUpdataItem> results;
    @Override
    public String toString() {
        return "ClassifyUpdataData{" +
                "results=" + results +
                '}';
    }
}
