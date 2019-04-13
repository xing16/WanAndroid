package com.xing.module.gank.apiservice;


import com.xing.commonbase.base.BaseResponse;
import com.xing.module.gank.bean.MeiziResult;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface GankApiService {

    @GET("http://gank.io/api/data/福利/{pageSize}/{page}")
    Observable<BaseResponse<List<MeiziResult>>> getMeiziList(@Path("pageSize") int pageSize, @Path("page") int page);

    @Streaming
    @GET
    Observable<ResponseBody> downloadImage(@Url String url);

}
