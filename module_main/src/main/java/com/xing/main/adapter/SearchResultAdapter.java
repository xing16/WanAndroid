package com.xing.main.adapter;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xing.main.R;
import com.xing.main.bean.SearchResult;

import java.util.List;

public class SearchResultAdapter extends BaseQuickAdapter<SearchResult.DatasBean, BaseViewHolder> {

    public SearchResultAdapter(int layoutResId, @Nullable List<SearchResult.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchResult.DatasBean item) {
        helper.setText(R.id.tv_search_title, item.getTitle())
                .setText(R.id.tv_search_desc, item.getDesc())
                .setText(R.id.tv_search_author, item.getAuthor())
                .setText(R.id.tv_search_time, item.getNiceDate());
        String envelopePic = item.getEnvelopePic();
        if (TextUtils.isEmpty(envelopePic)) {
            helper.setGone(R.id.iv_search_icon, false);
        } else {
            helper.setGone(R.id.iv_search_icon, true);
            ImageView iconImgView = helper.getView(R.id.iv_search_icon);
            Glide.with(mContext)
                    .load(envelopePic)
                    .into(iconImgView);
        }

    }


}
