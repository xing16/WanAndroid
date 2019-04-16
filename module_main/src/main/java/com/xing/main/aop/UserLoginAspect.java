package com.xing.main.aop;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xing.commonbase.base.BaseApplication;
import com.xing.commonbase.manager.UserLoginManager;
import com.xing.commonbase.util.ToastUtil;
import com.xing.main.annotation.UserLoginTrace;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import java.lang.reflect.Method;

@Aspect
public class UserLoginAspect {

    private static final String TAG = "UserLoginAspect";

    /**
     * 定义切点，标记切点为所有被 @UserLoginTrace 注解修饰的方法
     */
    @Pointcut("execution(@com.xing.main.annotation.UserLoginTrace * *(..))")
    public void loginPointcut() {

    }

    /**
     * 处理 UserLoginTrace 注解的切点方法
     *
     * @param joinPoint
     */
    @Around("loginPointcut()")
    public void handleLoginPointcut(ProceedingJoinPoint joinPoint) throws Throwable {
        Log.e(TAG, "handleLoginPointcut: ======== ");
        ToastUtil.show(BaseApplication.getApplication(), "joinpoint");
        // 已经登录，执行原来逻辑
        if (UserLoginManager.getInstance().isLoggedin()) {
            joinPoint.proceed();
        } else {   // 否则拦截处理
            // 获取注解参数
            MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
            Method method = methodSignature.getMethod();
            // 只处理标有  UserLoginTrace 注解修饰的方法
            if (!method.isAnnotationPresent(UserLoginTrace.class)) {
                return;
            }
            // 得到 UserLoginTrace 注解对象
            UserLoginTrace annotation = method.getAnnotation(UserLoginTrace.class);
            // 获取注解值
            long value = annotation.value();
            Context context = getContext(joinPoint);
            if (context != null) {
                handleLogin(context, value);
            }
        }
    }

    private Context getContext(ProceedingJoinPoint joinPoint) {
        Object object = joinPoint.getThis();
        if (object instanceof Activity) {
            return ((Activity) object);
        } else if (object instanceof Fragment) {
            return ((Fragment) object).getActivity();
        } else if (object instanceof android.app.Fragment) {
            return ((android.app.Fragment) object).getActivity();
        } else if (object instanceof View) {
            return ((View) object).getContext();
        }
        return null;
    }

    /**
     * 根据具体业务逻辑处理，是跳转到 LoginActivity， 还是弹出提示框
     *
     * @param context
     * @param value
     */
    private void handleLogin(Context context, long value) {
        if (value == 0) {
            ARouter.getInstance()
                    .build("/user/LoginActivity")
                    .navigation();
        } else {
            ToastUtil.show(context, "请先进行登录");
        }
    }

}
