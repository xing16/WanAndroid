package com.xing.main.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xing.main.R;
import com.xing.main.bean.FavoriteResult;

import java.util.List;

public class FavoriteAdapter extends BaseQuickAdapter<FavoriteResult.DatasBean, BaseViewHolder> {


    public FavoriteAdapter(int layoutResId, @Nullable List<FavoriteResult.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, FavoriteResult.DatasBean item) {
        helper.setText(R.id.tv_article_title, item.getTitle())
                .setText(R.id.tv_article_author, item.getAuthor())
                .setText(R.id.tv_article_time, item.getNiceDate());
    }
}
