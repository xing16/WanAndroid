package com.xing.usercenter.activity;

import android.os.Handler;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xing.commonbase.base.BaseActivity;
import com.xing.module.usercenter.R;


public class SplashActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initView() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoMainActivity();
            }
        }, 2000);
    }

    private void gotoMainActivity() {
        ARouter.getInstance().build("/main/MainActivity").navigation();
//        ARouter.getInstance().build("/user/LoginActivity").navigation();
        finish();
    }
}
