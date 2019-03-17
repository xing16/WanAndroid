package com.xing.commonbase.activity;

import android.content.Intent;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xing.commonbase.R;
import com.xing.commonbase.base.BaseActivity;
import com.xing.commonbase.widget.ProgressWebView;

@Route(path = "/web/WebViewActivity")
public class WebViewActivity extends BaseActivity {

    private ProgressWebView webView;
    private String url;
    private Toolbar toolbar;

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_web_view;
    }

    @Override
    protected void initView() {
        toolbar = findViewById(R.id.toolbar);
        webView = findViewById(R.id.wv_web);
        toolbar.setNavigationIcon(R.drawable.ic_close);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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
