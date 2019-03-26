package com.xing.main.fragment;


import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.xing.commonbase.base.BaseMVPFragment;
import com.xing.main.R;
import com.xing.main.bean.db.SearchHistory;
import com.xing.main.contract.SearchHistoryContract;
import com.xing.main.presenter.SearchHistoryPresenter;

import java.util.List;

/**
 * 搜索历史 Fragment
 */
public class SearchHistoryFragment extends BaseMVPFragment<SearchHistoryPresenter>
        implements SearchHistoryContract.View {


    private RecyclerView recyclerView;

    public SearchHistoryFragment() {
    }

    public static SearchHistoryFragment newInstance() {
        return new SearchHistoryFragment();
    }

    @Override
    protected SearchHistoryPresenter createPresenter() {
        return new SearchHistoryPresenter(mContext);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_search_history;
    }

    @Override
    protected void initView(View rootView) {
        recyclerView = rootView.findViewById(R.id.rv_search_history);
    }

    @Override
    protected void initData() {
        recyclerView.setLayoutManager(new GridLayoutManager(mContext, 2));
        presenter.getSearchHistory();
    }

    @Override
    public void onSearchHistory(List<SearchHistory> searchHistories) {

    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }
}
