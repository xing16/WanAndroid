package com.xing.module.gank.contract;

import com.xing.commonbase.mvp.IView;
import com.xing.module.gank.bean.MeiziResult;

import java.util.List;

public interface MeiziContract {

    interface View extends IView {
        void onMeiziList(List<MeiziResult> meiziResults);
    }

    interface Presenter {
        void getMeiziList(int pageSize, int page);
    }
}
