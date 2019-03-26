package com.xing.main.contract;

import com.xing.commonbase.mvp.IView;
import com.xing.main.bean.SearchResult;

public interface SearchResultContract {

    interface View extends IView {
        void onSearchResult(SearchResult searchResults);
    }

    interface Presenter {
        /**
         * 保存搜索历史记录
         *
         * @param keyword
         */
        void saveSearchHistory(String keyword);

        /**
         * 获取搜索结果
         *
         * @param page
         * @param keyword
         */
        void getSearchResult(int page, String keyword);
    }
}
