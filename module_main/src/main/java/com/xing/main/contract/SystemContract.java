package com.xing.main.contract;

import com.xing.commonbase.mvp.IView;
import com.xing.main.bean.SystemResult;

import java.util.List;

/**
 * 体系 contract
 */
public interface SystemContract {

    interface View extends IView {
        void onSystemList(List<SystemResult> systemResults);
    }

    interface Presenter {
        /**
         * 获取体系分类列表
         */
        void getSystemList();
    }
}
