package com.xing.commonbase;

import android.content.Intent;

import com.xing.commonbase.base.BaseActivity;
import com.xing.commonbase.widget.ProgressWebView;

public class WebViewActivity extends BaseActivity {

    private ProgressWebView webView;
    private String url;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initView() {
        webView = findViewById(R.id.wv_web);
    }

    @Override
    protected void initData() {
        super.initData();
        Intent intent = getIntent();
        if (intent != null) {
            url = intent.getStringExtra("url");
            webView.loadUrl(url);
        }
    }
}
