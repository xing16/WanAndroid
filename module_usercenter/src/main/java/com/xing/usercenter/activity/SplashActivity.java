package com.xing.usercenter.activity;

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
    }

    @Override
    protected void onResume() {
        super.onResume();
        gotoMainActivity();
    }

    private void gotoMainActivity() {
        ARouter.getInstance().build("/main/MainActivity").navigation();
        finish();
    }
}
