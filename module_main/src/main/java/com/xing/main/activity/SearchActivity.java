package com.xing.main.activity;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xing.commonbase.base.BaseActivity;
import com.xing.commonbase.util.SoftKeyboardUtil;
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
        keywordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String keyword = s.toString().trim();
                if (TextUtils.isEmpty(keyword)) {
                    // 显示搜索历史 Fragment
                    showSearchHistoryFragment();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_search_text) {
            String keyword = keywordEditText.getText().toString().trim();
            showSearchResultFragment(keyword);
            hideSoftKeyboard();
        } else if (v.getId() == R.id.iv_search_back) {
            finish();
        }
    }

    /**
     * 隐藏软键盘
     */
    private void hideSoftKeyboard() {
        SoftKeyboardUtil.hideSoftKeyboard(searchTxtView);
    }

    /**
     * 显示搜索结果 Fragment
     *
     * @param keyword
     */
    private void showSearchResultFragment(String keyword) {
        
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.fl_search_container, SearchResultFragment.newInstance(keyword))
                .commit();
    }

    /**
     * 显示搜索历史 Fragment
     */
    public void showSearchHistoryFragment() {
        getSupportFragmentManager()
                .beginTransaction().replace(R.id.fl_search_container, SearchHistoryFragment.newInstance())
                .commit();
    }

}
