package com.xing.module.gank.adapter;

import android.support.annotation.Nullable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.github.chrisbanes.photoview.OnOutsidePhotoTapListener;
import com.github.chrisbanes.photoview.OnPhotoTapListener;
import com.github.chrisbanes.photoview.PhotoView;
import com.xing.module.gank.R;
import com.xing.module.gank.activity.ImagePreviewActivity;
import com.xing.module.gank.bean.MeiziResult;

import java.util.List;

public class ImagePreviewAdapter extends BaseQuickAdapter<MeiziResult, BaseViewHolder> {

    public ImagePreviewAdapter(int layoutResId, @Nullable List<MeiziResult> data) {
        super(layoutResId, data);
    }

    public ImagePreviewAdapter(@Nullable List<MeiziResult> data) {
        super(data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MeiziResult item) {
        PhotoView photoView = helper.getView(R.id.pv_photo);
        photoView.setOnOutsidePhotoTapListener(new OnOutsidePhotoTapListener() {
            @Override
            public void onOutsidePhotoTap(ImageView imageView) {
                finishActivity();
            }
        });
        photoView.setOnPhotoTapListener(new OnPhotoTapListener() {
            @Override
            public void onPhotoTap(ImageView view, float x, float y) {
                finishActivity();
            }
        });
        Glide.with(mContext)
                .load(item.getUrl())
                .into(photoView);

    }

    private void finishActivity() {
        if (mContext instanceof ImagePreviewActivity) {
            ((ImagePreviewActivity) mContext).finish();
        }
    }

}
