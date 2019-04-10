package com.xing.main.contract;

import com.xing.commonbase.mvp.IView;
import com.xing.main.bean.FavoriteResult;

public interface FavoriteContract {

    interface View extends IView {
        void onFavoriteList(FavoriteResult result);
    }

    interface Presenter {
        void getFavoriteList(int page);
    }
}
