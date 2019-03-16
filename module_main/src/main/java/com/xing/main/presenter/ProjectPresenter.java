package com.xing.main.presenter;

import com.xing.commonbase.base.BaseObserver;
import com.xing.commonbase.mvp.BasePresenter;
import com.xing.main.apiservice.MainApiService;
import com.xing.main.bean.ProjectPageItem;
import com.xing.main.bean.ProjectTabItem;
import com.xing.main.contract.ProjectContract;
import com.xing.main.fragment.ProjectPageFragment;

import java.util.ArrayList;
import java.util.List;

public class ProjectPresenter extends BasePresenter<ProjectContract.View> implements ProjectContract.Presenter {


    /**
     * project 栏目
     */
    @Override
    public void getProjectTabs() {
        addSubscribe(create(MainApiService.class).getProjectTabs(), new BaseObserver<List<ProjectTabItem>>() {
            @Override
            protected void onSuccess(List<ProjectTabItem> data) {
                List<ProjectPageItem> projectPageItemList = createProjectPages(data);
                if (isViewAttached()) {
                    getView().onProjectTabs(projectPageItemList);
                }

            }
        });
    }

    private List<ProjectPageItem> createProjectPages(List<ProjectTabItem> projectItems) {
        if (projectItems == null || projectItems.size() == 0) {
            return new ArrayList<>();
        }
        List<ProjectPageItem> projectPageItemList = new ArrayList<>();
        for (ProjectTabItem projectItem : projectItems) {
            ProjectPageItem projectPageItem = new ProjectPageItem(projectItem.getId(),
                    projectItem.getName(), ProjectPageFragment.newInstance(projectItem.getId()));
            projectPageItemList.add(projectPageItem);
        }
        return projectPageItemList;
    }
}
