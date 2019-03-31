package com.xing.main.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xing.main.R;
import com.xing.main.bean.db.SearchHistory;

import java.util.List;

public class SearchHistoryAdapter extends BaseQuickAdapter<SearchHistory, BaseViewHolder> {

    public SearchHistoryAdapter(int layoutResId, @Nullable List<SearchHistory> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SearchHistory item) {
        helper.setText(R.id.tv_search_history_title, item.getKeyword());
        helper.addOnClickListener(R.id.iv_search_history_del);
    }
}
