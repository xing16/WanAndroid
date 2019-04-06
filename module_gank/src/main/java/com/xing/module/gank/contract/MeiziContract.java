package com.xing.module.gank.contract;

import com.xing.commonbase.mvp.IView;

public interface MeiziContract {

    interface View extends IView {
        void onMeizi();
    }

    interface Presenter {
        void getMeizi();
    }
}
