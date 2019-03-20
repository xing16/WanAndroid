package com.xing.main.apiservice;

import com.xing.commonbase.base.BaseResponse;
import com.xing.main.bean.BannerResult;
import com.xing.main.bean.HomeArticleResult;
import com.xing.main.bean.ProjectResult;
import com.xing.main.bean.ProjectTabItem;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MainApiService {

    /**
     * 获取首页 banner 数据
     *
     * @return
     */
    @GET("banner/json")
    Observable<BaseResponse<List<BannerResult>>> getBanner();


    @GET("article/list/{page}/json")
    Observable<BaseResponse<HomeArticleResult>> getHomeArticles(@Path("page") int page);


    /**
     * Project 指定栏目下的列表
     *
     * @param page
     * @param id
     * @return
     */
    @GET("project/list/{page}/json")
    Observable<BaseResponse<ProjectResult>> getProjects(@Path("page") int page, @Query("cid") int id);

    /**
     * Project 栏目分类
     *
     * @return
     */
    @GET("project/tree/json")
    Observable<BaseResponse<List<ProjectTabItem>>> getProjectTabs();


}
