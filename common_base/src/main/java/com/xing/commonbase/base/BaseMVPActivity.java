package com.xing.commonbase.base;

import com.xing.commonbase.mvp.IPresenter;
import com.xing.commonbase.mvp.IView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseMVPActivity<P extends IPresenter> extends BaseActivity implements IView {
    protected P presenter;
    private Unbinder unbinder;

    @Override
    protected void initData() {
        super.initData();
        presenter = createPresenter();
        // presenter 绑定 view
        if (presenter != null) {
            presenter.attachView(this);
        }
        unbinder = ButterKnife.bind(this);

    }

    protected abstract int getLayoutResId();

    protected abstract P createPresenter();

    @Override
    public void onDestroy() {
        super.onDestroy();
        // Activity 销毁时取消所有的订阅
        if (presenter != null) {
            presenter.detachView();
            presenter = null;
        }
        if (unbinder != null) {
            unbinder.unbind();
        }
    }
}
