package com.xing.main.presenter;

import com.xing.commonbase.base.BaseObserver;
import com.xing.commonbase.mvp.BasePresenter;
import com.xing.main.apiservice.MainApiService;
import com.xing.main.bean.BannerResult;
import com.xing.main.bean.HomeArticleResult;
import com.xing.main.bean.WeChatAuthorResult;
import com.xing.main.contract.HomeContract;

import java.util.List;

public class HomePresenter extends BasePresenter<HomeContract.View> implements HomeContract.Presenter {


    /**
     * 获取 Banner 数据
     */
    @Override
    public void getBanner() {
        addSubscribe(create(MainApiService.class).getBanner(), new BaseObserver<List<BannerResult>>() {
            @Override
            protected void onSuccess(List<BannerResult> data) {
                if (isViewAttached()) {
                    getView().onBanner(data);
                }
            }
        });
    }

    @Override
    public void getWeChatAuthors() {
        addSubscribe(create(MainApiService.class).getWeChatAuthors(), new BaseObserver<List<WeChatAuthorResult>>() {
            @Override
            protected void onSuccess(List<WeChatAuthorResult> data) {
                if (isViewAttached()) {
                    getView().onWeChatAuthors(data);
                }
            }
        });
    }

    /**
     * 获取首页文章数据
     *
     * @param page
     */
    @Override
    public void getHomeArticles(int page) {
        addSubscribe(create(MainApiService.class).getHomeArticles(page), new BaseObserver<HomeArticleResult>() {
            @Override
            protected void onSuccess(HomeArticleResult data) {
                if (isViewAttached()) {
                    getView().onHomeArticles(data);
                }
            }
        });
    }


}
