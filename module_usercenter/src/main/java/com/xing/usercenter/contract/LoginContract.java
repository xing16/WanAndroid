package com.xing.usercenter.contract;

import com.xing.commonbase.mvp.IView;
import com.xing.usercenter.bean.LoginResult;

public interface LoginContract {

    interface View extends IView {
        void loginSuccess(LoginResult result);
    }

    interface Presenter {
        void login(String phone, String password);
    }
}