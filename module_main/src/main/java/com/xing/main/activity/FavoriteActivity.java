package com.xing.main.activity;

import android.graphics.Color;
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
import com.xing.main.adapter.FavoriteAdapter;
import com.xing.main.bean.FavoriteResult;
import com.xing.main.contract.FavoriteContract;
import com.xing.main.presenter.FavoritePresenter;

import java.util.ArrayList;
import java.util.List;

@Route(path = "/favorite/FavoriteActivity")
public class FavoriteActivity extends BaseMVPActivity<FavoritePresenter>
        implements FavoriteContract.View {
    private RecyclerView recyclerView;
    private int page = 0;
    private List<FavoriteResult.DatasBean> dataList = new ArrayList<>();
    private FavoriteAdapter adapter;
    private RefreshLayout refreshLayout;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_favorite;
    }

    @Override
    protected FavoritePresenter createPresenter() {
        return new FavoritePresenter();
    }


    @Override
    protected void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.favorite);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        refreshLayout = findViewById(R.id.srl_favorite);
        recyclerView = findViewById(R.id.rv_favorite);
    }

    @Override
    protected void initData() {
        super.initData();
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        // 设置 ItemDecoration 作为分割线
        LinearItemDecoration itemDecoration = new LinearItemDecoration(mContext)
//                .itemOffsets(10, 10)   // 10dp
                .height(0.8f)    // 0.5dp
                .color(Color.parseColor("#aacccccc"))  // color 的 int 值，不是 R.color 中的值
                .margin(12, 12);  // 12dp
        recyclerView.addItemDecoration(itemDecoration);
        presenter.getFavoriteList(page);

        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                presenter.getFavoriteList(page);
            }
        });
    }

    /**
     * 获取收藏列表结果回调
     *
     * @param result
     */
    @Override
    public void onFavoriteList(FavoriteResult result) {
        refreshLayout.finishLoadMore();
        page++;
        if (result == null) {
            return;
        }

        dataList.addAll(result.getDatas());
        if (adapter == null) {
            adapter = new FavoriteAdapter(R.layout.item_home_article, dataList);
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

    /**
     * 跳转到 WebViewActivity
     */
    private void gotoWebViewActivity(FavoriteResult.DatasBean bean) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.URL, bean.getLink());
        bundle.putInt(Constants.ID, bean.getId());
        bundle.putString(Constants.AUTHOR, bean.getAuthor());
        bundle.putString(Constants.TITLE, bean.getTitle());
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

    }

}
