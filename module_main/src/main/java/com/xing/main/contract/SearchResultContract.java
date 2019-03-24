package com.xing.main.contract;

import com.xing.commonbase.mvp.IView;
import com.xing.main.bean.SearchResult;

public interface SearchResultContract {

    interface View extends IView {
        void onSearchResult(SearchResult searchResults);
    }

    interface Presenter {
        void getSearchResult(int page, String keyword);
    }
}
