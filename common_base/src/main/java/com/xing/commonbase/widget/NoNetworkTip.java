package com.xing.commonbase.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.provider.Settings;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.xing.commonbase.R;

import java.lang.ref.WeakReference;

/**
 * 显示网络链接异常的提示条
 */
public class NoNetworkTip {

    private WindowManager windowManager;
    private WeakReference<Activity> activityWeakReference;
    private boolean isShowing = false;
    private View view;
    private WindowManager.LayoutParams layoutParams;

    public NoNetworkTip(Activity activity) {
        activityWeakReference = new WeakReference<>(activity);
        windowManager = (WindowManager) activity.getSystemService(Context.WINDOW_SERVICE);
        layoutParams = new WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_APPLICATION,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);
        layoutParams.gravity = Gravity.START | Gravity.TOP;
//        layoutParams.x = 100;
        layoutParams.horizontalMargin = 100;
        layoutParams.horizontalWeight = 0.8f;
        layoutParams.y = 150;
        initView();
    }

    public void initView() {
        final Activity activity = activityWeakReference.get();
        if (activity != null) {
            view = LayoutInflater.from(activity).inflate(R.layout.layout_no_network_tip, null);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // 跳转至设置页面
                    Intent intent = new Intent(Settings.ACTION_SETTINGS);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intent);
                }
            });
        }
    }


    public void show() {
        isShowing = true;
        if (view == null) {
            initView();
        }
        if (view != null) {
            windowManager.addView(view, layoutParams);
        }
    }

    public void dismiss() {
        isShowing = false;
        windowManager.removeViewImmediate(view);
        view = null;
    }

    public boolean isShowing() {
        return isShowing;
    }



}
