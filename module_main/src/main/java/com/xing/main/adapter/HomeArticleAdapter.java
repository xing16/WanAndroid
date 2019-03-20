package com.xing.main.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xing.main.R;
import com.xing.main.bean.HomeArticleResult;

import java.util.List;

public class HomeArticleAdapter extends BaseQuickAdapter<HomeArticleResult.DatasBean, BaseViewHolder> {

    public HomeArticleAdapter(int layoutResId) {
        super(layoutResId);
    }

    public HomeArticleAdapter(int layoutResId, @Nullable List<HomeArticleResult.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HomeArticleResult.DatasBean item) {
        helper.setText(R.id.tv_article_title, item.getTitle())
                .setText(R.id.tv_article_author, item.getAuthor());
    }
}
