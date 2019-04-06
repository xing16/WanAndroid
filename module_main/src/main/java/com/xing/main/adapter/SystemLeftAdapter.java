package com.xing.main.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xing.main.R;
import com.xing.main.bean.SystemResult;

import java.util.List;

/**
 * 体系中一级列表(左侧) recyclerview 适配器
 */
public class SystemLeftAdapter extends BaseQuickAdapter<SystemResult, BaseViewHolder> {

    public SystemLeftAdapter(int layoutResId, @Nullable List<SystemResult> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, SystemResult item) {
        helper.setText(R.id.tv_system_left_title, item.getName());
    }
}
