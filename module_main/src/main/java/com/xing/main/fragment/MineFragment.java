package com.xing.main.fragment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.xing.commonbase.base.BaseFragment;
import com.xing.commonbase.util.BlurUtil;
import com.xing.commonbase.widget.ItemView;
import com.xing.commonbase.widget.ZoomScrollView;
import com.xing.main.R;

public class MineFragment extends BaseFragment implements View.OnClickListener {

    private ImageView backImgView;
    private ZoomScrollView scrollView;
    private View avatarLayout;
    private TextView meiziView;
    private ItemView favoriteItemView;
    private ItemView aboutItemView;

    public MineFragment() {
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View rootView) {
        backImgView = rootView.findViewById(R.id.iv_avatar_background);
        scrollView = rootView.findViewById(R.id.sv_scroll);
//        avatarLayout = rootView.findViewById(R.id.rl_layout);
        favoriteItemView = rootView.findViewById(R.id.iv_mine_favorite);
        aboutItemView = rootView.findViewById(R.id.iv_mine_about);
    }

    @Override
    protected void initData() {
        scrollView.setZoomView(backImgView);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.test);
        backImgView.setImageBitmap(BlurUtil.blur(mContext, bitmap, 18));


        favoriteItemView.setOnClickListener(this);
        aboutItemView.setOnClickListener(this);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.iv_mine_about) {
            gotoAboutActivity();
        } else if (v.getId() == R.id.iv_mine_favorite) {
            gotoFavoriteActivity();
        }
    }

    private void gotoFavoriteActivity() {
        ARouter.getInstance()
                .build("/favorite/FavoriteActivity")
                .navigation();
    }

    private void gotoAboutActivity() {
        ARouter.getInstance()
                .build("/about/AboutActivity")
                .navigation();
    }
}
