package com.xing.module.gank.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xing.module.gank.R;
import com.xing.module.gank.bean.MeiziResult;

import java.util.List;

public class MeiziAdapter extends BaseQuickAdapter<MeiziResult, BaseViewHolder> {

    public MeiziAdapter(int layoutResId, @Nullable List<MeiziResult> data) {
        super(layoutResId, data);
    }

    public MeiziAdapter(@Nullable List<MeiziResult> data) {
        super(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeiziResult item) {
        ImageView imageView = helper.getView(R.id.iv_meizi_image);
        Glide.with(mContext)
                .load(item.getUrl())
                .into(imageView);

    }
}
