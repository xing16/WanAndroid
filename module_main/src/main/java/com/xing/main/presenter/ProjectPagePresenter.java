package com.xing.main.presenter;

import com.xing.commonbase.base.BaseObserver;
import com.xing.commonbase.mvp.BasePresenter;
import com.xing.main.apiservice.MainApiService;
import com.xing.main.bean.ProjectResult;
import com.xing.main.contract.ProjectPageContract;

public class ProjectPagePresenter extends BasePresenter<ProjectPageContract.View>
        implements ProjectPageContract.Presenter {

    @Override
    public void getData(int id, int page) {
        addSubscribe(create(MainApiService.class).getProjects(page, id), new BaseObserver<ProjectResult>() {

            @Override
            protected void onSuccess(ProjectResult data) {
                if (isViewAttached()) {
                    getView().onProjectList(data);
                }
            }
        });
    }
}
