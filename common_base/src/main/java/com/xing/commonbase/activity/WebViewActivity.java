package com.xing.commonbase.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

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
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        webView = findViewById(R.id.wv_web);
//        toolbar.setNavigationIcon(R.drawable.ic_close);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        webView.setWebViewCallback(new ProgressWebView.WebViewCallback() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {

            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                toolbar.setTitle(title);
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {

            }

            @Override
            public void onPageFinished(WebView view, String url) {

            }

            @Override
            public void onLoadResource(WebView view, String url) {

            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

            }

            @Override
            public void onPageLoadComplete() {

            }

            @Override
            public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams) {
                return false;
            }

            @Override
            public void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture) {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_web_view, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.item_web_favorite) {

        }
        return true;
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_alpha, R.anim.anim_web_exit);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webView.removeAllViews();
        webView = null;
    }
}
