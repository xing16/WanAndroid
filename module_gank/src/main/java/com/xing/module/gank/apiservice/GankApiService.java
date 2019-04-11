package com.xing.module.gank.apiservice;


import com.xing.commonbase.base.BaseResponse;
import com.xing.module.gank.bean.MeiziResult;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GankApiService {

    @GET("http://gank.io/api/data/福利/{pageSize}/{page}")
    Observable<BaseResponse<List<MeiziResult>>> getMeiziList(@Path("pageSize") int pageSize, @Path("page") int page);
}
