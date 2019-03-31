package com.xing.main.contract;

import com.xing.commonbase.mvp.IView;
import com.xing.main.bean.SearchHotKey;
import com.xing.main.bean.db.SearchHistory;

import java.util.List;

public interface SearchHistoryContract {

    interface View extends IView {

        /**
         * 搜索热词
         */
        void onSearchHotKey(List<SearchHotKey> searchHotKeys);

        /**
         * 搜索历史
         */
        void onSearchHistory(List<SearchHistory> searchHistories);

        /**
         * 删除所有搜索历史
         */
        void onDeleteAllHistory();


    }

    interface Presenter {

        /**
         * 搜索热词
         */
        void getSearchHotKey();

        /**
         * 搜索历史
         */
        void getSearchHistory();

        /**
         * 删除所有搜索历史
         */
        void deleteAllHistory();
    }
}
