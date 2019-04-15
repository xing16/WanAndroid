package com.xing.main.contract;

import com.xing.commonbase.mvp.IView;
import com.xing.main.bean.ProjectResult;

public interface ProjectPageContract {

    interface View extends IView {
        void onProjectList(ProjectResult projectResult);
    }

    interface Presenter {
        void getProjects(int id, int page);
    }
}
