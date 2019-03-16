package com.xing.commonbase.base;

import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;

public class BaseApplication extends Application {

    private static BaseApplication application;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        initRouter(this);
    }

    /**
     * 初始化 ARouter
     *
     * @param baseApplication
     */
    private void initRouter(BaseApplication baseApplication) {
        if (isDebug()) {           // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(baseApplication); // 尽可能早，推荐在Application中初始化
    }

    public static BaseApplication getApplication() {
        return application;
    }

    private boolean isDebug() {
        return true;
    }
}
