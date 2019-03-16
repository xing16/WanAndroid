package com.xing.main.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.WindowManager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.xing.commonbase.base.BaseActivity;
import com.xing.main.R;
import com.xing.main.fragment.HomeFragment;
import com.xing.main.fragment.MineFragment;
import com.xing.main.fragment.ProjectFragment;
import com.xing.main.fragment.SystemFragment;

import java.util.ArrayList;
import java.util.List;


@Route(path = "/main/MainActivity")
public class MainActivity extends BaseActivity {

    private RadioGroup radioGroup;
    private FragmentManager fragmentManager;
    private List<Fragment> fragmentList;
    private RadioButton homeRadioButton;
    private RadioButton mineRadioButton;
    private int currentSelectedId = R.id.rb_home;

    @Override
    protected int getLayoutResId() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(Color.WHITE);
//            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);//黑色
        }
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        radioGroup = findViewById(R.id.rg_radio_group);
        homeRadioButton = findViewById(R.id.rb_home);
        mineRadioButton = findViewById(R.id.rb_mine);
    }

    @Override
    protected void initData() {
        super.initData();
        fragmentManager = getSupportFragmentManager();
        createFragment();
        selectFragment(0);
        homeRadioButton.setChecked(true);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == currentSelectedId) {
                    return;
                }
                currentSelectedId = checkedId;
                if (checkedId == R.id.rb_home) {
                    selectFragment(0);
                } else if (checkedId == R.id.rb_project) {
                    selectFragment(1);
                } else if (checkedId == R.id.rb_system) {
                    selectFragment(2);
                } else if (checkedId == R.id.rb_mine) {
                    selectFragment(3);
                }
            }
        });
    }

    private void createFragment() {
        fragmentList = new ArrayList<>();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        HomeFragment homeFragment = new HomeFragment();
        ft.add(R.id.fl_main_container, homeFragment);
        fragmentList.add(homeFragment);

        ProjectFragment projectFragment = new ProjectFragment();
        ft.add(R.id.fl_main_container, projectFragment);
        fragmentList.add(projectFragment);

        SystemFragment systemFragment = new SystemFragment();
        ft.add(R.id.fl_main_container, systemFragment);
        fragmentList.add(systemFragment);

        MineFragment mineFragment = new MineFragment();
        ft.add(R.id.fl_main_container, mineFragment);
        fragmentList.add(mineFragment);

        // 提交事务
        ft.commit();
    }


    /**
     * 选中某个 Fragment
     *
     * @param index
     */
    private void selectFragment(int index) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        for (int i = 0; i < fragmentList.size(); i++) {
            if (i == index) {
                ft.show(fragmentList.get(i));
            } else {
                ft.hide(fragmentList.get(i));
            }
        }
        ft.commit();
    }
}
