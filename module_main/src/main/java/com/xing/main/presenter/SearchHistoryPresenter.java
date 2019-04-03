package com.xing.main.presenter;

import android.content.Context;

import com.xing.commonbase.base.BaseObserver;
import com.xing.commonbase.mvp.BasePresenter;
import com.xing.main.apiservice.MainApiService;
import com.xing.main.bean.SearchHotKey;
import com.xing.main.bean.db.SearchHistory;
import com.xing.main.contract.SearchHistoryContract;
import com.xing.main.db.DbManager;
import com.xing.main.db.SearchHistoryDao;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchHistoryPresenter extends BasePresenter<SearchHistoryContract.View>
        implements SearchHistoryContract.Presenter {

    private Context context;
    private Disposable disposable;

    public SearchHistoryPresenter(Context context) {
        this.context = context;
    }

    @Override
    public void getSearchHotKey() {
        addSubscribe(create(MainApiService.class).getSearchHotKey(), new BaseObserver<List<SearchHotKey>>() {

            @Override
            protected void onSuccess(List<SearchHotKey> data) {
                if (isViewAttached()) {
                    getView().onSearchHotKey(data);
                }
            }
        });
    }

    @Override
    public void getSearchHistory() {
        disposable = Observable.create(new ObservableOnSubscribe<List<SearchHistory>>() {
            @Override
            public void subscribe(ObservableEmitter<List<SearchHistory>> emitter) {
                // 搜索历史
                List<SearchHistory> searchHistories = DbManager.getInstance()
                        .getSearchHistoryDao()
                        .queryBuilder()
                        .orderDesc(SearchHistoryDao.Properties.Time)
                        .list();
                if (searchHistories != null) {
                    emitter.onNext(searchHistories);
                }
                emitter.onComplete();
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<List<SearchHistory>>() {
                    @Override
                    public void accept(List<SearchHistory> searchHistories) {
                        if (isViewAttached()) {
                            getView().onSearchHistory(searchHistories);
                        }
                    }
                });

    }

    @Override
    public void deleteAllHistory() {
        disposable = Observable.create(new ObservableOnSubscribe<Boolean>() {
            @Override
            public void subscribe(ObservableEmitter<Boolean> emitter) throws Exception {
                DbManager.getInstance().getSearchHistoryDao().deleteAll();
                emitter.onNext(true);
            }
        }).subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean flag) {
                        if (isViewAttached()) {
                            getView().onDeleteAllHistory();
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
