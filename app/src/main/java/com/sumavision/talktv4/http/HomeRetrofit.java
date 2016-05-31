package com.sumavision.talktv4.http;

import com.sumavision.talktv4.model.entity.decor.ClassifyData;
import com.sumavision.talktv4.model.entity.decor.ClassifyUpdataData;
import com.sumavision.talktv4.model.entity.decor.FunnyData;
import com.sumavision.talktv4.model.entity.decor.GanHuoData;
import com.sumavision.talktv4.util.AppGlobalConsts;

import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;
import rx.Observable;

/**
 *  desc  网络访问的接口
 *  @author  yangjh
 *  created at  16-5-23 上午11:35
 */
public interface HomeRetrofit {
    @GET("data/休息视频/" + AppGlobalConsts.MEIZI_SIZE + "/{page}")
    Observable<FunnyData> getFunnyData(@Path("page") int page);

    /* //当本地数据存在的时候，
     @POST("/test/rest1.php")
     Observable<ClassifyUpdataData> getClassifyUpdateData(@Field("tag") String tag);*/
    //当本地数据存在的时候，更新分类
    @GET("/json/update.json")
    Observable<ClassifyUpdataData> getClassifyUpdateData(@Header("Cache-Control") String cacheControl);


    //第一次请求服务器,获取全量分类
    @GET("/json/order.json")
    Observable<ClassifyData> getClassifyData();

    //请求不同类型干货（通用）
    @GET("data/{type}/" + AppGlobalConsts.MEIZI_SIZE + "/{page}")
    Observable<GanHuoData> getGanHuoData(@Header("Cache-Control") String cacheControl, @Path("type") String type, @Path("page") int page);
}