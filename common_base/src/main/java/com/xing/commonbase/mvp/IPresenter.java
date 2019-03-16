package com.xing.commonbase.mvp;

public interface IPresenter<V extends IView> {

    /**
     * 绑定 View ，一般在初始化时调用
     *
     * @param view
     */
    void attachView(V view);

    /**
     * 解除绑定 View,一般在 onDestroy 中调用
     */
    void detachView();

    /**
     * 是否绑定了View,一般在发起网络请求之前调用
     *
     * @return
     */
    boolean isViewAttached();

    V getView();
}
