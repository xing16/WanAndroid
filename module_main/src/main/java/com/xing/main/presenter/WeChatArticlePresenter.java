package com.xing.main.presenter;

import com.xing.commonbase.base.BaseObserver;
import com.xing.commonbase.mvp.BasePresenter;
import com.xing.main.apiservice.MainApiService;
import com.xing.main.bean.WeChatArticleResult;
import com.xing.main.contract.WeChatArticleListContract;

public class WeChatArticlePresenter extends BasePresenter<WeChatArticleListContract.View>
        implements WeChatArticleListContract.Presenter {

    @Override
    public void getWeChatArticle(int id, int page) {
        addSubscribe(create(MainApiService.class).getWeChatArticles(id, page), new BaseObserver<WeChatArticleResult>() {

            @Override
            protected void onSuccess(WeChatArticleResult data) {
                if (isViewAttached()) {
                    getView().onWeChatArticleList(data);
                }
            }
        });
    }
}
