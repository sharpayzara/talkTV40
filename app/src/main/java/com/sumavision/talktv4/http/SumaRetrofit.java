package com.sumavision.talktv4.http;

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
public interface SumaRetrofit {
     @GET("data/休息视频/" + AppGlobalConsts.MEIZI_SIZE + "/{page}")
    Observable<FunnyData> getFunnyData(@Path("page") int page);

    //请求不同类型干货（通用）
    @GET("data/{type}/"+AppGlobalConsts.MEIZI_SIZE+"/{page}")
    Observable<GanHuoData> getGanHuoData(@Header("Cache-Control") String cacheControl, @Path("type") String type, @Path("page") int page);

}
