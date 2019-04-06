package com.xing.main.contract;

import com.xing.commonbase.mvp.IView;
import com.xing.main.bean.WeChatArticleResult;

public interface WeChatArticleListContract {

    interface View extends IView {
        void onWeChatArticleList(WeChatArticleResult result);
    }

    interface Presenter {
        void getWeChatArticle(int id, int page);
    }
}
