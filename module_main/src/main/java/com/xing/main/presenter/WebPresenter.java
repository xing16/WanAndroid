package com.xing.main.presenter;

import com.xing.commonbase.base.BaseObserver;
import com.xing.commonbase.mvp.BasePresenter;
import com.xing.main.apiservice.MainApiService;
import com.xing.main.bean.FavoriteAddResult;
import com.xing.main.contract.WebContract;

import io.reactivex.Observable;

public class WebPresenter extends BasePresenter<WebContract.View> implements WebContract.Presenter {

    @Override
    public void addArticleFavorite(int id, String title, String author, String link) {
        Observable observable;
        if (id == -1) {   // 站外文章
            observable = create(MainApiService.class).addFavorite(title, author, link);
        } else {     // 站内文章
            observable = create(MainApiService.class).addFavorite(id);
        }
        addSubscribe(observable, new BaseObserver<FavoriteAddResult>() {

            @Override
            protected void onSuccess(FavoriteAddResult data) {
                if (isViewAttached()) {
                    getView().onFavoriteAdded();
                }
            }
        });
    }
}
