package com.xing.main.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xing.commonbase.WebViewActivity;
import com.xing.commonbase.base.BaseLazyFragment;
import com.xing.commonbase.util.IntentUtil;
import com.xing.commonbase.widget.LinearItemDecoration;
import com.xing.main.R;
import com.xing.main.adapter.ProjectRecyclerAdapter;
import com.xing.main.bean.ProjectResult;
import com.xing.main.contract.ProjectPageContract;
import com.xing.main.presenter.ProjectPagePresenter;

import java.util.ArrayList;
import java.util.List;

public class ProjectPageFragment extends BaseLazyFragment<ProjectPagePresenter> implements ProjectPageContract.View {

    private RecyclerView recyclerView;
    private int id;
    private int page = 0;
    private ProjectRecyclerAdapter recyclerAdapter;
    private List<ProjectResult.DatasBean> mDataList = new ArrayList<>();

    public static ProjectPageFragment newInstance(int id) {
        ProjectPageFragment homePageFragment = new ProjectPageFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("id", id);
        homePageFragment.setArguments(bundle);
        return homePageFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            id = bundle.getInt("id");
        }
    }

    @Override
    protected void loadData() {
        presenter.getData(id, page);
    }

    @Override
    protected ProjectPagePresenter createPresenter() {
        return new ProjectPagePresenter();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home_page;
    }

    @Override
    protected void initView(View rootView) {
        recyclerView = rootView.findViewById(R.id.rv_home_page);
    }

    @Override
    protected void initData() {
        Log.e("LinearItemDecoration", "initData: " + id);
        // 设置 ItemDecoration 作为分割线
        LinearItemDecoration itemDecoration = new LinearItemDecoration(mContext)
//                .itemOffsets(10, 10)   // 10dp
                .height(0.8f)    // 0.5dp
                .color(Color.parseColor("#aacccccc"))  // color 的 int 值，不是 R.color 中的值
                .margin(10, 10);  // 10dp
        recyclerView.addItemDecoration(itemDecoration);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onProjectList(ProjectResult projectResult) {
        if (projectResult == null) {
            return;
        }
        List<ProjectResult.DatasBean> datas = projectResult.getDatas();
        if (datas == null || datas.size() == 0) {
            return;
        }

        mDataList.addAll(datas);
        if (recyclerAdapter == null) {
            recyclerAdapter = new ProjectRecyclerAdapter(R.layout.item_recycler_project, mDataList);
            recyclerView.setAdapter(recyclerAdapter);
            recyclerAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    gotoWebViewActivity(mDataList.get(position).getLink());
                }
            });
        } else {
//            recyclerAdapter.set
        }
    }

    /**
     * 跳转到 WebViewActivity
     */
    private void gotoWebViewActivity(String url) {
        Bundle bundle = new Bundle();
        bundle.putString("url", url);
        IntentUtil.startActivity(mContext, WebViewActivity.class, bundle);
    }
}
