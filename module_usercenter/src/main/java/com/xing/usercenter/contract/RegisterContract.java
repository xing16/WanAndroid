package com.xing.usercenter.contract;

import com.xing.commonbase.mvp.IView;
import com.xing.usercenter.bean.RegisterResult;

public interface RegisterContract {

    interface View extends IView {
        void registerSuccess(RegisterResult result);
    }

    interface Presenter {
        void register(String phone, String password, String repassword);
    }
}