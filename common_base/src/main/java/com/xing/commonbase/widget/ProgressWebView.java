package com.xing.commonbase.widget;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import java.util.HashMap;
import java.util.Map;

public class ProgressWebView extends WebView {

    private static final String TAG = "FlagWebView";
    private Context mContext;
    private WebProgressBar progressBar;
    private boolean isStarted = false;
    private boolean isProgressBarVisible = true;     // 进度条是否可见
    private WebViewCallback callback;
    private Handler progressHandler;

    public ProgressWebView(Context context) {
        this(context, null);
    }

    public ProgressWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        initWebSettings();
        initProgressBar();
        setWebViewClient(new FlagWebViewClient());
        setWebChromeClient(new FlagWebChromeClient());
    }

    private void initWebSettings() {
        getSettings().setJavaScriptEnabled(true);
        getSettings().setSupportZoom(true);           // 设置是否支持缩放
        getSettings().setDisplayZoomControls(false);
        getSettings().setBuiltInZoomControls(true);
        getSettings().setAllowFileAccess(true);       // 是否允许访问文件
        getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        //缓存
        getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        getSettings().setDomStorageEnabled(true);
        getSettings().setDatabaseEnabled(true);
        getSettings().setAppCacheEnabled(true);
        getSettings().setUseWideViewPort(true);
        getSettings().setLoadWithOverviewMode(true);
        getSettings().setLoadsImagesAutomatically(true);
        getSettings().setBlockNetworkImage(false);
        getSettings().setBlockNetworkLoads(false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getSettings().setMixedContentMode(android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
    }

    private void initProgressBar() {
        progressBar = new WebProgressBar(mContext);
        progressBar.setLayoutParams(new ViewGroup.LayoutParams
                (ViewGroup.LayoutParams.MATCH_PARENT, 10));
        //添加进度条至WebView中
        addView(progressBar);

        progressHandler = new Handler(Looper.getMainLooper());
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            progressBar.setVisibility(View.GONE);
        }
    };


    /**
     * 设置进度条是否可见
     */
    public void setProgressBarVisible(boolean visible) {
        if (visible) {   // 设置 ProgressBar 可见，当前没有进度条，则添加进度条
            if (!isProgressBarVisible) {
                addView(progressBar);
            }
        } else {    // 设置不显示进度条，当前有进度条，则移除
            if (isProgressBarVisible) {
                removeView(progressBar);
            }
        }
        isProgressBarVisible = visible;
    }


    public void setWebViewCallback(WebViewCallback callback) {
        this.callback = callback;
    }

    /**
     * 属性动画作用于 progress 属性
     *
     * @param newProgress:新进度
     * @param currentProgress:当前进度
     */
    private void startProgressAnimation(int newProgress, int currentProgress) {
        ObjectAnimator animator = ObjectAnimator.ofInt(progressBar, "progress", currentProgress, newProgress);
        animator.setDuration(300);
        animator.setInterpolator(new DecelerateInterpolator()); // 减速形式的加速器
        animator.start();
    }

    class FlagWebViewClient extends WebViewClient {

        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            Map<String, String> map = new HashMap<>();
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (callback != null) {
                callback.onPageStarted(view, url, favicon);
            }
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (callback != null) {
                callback.onPageFinished(view, url);
            }
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);
            if (callback != null) {
                callback.onLoadResource(view, url);
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);
            if (callback != null) {
                callback.onReceivedError(view, errorCode, description, failingUrl);
            }
        }

        @Override
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            sslErrorHandler.proceed();
        }
    }

    class FlagWebChromeClient extends WebChromeClient {

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            Log.e(TAG, "onProgressChanged:newProgress  " + newProgress);
            if (isProgressBarVisible && progressBar != null) {
                String url = view.getUrl();
                progressBar.setVisibility(View.VISIBLE);
                int currentProgress = progressBar.getProgress();
                if (!TextUtils.isEmpty(url)) {
                    // 加载本地 html,progress 是依次增长的
                    if (url.startsWith("file")) {
                        startProgressAnimation(newProgress, currentProgress);
                        if (newProgress == 100) {
                            progressHandler.postDelayed(runnable, 100);
                        }
                    } else {  // 加载网络 html,progress 是反复增加的
                        if (newProgress >= 100 && !isStarted) {
                            isStarted = true;
                        } else {
                            if (newProgress < currentProgress) {
                                return;
                            }
                            startProgressAnimation(newProgress, currentProgress);
                            if (newProgress == 100) {
                                // 判断页面是否加载完成，不使用 onPageFinished(),因为 onPageFinished() 会回调两次
                                if (callback != null) {
                                    callback.onPageLoadComplete();
                                }
                                progressHandler.postDelayed(runnable, 100);
                            }
                        }
                    }
                }
            }
            if (callback != null) {
                callback.onProgressChanged(view, newProgress);
            }
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
            if (callback != null) {
                callback.onReceivedTitle(view, title);
            }
        }

        // Android 5.0+
        @Override
        public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, FileChooserParams fileChooserParams) {
            if (callback != null) {
                callback.onShowFileChooser(webView, valueCallback, fileChooserParams);
            }
            return true;
        }
    }


    public interface WebViewCallback {

        void onProgressChanged(WebView view, int newProgress);

        void onReceivedTitle(WebView view, String title);

        void onPageStarted(WebView view, String url, Bitmap favicon);

        void onPageFinished(WebView view, String url);

        void onLoadResource(WebView view, String url);

        void onReceivedError(WebView view, int errorCode, String description, String failingUrl);

        /**
         * 页面加载完成,不使用 onPageFinish() 因为 onPageFinish() 会被回调两次
         */
        void onPageLoadComplete();

        // Android 5.0+
        boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> valueCallback, WebChromeClient.FileChooserParams fileChooserParams);

        // Android 4.1+
        void openFileChooser(ValueCallback<Uri> valueCallback, String acceptType, String capture);
    }
}
