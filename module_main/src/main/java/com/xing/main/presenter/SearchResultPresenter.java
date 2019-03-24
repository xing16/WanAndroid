package com.xing.main.presenter;

import com.xing.commonbase.base.BaseObserver;
import com.xing.commonbase.mvp.BasePresenter;
import com.xing.main.apiservice.MainApiService;
import com.xing.main.bean.SearchResult;
import com.xing.main.contract.SearchResultContract;

public class SearchResultPresenter extends BasePresenter<SearchResultContract.View> implements SearchResultContract.Presenter {
    @Override
    public void getSearchResult(int page, String keyword) {
        addSubscribe(create(MainApiService.class).getSearchResult(page, keyword), new BaseObserver<SearchResult>() {

            @Override
            protected void onSuccess(SearchResult data) {
                if (isViewAttached()) {
                    getView().onSearchResult(data);
                }
            }
        });
    }
}
