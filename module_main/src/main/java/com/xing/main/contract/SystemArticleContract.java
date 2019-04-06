package com.xing.main.contract;

import com.xing.commonbase.mvp.IView;
import com.xing.main.bean.SystemArticleResult;

public interface SystemArticleContract {

    interface View extends IView {
        void onSystemArticleList(SystemArticleResult result);
    }

    interface Presenter {
        void getSystemArticleList(int page, int id);
    }
}
