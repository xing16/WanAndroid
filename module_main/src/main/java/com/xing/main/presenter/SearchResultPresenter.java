package com.xing.main.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.xing.commonbase.base.BaseObserver;
import com.xing.commonbase.mvp.BasePresenter;
import com.xing.main.apiservice.MainApiService;
import com.xing.main.bean.SearchResult;
import com.xing.main.bean.db.SearchHistory;
import com.xing.main.contract.SearchResultContract;
import com.xing.main.db.DbManager;
import com.xing.main.db.SearchHistoryDao;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchResultPresenter extends BasePresenter<SearchResultContract.View> implements SearchResultContract.Presenter {

    private Disposable disposable;

    @Override
    public void saveSearchHistory(String keyword) {
        if (TextUtils.isEmpty(keyword)) {
            return;
        }
        disposable = Observable.just(keyword)
                .observeOn(Schedulers.newThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String keyword) throws Exception {
                        Log.e("debugdebug", "accept: ====== " + Thread.currentThread().getName());
                        SearchHistoryDao searchHistoryDao = DbManager.getInstance().getSearchHistoryDao();
                        List<SearchHistory> searchHistories = searchHistoryDao.loadAll();
                        if (searchHistories != null && searchHistories.size() > 0) {
                            boolean flag = false;
                            for (SearchHistory searchHistory : searchHistories) {
                                String cacheKeyword = searchHistory.getKeyword();
                                if (keyword.equals(cacheKeyword)) {
                                    // 更新时间
                                    searchHistory.setTime(System.currentTimeMillis());
                                    searchHistoryDao.update(searchHistory);
                                    flag = true;
                                    break;
                                }
                            }
                            // 新增
                            if (!flag) {
                                searchHistoryDao.insert(new SearchHistory(keyword, System.currentTimeMillis()));
                            }
                        } else {
                            // 新增
                            searchHistoryDao.insert(new SearchHistory(keyword, System.currentTimeMillis()));
                        }
                    }
                });
    }

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


    @Override
    public void detachView() {
        super.detachView();
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
