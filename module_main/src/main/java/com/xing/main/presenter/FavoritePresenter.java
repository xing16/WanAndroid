package com.xing.main.presenter;

import com.xing.commonbase.base.BaseObserver;
import com.xing.commonbase.mvp.BasePresenter;
import com.xing.main.apiservice.MainApiService;
import com.xing.main.bean.FavoriteResult;
import com.xing.main.contract.FavoriteContract;

public class FavoritePresenter extends BasePresenter<FavoriteContract.View>
        implements FavoriteContract.Presenter {

    @Override
    public void getFavoriteList(int page) {
        addSubscribe(create(MainApiService.class).getFavoriteList(page), new BaseObserver<FavoriteResult>() {

            @Override
            protected void onSuccess(FavoriteResult data) {
                if (isViewAttached()) {
                    getView().onFavoriteList(data);
                }
            }
        });
    }
}
