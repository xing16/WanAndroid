package com.xing.commonbase.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.xing.commonbase.mvp.IPresenter;


public abstract class BaseLazyFragment<P extends IPresenter> extends BaseMVPFragment<P> {

    /**
     * Fragment 中的 View 是否创建完成
     */
    protected boolean isViewCreated;

    /**
     * Fragment 是否对用户可见
     */
    protected boolean isVisible;

    /**
     * Fragment 左右切换时，只在第一次显示时请求数据
     */
    protected boolean isFirstLoad = true;


    public BaseLazyFragment() {
    }

    /**
     * Fragment 中创建完成的回调方法
     *
     * @param view
     * @param savedInstanceState
     */
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isViewCreated = true;
        lazyLoad();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    private void onInvisible() {

    }

    protected void onVisible() {
        lazyLoad();
    }


    /**
     * (1) isViewCreated 参数在系统调用 onViewCreated 时设置为 true,这时onCreateView方法已调用完毕(一般我们在这方法
     * 里执行findviewbyid等方法),确保 loadData()方法不会报空指针异常。
     * <p>
     * (2) isVisible 参数在 fragment 可见时通过系统回调 setUserVisibileHint 方法设置为true,不可见时为false，
     * 这是 fragment 实现懒加载的关键。
     * <p>
     * (3) isFirstLoad 确保 ViewPager 来回切换时 TabFragment 的 loadData()方法不会被重复调用，loadData()在该
     * Fragment 的整个生命周期只调用一次,第一次调用 loadData()方法后马上执行 isFirst = false。
     */
    private void lazyLoad() {
        /**
         *这里进行双重标记判断,是因为setUserVisibleHint会多次回调,
         * 并且会在onCreateView执行前回调,必须确保onCreateView加载完毕且页面可见,才加载数据
         */
        if (isViewCreated && isVisible && isFirstLoad) {
            loadData();
            isFirstLoad = false;
        }
    }

    /**
     * 子类实现加载数据
     */
    protected abstract void loadData();
}
