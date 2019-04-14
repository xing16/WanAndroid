package com.xing.commonbase.interceptor;

import android.content.Context;
import android.util.Log;

import com.alibaba.android.arouter.facade.Postcard;
import com.alibaba.android.arouter.facade.callback.InterceptorCallback;
import com.alibaba.android.arouter.facade.template.IInterceptor;

/**
 * 登录拦截器
 */
//@Interceptor(name = "loginInterceptor", priority = 2)
public class LoginInterceptor implements IInterceptor {
    private static final String TAG = "LoginInterceptor";

    @Override
    public void process(Postcard postcard, InterceptorCallback callback) {
        String path = postcard.getPath();

        callback.onContinue(postcard);
//        // 已经登录
//        if (UserLoginManager.getInstance().isLoggedin()) {
//            callback.onContinue(postcard);
//        } else {   // 没有登录
//
//        }
    }

    @Override
    public void init(Context context) {
        Log.e(TAG, "init: ");
    }
}
