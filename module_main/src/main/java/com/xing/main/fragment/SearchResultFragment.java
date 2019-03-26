package com.xing.main.fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xing.commonbase.base.BaseMVPFragment;
import com.xing.commonbase.constants.Constants;
import com.xing.commonbase.widget.LinearItemDecoration;
import com.xing.main.R;
import com.xing.main.adapter.SearchResultAdapter;
import com.xing.main.bean.SearchResult;
import com.xing.main.contract.SearchResultContract;
import com.xing.main.presenter.SearchResultPresenter;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索结果 Fragment
 */
public class SearchResultFragment extends BaseMVPFragment<SearchResultPresenter>
        implements SearchResultContract.View {

    private RecyclerView recyclerView;
    private String keyword;
    private int page;
    private List<SearchResult.DatasBean> dataList = new ArrayList<>();
    private SearchResultAdapter searchResultAdapter;

    public SearchResultFragment() {
    }

    public static SearchResultFragment newInstance(String keyword) {
        SearchResultFragment searchResultFragment = new SearchResultFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constants.KEY_WORD, keyword);
        searchResultFragment.setArguments(bundle);
        return searchResultFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            keyword = bundle.getString(Constants.KEY_WORD);
        }
    }

    /**
     * 保存搜索历史
     *
     * @param keyword
     */
    private void saveSearchHistory(String keyword) {
        presenter.saveSearchHistory(keyword);
    }

    @Override
    protected SearchResultPresenter createPresenter() {
        return new SearchResultPresenter();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_search_result;
    }

    @Override
    protected void initView(View rootView) {
        recyclerView = rootView.findViewById(R.id.rv_search_result);
    }

    @Override
    protected void initData() {
        // 保存搜索历史
        saveSearchHistory(keyword);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        // 设置 ItemDecoration 作为分割线
        LinearItemDecoration itemDecoration = new LinearItemDecoration(mContext)
//                .itemOffsets(10, 10)   // 10dp
                .height(0.8f)    // 0.5dp
                .color(Color.parseColor("#aacccccc"))  // color 的 int 值，不是 R.color 中的值
                .margin(12, 12);  // 12dp
        recyclerView.addItemDecoration(itemDecoration);
        // 请求搜索结果
        presenter.getSearchResult(page, keyword);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void onSearchResult(SearchResult searchResults) {
        if (searchResults == null || searchResults.getDatas() == null) {
            return;
        }
        dataList.addAll(searchResults.getDatas());
        if (searchResultAdapter == null) {
            searchResultAdapter = new SearchResultAdapter(R.layout.item_search_result, dataList);
            searchResultAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    gotoWebViewActivity(dataList.get(position));
                }
            });
            recyclerView.setAdapter(searchResultAdapter);
        } else {
            searchResultAdapter.setNewData(dataList);
        }
    }

    private void gotoWebViewActivity(SearchResult.DatasBean datasBean) {
        if (datasBean == null) {
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString("url", datasBean.getLink());
        ARouter.getInstance()
                .build("/web/WebViewActivity")
                .with(bundle)
                .navigation();
        getActivity().overridePendingTransition(R.anim.anim_web_enter, R.anim.anim_alpha);
    }
}
