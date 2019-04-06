package com.xing.module.gank.activity;

import android.support.v7.widget.RecyclerView;

import com.xing.commonbase.base.BaseMVPActivity;
import com.xing.module.gank.R;
import com.xing.module.gank.contract.MeiziContract;
import com.xing.module.gank.presenter.MeiziPresenter;

public class MeiziActivity extends BaseMVPActivity<MeiziPresenter> implements MeiziContract.View {

    private RecyclerView recyclerView;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_meizi;
    }

    @Override
    protected void initView() {
        recyclerView = findViewById(R.id.rv_meizi);
    }

    @Override
    protected MeiziPresenter createPresenter() {
        return new MeiziPresenter();
    }

    @Override
    public void onMeizi() {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
