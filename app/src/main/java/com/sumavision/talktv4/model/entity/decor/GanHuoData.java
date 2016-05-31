package com.sumavision.talktv4.model.entity.decor;

import com.google.gson.annotations.SerializedName;
import com.sumavision.talktv4.model.entity.Gank;

import java.util.List;

import retrofit2.http.Field;

/**
 * 通用(Android ,ios,前端，拓展资源，休息视频)数据模型
 *
 */
public class GanHuoData extends BaseData {
   @SerializedName("results")
    public List<Gank> results;

    @Override
    public String toString() {
        return "GanHuoData{" +
                "results=" + results +
                '}';
    }
}
