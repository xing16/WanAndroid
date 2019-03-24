package com.xing.main.activity;

import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xing.commonbase.base.BaseActivity;
import com.xing.main.R;
import com.xing.main.fragment.SearchHistoryFragment;
import com.xing.main.fragment.SearchResultFragment;

@Route(path = "/search/SearchActivity")
public class SearchActivity extends BaseActivity implements View.OnClickListener {

    private TextView searchTxtView;
    private ImageView searchBackView;
    private EditText keywordEditText;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_search;
    }

    @Override
    protected void initView() {
        searchTxtView = findViewById(R.id.tv_search_text);
        searchBackView = findViewById(R.id.iv_search_back);
        keywordEditText = findViewById(R.id.et_keyword);
    }

    @Override
    protected void initData() {
        super.initData();
        getSupportFragmentManager().beginTransaction()
                .add(R.id.fl_search_container, SearchHistoryFragment.newInstance())
                .commit();
        setListener();
    }

    private void setListener() {
        searchTxtView.setOnClickListener(this);
        searchBackView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_search_text) {
            String keyword = keywordEditText.getText().toString().trim();
            showSearchResultFragment(keyword);
        } else if (v.getId() == R.id.iv_search_back) {
            finish();
        }
    }

    private void showSearchResultFragment(String keyword) {
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.fl_search_container, SearchResultFragment.newInstance(keyword))
                .commit();
    }
}
