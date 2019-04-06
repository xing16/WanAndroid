package com.xing.main.presenter;

import com.xing.commonbase.base.BaseObserver;
import com.xing.commonbase.mvp.BasePresenter;
import com.xing.main.apiservice.MainApiService;
import com.xing.main.bean.SystemResult;
import com.xing.main.contract.SystemContract;

import java.util.List;

public class SystemPresenter extends BasePresenter<SystemContract.View>
        implements SystemContract.Presenter {

    @Override
    public void getSystemList() {
        addSubscribe(create(MainApiService.class).getSystemList(), new BaseObserver<List<SystemResult>>() {

            @Override
            protected void onSuccess(List<SystemResult> data) {
                if (isViewAttached()) {
                    getView().onSystemList(data);
                }
            }
        });
    }
}
