package com.xing.main.fragment;


import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xing.commonbase.base.BaseMVPFragment;
import com.xing.commonbase.constants.Constants;
import com.xing.main.R;
import com.xing.main.contract.SearchResultContract;
import com.xing.main.presenter.SearchResultPresenter;

/**
 * 搜索结果 Fragment
 */
public class SearchResultFragment extends BaseMVPFragment<SearchResultPresenter>
        implements SearchResultContract.View {

    private RecyclerView recyclerView;
    private String keyword;

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
        // 请求搜索结果
        presenter.getSearchResult(keyword);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
