package com.xing.main.contract;

import com.xing.commonbase.mvp.IView;
import com.xing.main.bean.ProjectPageItem;

import java.util.List;

public interface ProjectContract {

    interface View extends IView {
        void onProjectTabs(List<ProjectPageItem> projectPageItemList);
    }

    interface Presenter {
        void getProjectTabs();
    }
}
