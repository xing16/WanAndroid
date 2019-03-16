package com.xing.commonbase.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xing.commonbase.mvp.IPresenter;
import com.xing.commonbase.mvp.IView;

import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseMVPActivity<P extends IPresenter> extends AppCompatActivity implements IView {
    protected Context mContext;
    protected P presenter;
    private Unbinder unbinder;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        mContext = this;
        presenter = createPresenter();
        // presenter 绑定 view
        if (presenter != null) {
            presenter.attachView(this);
        }
        unbinder = ButterKnife.bind(this);
        init();
    }

    protected abstract int getLayoutResId();

    protected abstract P createPresenter();

    protected void init() {

    }

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
