package com.xing.main.contract;

import com.xing.commonbase.mvp.IView;

public interface SearchResultContract {

    interface View extends IView {
    }

    interface Presenter {
        void getSearchResult(String keyword);
    }
}
