package com.xing.commonbase.mvp;

import android.content.Context;

import com.xing.commonbase.base.BaseObserver;
import com.xing.commonbase.http.RetrofitClient;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class BasePresenter<V extends IView> implements IPresenter<V> {

    //    protected V view;
    protected WeakReference<V> viewRef;
    // 管理订阅关系，用于取消订阅
    protected CompositeDisposable compositeDisposable;

    protected Context mContext;

    public BasePresenter() {

    }


    /**
     * 绑定 View ，一般在初始化时调用
     *
     * @param view
     */
    public void attachView(V view) {
        viewRef = new WeakReference<>(view);
        V v = viewRef.get();
//        this.view = view;
    }

    /**
     * 解除绑定 View,一般在 onDestroy 中调用
     */
    public void detachView() {
        this.viewRef = null;
        unsubscribe();
    }

    /**
     * 是否绑定了View,一般在发起网络请求之前调用
     *
     * @return
     */
    public boolean isViewAttached() {
        return viewRef.get() != null;
    }

    public V getView() {
        return viewRef.get();
    }


    /**
     * 添加订阅
     */
    public void addSubscribe(Observable<?> observable, BaseObserver observer) {
        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }
        BaseObserver baseObserver = observable.subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(observer);
        compositeDisposable.add(baseObserver);
    }

    /**
     * 取消订阅
     */
    public void unsubscribe() {
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    protected <T> T create(Class<T> clazz) {
        return RetrofitClient.getInstance().getRetrofit().create(clazz);
    }
}