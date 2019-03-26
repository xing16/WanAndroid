package com.xing.main.presenter;

import android.content.Context;

import com.xing.commonbase.base.BaseResponse;
import com.xing.commonbase.mvp.BasePresenter;
import com.xing.main.apiservice.MainApiService;
import com.xing.main.bean.SearchHotKey;
import com.xing.main.bean.db.SearchHistory;
import com.xing.main.contract.SearchHistoryContract;
import com.xing.main.db.DbManager;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.BiFunction;
import io.reactivex.schedulers.Schedulers;

public class SearchHistoryPresenter extends BasePresenter<SearchHistoryContract.View> implements SearchHistoryContract.Presenter {

    private Context context;

    public SearchHistoryPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void getSearchHistory() {

        // 搜索历史
        Observable<List<SearchHistory>> searchHistoryObservable = Observable.create(
                new ObservableOnSubscribe<List<SearchHistory>>() {
                    @Override
                    public void subscribe(ObservableEmitter<List<SearchHistory>> emitter) throws Exception {
                        // 搜索历史
                        List<SearchHistory> searchHistories = DbManager.getInstance().getSearchHistoryDao().loadAll();
                        if (searchHistories != null) {
                            emitter.onNext(searchHistories);
                        }
                        emitter.onComplete();
                    }
                });

        // 搜索热词
        Observable<BaseResponse<List<SearchHotKey>>> baseResponseObservable = create(MainApiService.class)
                .getSearchHotKey().subscribeOn(Schedulers.newThread());
        Observable<Object> zip = Observable.zip(searchHistoryObservable, baseResponseObservable, new BiFunction<List<SearchHistory>, BaseResponse<List<SearchHotKey>>, Object>() {
            @Override
            public Object apply(List<SearchHistory> searchHistories, BaseResponse<List<SearchHotKey>> listBaseResponse) throws Exception {
                List<SearchHistory> allSearchList = new ArrayList<>();
                List<SearchHotKey> searchHotKeys = listBaseResponse.getData();
                List<SearchHistory> searchHistoryList = new ArrayList<>();
                if (searchHotKeys != null) {
                    for (SearchHotKey searchHotKey : searchHotKeys) {
                        searchHistoryList.add(new SearchHistory(searchHotKey.getName(), System.currentTimeMillis()));
                    }
                }
                allSearchList.addAll(searchHistoryList);
                allSearchList.addAll(searchHistories);
                return allSearchList;
            }
        });

    }

    @Override
    public void getSearchSuggestion() {

    }
}
