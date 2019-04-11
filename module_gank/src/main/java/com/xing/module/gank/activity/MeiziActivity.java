package com.xing.module.gank.activity;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xing.commonbase.base.BaseMVPActivity;
import com.xing.module.gank.R;
import com.xing.module.gank.adapter.MeiziAdapter;
import com.xing.module.gank.bean.MeiziResult;
import com.xing.module.gank.contract.MeiziContract;
import com.xing.module.gank.presenter.MeiziPresenter;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/gank/MeiziActivity")
public class MeiziActivity extends BaseMVPActivity<MeiziPresenter>
        implements MeiziContract.View {

    private RecyclerView recyclerView;
    private List<MeiziResult> dataList = new ArrayList<>();
    private MeiziAdapter adapter;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_meizi;
    }

    @Override
    protected void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("妹子");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        recyclerView = findViewById(R.id.rv_meizi);
    }

    @Override
    protected MeiziPresenter createPresenter() {
        return new MeiziPresenter();
    }

    @Override
    protected void initData() {
        super.initData();
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        presenter.getMeiziList(10, 1);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onMeiziList(List<MeiziResult> meiziResults) {
        dataList.addAll(meiziResults);
        if (adapter == null) {
            adapter = new MeiziAdapter(R.layout.item_meizi, dataList);
            recyclerView.setAdapter(adapter);
        } else {
            adapter.setNewData(meiziResults);
        }
    }
}
