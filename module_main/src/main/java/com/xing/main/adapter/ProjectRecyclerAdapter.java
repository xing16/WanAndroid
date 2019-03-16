package com.xing.main.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.xing.main.R;
import com.xing.main.bean.ProjectResult;

import java.util.List;

public class ProjectRecyclerAdapter extends BaseQuickAdapter<ProjectResult.DatasBean, BaseViewHolder> {

    public ProjectRecyclerAdapter(int layoutResId, @Nullable List<ProjectResult.DatasBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ProjectResult.DatasBean item) {
        helper.setText(R.id.tv_project_title, item.getTitle());
        helper.setText(R.id.tv_project_author, item.getAuthor() + "\t\t" + item.getNiceDate());
        // 加载网络图片
//        Glide.with(mContext)
//                .load(item.getEnvelopePic())
//                .centerCrop()
//                .into((ImageView) helper.getView(R.id.iv_project_img));
    }
}
