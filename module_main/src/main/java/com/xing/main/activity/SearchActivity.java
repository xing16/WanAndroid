package com.xing.main.activity;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xing.commonbase.base.BaseActivity;
import com.xing.main.R;
import com.xing.main.fragment.SearchHistoryFragment;

@Route(path = "/search/SearchActivity")
public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private TextView searchTxtView;
    private ImageView searchBackView;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        searchTxtView = findViewById(R.id.tv_search_text);
        searchBackView = findViewById(R.id.iv_search_back);
    }

    @Override
    protected void initData() {
        super.initData();
        SearchHistoryFragment searchHistoryFragment = SearchHistoryFragment.newInstance();

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fl_search_container, searchHistoryFragment);
        fragmentTransaction.show(searchHistoryFragment);
        fragmentTransaction.commit();
        setListener();
    }

    private void setListener() {
        searchTxtView.setOnClickListener(this);
        searchBackView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_search_text) {
            showSearchResultFragment();
        } else if (v.getId() == R.id.iv_search_back) {
            finish();
        }
    }

    private void showSearchResultFragment() {

    }
}
