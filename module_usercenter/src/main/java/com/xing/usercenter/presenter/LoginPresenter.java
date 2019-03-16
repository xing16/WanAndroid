package com.xing.usercenter.presenter;

import com.xing.commonbase.base.BaseObserver;
import com.xing.commonbase.constants.Constants;
import com.xing.commonbase.mvp.BasePresenter;
import com.xing.commonbase.util.SharedPreferenceUtil;
import com.xing.usercenter.apiservice.UserCenterApiService;
import com.xing.usercenter.bean.LoginResult;
import com.xing.usercenter.contract.LoginContract;

public class LoginPresenter extends BasePresenter<LoginContract.View>
        implements LoginContract.Presenter {

    @Override
    public void login(String username, String password) {
        addSubscribe(create(UserCenterApiService.class).login(username, password), new BaseObserver<LoginResult>(getView()) {

            @Override
            protected void onSuccess(LoginResult data) {
                if (isViewAttached()) {
                    getView().loginSuccess(data);
                }
            }
        });
    }

    /**
     * 保存 cookie 至本地
     *
     * @param result
     */
    private void saveCookie(LoginResult result) {
        if (result != null) {
//            SharedPreferenceUtils.write(Constants.File_TOKEN, Constants.ACCESS_TOKEN, result.getAccessToken());
        }
    }

    /**
     * 保存手机号和密码
     *
     * @param username
     * @param password
     */
    public void saveUsernamePassword(String username, String password) {
        SharedPreferenceUtil.write(Constants.USER_LOGIN, Constants.USERNAME, username);
        SharedPreferenceUtil.write(Constants.USER_LOGIN, Constants.PASSWORD, password);
    }

    public String readUsernamePassword(String key) {
        return SharedPreferenceUtil.read(Constants.USER_LOGIN, key, "");
    }
}
