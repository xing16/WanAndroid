package com.xing.main.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xing.commonbase.base.BaseMVPFragment;
import com.xing.commonbase.widget.LinearItemDecoration;
import com.xing.main.R;
import com.xing.main.adapter.SystemLeftAdapter;
import com.xing.main.adapter.SystemRightAdapter;
import com.xing.main.bean.SystemResult;
import com.xing.main.contract.SystemContract;
import com.xing.main.presenter.SystemPresenter;

import java.util.List;

/**
 * 体系
 */
public class SystemFragment extends BaseMVPFragment<SystemPresenter> implements SystemContract.View {

    private RecyclerView leftRecyclerView;
    private RecyclerView rightRecyclerView;
    private SystemLeftAdapter systemLeftAdapter;
    private SystemRightAdapter systemRightAdapter;
    private int leftCurPosition = 0;

    @Override
    protected SystemPresenter createPresenter() {
        return new SystemPresenter();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_system;
    }

    @Override
    protected void initView(View rootView) {
        leftRecyclerView = rootView.findViewById(R.id.rv_system_left);
        rightRecyclerView = rootView.findViewById(R.id.rv_system_right);
    }

    @Override
    protected void initData() {
        leftRecyclerView.setLayoutManager(new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL, false));
        rightRecyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        // 设置 ItemDecoration 作为分割线
        LinearItemDecoration itemDecoration = new LinearItemDecoration(mContext)
                .height(0.5f)    // dp
                .color(0xaacccccc);  // color 的 int 值，不是 R.color 中的值
        leftRecyclerView.addItemDecoration(itemDecoration);

        /**
         * 请求体系分类数据
         */
        presenter.getSystemList();
    }

    @Override
    public void onSystemList(final List<SystemResult> systemResults) {
        if (systemResults == null) {
            return;
        }
        if (systemLeftAdapter == null) {
            // 左侧 recyclerview
            systemLeftAdapter = new SystemLeftAdapter(R.layout.item_system_left, systemResults);
            systemResults.get(0).setSelected(true);
            systemLeftAdapter.notifyDataSetChanged();
            systemLeftAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    if (leftCurPosition == position) {
                        return;
                    }
                    leftCurPosition = position;
                    for (int i = 0; i < systemResults.size(); i++) {
                        SystemResult systemResult = systemResults.get(i);
                        systemResult.setSelected(i == leftCurPosition);
                    }
                    systemLeftAdapter.notifyDataSetChanged();
                    SystemResult systemResult = systemResults.get(position);
                    if (systemResult != null) {
                        List<SystemResult.ChildrenBean> children = systemResult.getChildren();
                        systemRightAdapter.setNewData(children);
                    }
                }
            });
            leftRecyclerView.setAdapter(systemLeftAdapter);

        } else {
            systemLeftAdapter.setNewData(systemResults);
        }
        // 右侧 recyclerview
        if (systemRightAdapter == null) {
            systemRightAdapter = new SystemRightAdapter(R.layout.item_system_right, systemResults.get(0).getChildren());
            systemRightAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    gotoSystemArticleActivity(systemRightAdapter.getData().get(position));
                }
            });
            rightRecyclerView.setAdapter(systemRightAdapter);
        }
    }

    /**
     * 跳转到体系文章列表
     *
     * @param childrenBean
     */
    private void gotoSystemArticleActivity(SystemResult.ChildrenBean childrenBean) {
        Bundle bundle = new Bundle();
        bundle.putSerializable("SystemResult", childrenBean);
        ARouter.getInstance()
                .build("/system/SystemArticleActivity")
                .with(bundle)
                .navigation();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
