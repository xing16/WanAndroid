package com.xing.commonbase.base;

import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xing.commonbase.receiver.NetworkChangeReceiver;

public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;
    private NetworkChangeReceiver receiver;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
        mContext = this;
        registerNetworkChangeReceiver();
        initView();
        initData();
    }

    protected abstract int getLayoutResId();

    protected abstract void initView();

    protected void initData() {

    }

    /**
     * 注册网络监听广播
     */
    private void registerNetworkChangeReceiver() {
        receiver = new NetworkChangeReceiver(this);
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(receiver,filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (receiver != null) {
            unregisterReceiver(receiver);
            receiver.onDestroy();
            receiver = null;
        }
    }
}
