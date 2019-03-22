package com.xing.main.fragment;


import android.view.View;

import com.xing.commonbase.base.BaseFragment;
import com.xing.main.R;

/**
 * 搜索历史 Fragment
 */
public class SearchHistoryFragment extends BaseFragment {


    public SearchHistoryFragment() {
    }

    public static SearchHistoryFragment newInstance() {
        SearchHistoryFragment searchHistoryFragment = new SearchHistoryFragment();
        return searchHistoryFragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_search_history;
    }

    @Override
    protected void initView(View rootView) {
    }

    @Override
    protected void initData() {

    }

}
