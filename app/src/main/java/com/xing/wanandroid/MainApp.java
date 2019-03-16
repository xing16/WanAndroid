package com.xing.wanandroid;

import android.content.Context;

import com.xing.commonbase.base.BaseApplication;

public class MainApp extends BaseApplication {

    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getAppContext() {
        return mContext;
    }

}
