package com.xing.main.activity;

import android.support.v7.widget.Toolbar;
import android.view.View;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xing.commonbase.base.BaseActivity;
import com.xing.main.R;

@Route(path = "/about/AboutActivity")
public class AboutActivity extends BaseActivity {

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_about;
    }

    @Override
    protected void initView() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.about);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
