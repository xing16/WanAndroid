package com.xing.main.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener;
import com.xing.commonbase.base.BaseMVPActivity;
import com.xing.commonbase.constants.Constants;
import com.xing.commonbase.widget.LinearItemDecoration;
import com.xing.main.R;
import com.xing.main.adapter.SystemArticleAdapter;
import com.xing.main.bean.SystemArticleResult;
import com.xing.main.bean.SystemResult;
import com.xing.main.contract.SystemArticleContract;
import com.xing.main.presenter.SystemArticlePresenter;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/system/SystemArticleActivity")
public class SystemArticleActivity extends BaseMVPActivity<SystemArticlePresenter>
        implements SystemArticleContract.View {

    private RecyclerView recyclerView;
    private List<SystemArticleResult.DatasBean> dataList = new ArrayList<>();
    private SystemArticleAdapter adapter;
    private int id = 0;
    private int page = 0;
    private RefreshLayout refreshLayout;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_system_article;
    }

    @Override
    protected void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        refreshLayout = findViewById(R.id.srf_system_article);
        recyclerView = findViewById(R.id.rv_system_article);
    }

    @Override
    protected void initData() {
        super.initData();
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            SystemResult.ChildrenBean childrenBean = (SystemResult.ChildrenBean) bundle.getSerializable("SystemResult");
            if (childrenBean != null) {
                id = childrenBean.getId();
                String name = childrenBean.getName();
                getSupportActionBar().setTitle(name);
            }
        }
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        // 设置 ItemDecoration 作为分割线
        LinearItemDecoration itemDecoration = new LinearItemDecoration(mContext)
                .height(1f)    // dp
                .color(0xaa999999);  // color 的 int 值，不是 R.color 中的值
        recyclerView.addItemDecoration(itemDecoration);

        presenter.getSystemArticleList(page, id);
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.getSystemArticleList(page, id);
            }
        });
    }

    @Override
    protected SystemArticlePresenter createPresenter() {
        return new SystemArticlePresenter();
    }

    @Override
    public void onSystemArticleList(SystemArticleResult result) {
        page++;
        if (result != null) {
            dataList.addAll(result.getDatas());
            if (adapter == null) {
                adapter = new SystemArticleAdapter(R.layout.item_home_article, dataList);
                adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                        gotoWebViewActivity(dataList.get(position));
                    }
                });
                recyclerView.setAdapter(adapter);
            } else {
                adapter.setNewData(dataList);
            }
        }
    }

    /**
     * 跳转到 webviewactivity
     *
     * @param datasBean
     */
    private void gotoWebViewActivity(SystemArticleResult.DatasBean datasBean) {
        if (datasBean == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(Constants.URL, datasBean.getLink());
        bundle.putInt(Constants.ID, datasBean.getId());
        bundle.putString(Constants.AUTHOR, null);
        bundle.putString(Constants.TITLE, datasBean.getTitle());
        ARouter.getInstance()
                .build("/web/WebViewActivity")
                .with(bundle)
                .navigation();
        overridePendingTransition(R.anim.anim_web_enter, R.anim.anim_alpha);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {
        refreshLayout.finishLoadMore();
    }
}
