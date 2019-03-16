package com.xing.usercenter.presenter;

import com.xing.commonbase.base.BaseObserver;
import com.xing.commonbase.mvp.BasePresenter;
import com.xing.usercenter.apiservice.UserCenterApiService;
import com.xing.usercenter.bean.RegisterResult;
import com.xing.usercenter.contract.RegisterContract;

public class RegisterPresenter extends BasePresenter<RegisterContract.View>
        implements RegisterContract.Presenter {

    private final UserCenterApiService apiService;

    public RegisterPresenter() {
        apiService = create(UserCenterApiService.class);
    }

    @Override
    public void register(String username, String password, String repassword) {
        addSubscribe(apiService.register(username, password, repassword), new BaseObserver<RegisterResult>(getView()) {

            @Override
            protected void onSuccess(RegisterResult data) {
                if (isViewAttached()) {
                    getView().registerSuccess(data);
                }
            }
        });
    }
}