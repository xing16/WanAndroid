package com.xing.module.gank.presenter;

import com.xing.commonbase.base.BaseObserver;
import com.xing.commonbase.mvp.BasePresenter;
import com.xing.module.gank.apiservice.GankApiService;
import com.xing.module.gank.bean.MeiziResult;
import com.xing.module.gank.contract.MeiziContract;

import java.util.List;

public class MeiziPresenter extends BasePresenter<MeiziContract.View> implements MeiziContract.Presenter {

    @Override
    public void getMeiziList(int pageSize, int page) {
        addSubscribe(create(GankApiService.class).getMeiziList(pageSize, page),
                new BaseObserver<List<MeiziResult>>() {

                    @Override
                    protected void onSuccess(List<MeiziResult> data) {
                        if (isViewAttached()) {
                            getView().onMeiziList(data);
                        }
                    }
                });
    }
}
