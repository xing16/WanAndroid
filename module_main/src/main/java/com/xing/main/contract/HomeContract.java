package com.xing.main.contract;

import com.xing.commonbase.mvp.IView;
import com.xing.main.bean.BannerResult;
import com.xing.main.bean.HomeArticleResult;
import com.xing.main.bean.WeChatAuthorResult;

import java.util.List;

public interface HomeContract {

    interface View extends IView {
        /**
         * banner 数据回调
         */
        void onBanner(List<BannerResult> bannerResults);

        /**
         * 公众号数据回调
         */
        void onWeChatAuthors(List<WeChatAuthorResult> weChatAuthorResults);


        /**
         * 首页文章列表数据回调
         *
         * @param result
         */
        void onHomeArticles(HomeArticleResult result);
    }

    interface Presenter {
        /**
         * 获取 banner 数据
         */
        void getBanner();

        /**
         * 获取公众号列表
         */
        void getWeChatAuthors();

        /**
         * 获取首页文章列表
         */
        void getHomeArticles(int page);

    }
}
