package com.xing.main.contract;

import com.xing.commonbase.mvp.IView;
import com.xing.main.bean.db.SearchHistory;

import java.util.List;

public interface SearchHistoryContract {

    interface View extends IView {
        void onSearchHistory(List<SearchHistory> searchHistories);
    }

    interface Presenter {


        void getSearchHistory();

        void getSearchSuggestion();
    }
}
