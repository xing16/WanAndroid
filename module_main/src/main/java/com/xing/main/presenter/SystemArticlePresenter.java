package com.xing.main.presenter;

import com.xing.commonbase.base.BaseObserver;
import com.xing.commonbase.mvp.BasePresenter;
import com.xing.main.apiservice.MainApiService;
import com.xing.main.bean.SystemArticleResult;
import com.xing.main.contract.SystemArticleContract;

public class SystemArticlePresenter extends BasePresenter<SystemArticleContract.View>
        implements SystemArticleContract.Presenter {

    @Override
    public void getSystemArticleList(int page, int id) {
        addSubscribe(create(MainApiService.class).getSystemArticles(page, id),
                new BaseObserver<SystemArticleResult>(getView()) {

                    @Override
                    protected void onSuccess(SystemArticleResult data) {
                        if (isViewAttached()) {
                            getView().onSystemArticleList(data);
                        }
                    }
                });
    }
}
