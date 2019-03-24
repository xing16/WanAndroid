package com.xing.main.fragment;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.xing.commonbase.base.BaseMVPFragment;
import com.xing.commonbase.util.StatusBarUtil;
import com.xing.commonbase.widget.LinearItemDecoration;
import com.xing.main.R;
import com.xing.main.activity.MainActivity;
import com.xing.main.adapter.HomeArticleAdapter;
import com.xing.main.bean.BannerResult;
import com.xing.main.bean.HomeArticleResult;
import com.xing.main.contract.HomeContract;
import com.xing.main.imageloader.GlideImageLoader;
import com.xing.main.presenter.HomePresenter;
import com.youth.banner.Banner;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseMVPFragment<HomePresenter> implements HomeContract.View, View.OnClickListener {

    private static final String TAG = "HomeFragment";
    private Banner banner;
    private RecyclerView recyclerView;
    private int page = 0;
    private HomeArticleAdapter homeArticleAdapter;
    private List<HomeArticleResult.DatasBean> dataList = new ArrayList<>();
    private View headerView;
    private View searchLayoutView;
    private TextView loginTxtView;
    private TextView searchTxtView;
    private ImageView logoImgView;
    private int bannerHeight;
    private LinearLayoutManager linearLayoutManager;
    private int searchLayoutHeight;

    public HomeFragment() {
    }

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_home;
    }

    @Override
    protected HomePresenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    protected void initView(View rootView) {
        logoImgView = rootView.findViewById(R.id.iv_home_logo);
        recyclerView = rootView.findViewById(R.id.rv_home);
        searchLayoutView = rootView.findViewById(R.id.rl_search_header);
        loginTxtView = rootView.findViewById(R.id.tv_home_login);
        searchTxtView = rootView.findViewById(R.id.tv_home_search);
        headerView = LayoutInflater.from(mContext).inflate(R.layout.layout_home_header, null);
        banner = headerView.findViewById(R.id.banner_home);
        float a = 1f;


//        if (background instanceof ColorDrawable) {
//            int color = ((ColorDrawable) background).getColor();
//            Log.e(TAG, "initView: color = " + color);
//            color &
//            int red = color >> 16 & 0xff;
//            int green = color >> 8 & 0xff;
//            int blue = color & 0xff;
//            red = (int) (red * a + 0.5);
//            green = (int) (green * a + 0.5);
//            blue = (int) (blue * a + 0.5);
//            int finalColor = 0xff << 24 | red << 16 | green << 8 | blue;
//            searchLayoutView.setBackgroundColor(finalColor);
//
//        }
    }

    @Override
    protected void initData() {
        RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) searchLayoutView.getLayoutParams();
        lp.topMargin = StatusBarUtil.getStatusBarHeight(mContext);
        linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        // 设置 ItemDecoration 作为分割线
        LinearItemDecoration itemDecoration = new LinearItemDecoration(mContext)
//                .itemOffsets(10, 10)   // 10dp
                .height(8f)    // dp
                .color(Color.parseColor("#66dddddd"));  // color 的 int 值，不是 R.color 中的值
        recyclerView.addItemDecoration(itemDecoration);

        //设置图片加载器
        banner.setImageLoader(new GlideImageLoader());
        // 请求 banner 数据
        presenter.getBanner();

        // 请求首页文章列表
        presenter.getHomeArticles(page);
        setListener();
    }

    private void setListener() {
        loginTxtView.setOnClickListener(this);
        searchTxtView.setOnClickListener(this);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int scrollOffset = recyclerView.computeVerticalScrollOffset();
                setSearchLayoutAlpha(scrollOffset);
            }
        });
    }

    private void setSearchLayoutAlpha(int offset) {
        if (searchLayoutHeight == 0) {
            searchLayoutHeight = searchLayoutView.getMeasuredHeight();
        }
        if (bannerHeight == 0) {
            bannerHeight = banner.getMeasuredHeight();
        }
        int maxOffset = bannerHeight - StatusBarUtil.getStatusBarHeight(mContext) - searchLayoutHeight;
        Log.e(TAG, "offset: " + offset + ", maxOffset = " + maxOffset);
        if (offset <= maxOffset) {
            float percent = offset * 1.0f / maxOffset;
            Log.e(TAG, "setSearchLayoutAlpha: percent = " + percent);
            searchLayoutView.getBackground().mutate().setAlpha((int) (255 * percent));
            loginTxtView.setTextColor(getResources().getColor(android.R.color.white));
            logoImgView.setImageResource(R.drawable.ic_home_logo_white);
            searchTxtView.setBackground(getResources().getDrawable(R.drawable.shape_home_input));
            ((MainActivity)getActivity()).setStatusBarTransparent();
        } else {
            loginTxtView.setTextColor(getResources().getColor(R.color.colorAccent));
            logoImgView.setImageResource(R.drawable.ic_home_logo_black);
            searchTxtView.setBackground(getResources().getDrawable(R.drawable.shape_home_input_dark));
            ((MainActivity)getActivity()).setStatusBarWhite();
        }
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
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    /**
     * Banner 数据成功回调
     *
     * @param bannerResults
     */
    @Override
    public void onBanner(final List<BannerResult> bannerResults) {
        if (bannerResults == null || bannerResults.size() == 0) {
            return;
        }
        List<String> images = getImages(bannerResults);
        //设置图片集合
        banner.setImages(images);
        banner.setOnBannerListener(new OnBannerListener() {
            @Override
            public void OnBannerClick(int position) {
                BannerResult bannerResult = bannerResults.get(position);
                String url = bannerResult.getUrl();
                gotoWebViewActivity(url);
            }
        });
        //banner设置方法全部调用完毕时最后调用
        banner.start();

    }

    /**
     * 首页文章列表数据回调
     *
     * @param result
     */
    @Override
    public void onHomeArticles(HomeArticleResult result) {
        if (result == null) {
            return;
        }
        dataList.addAll(result.getDatas());
        if (homeArticleAdapter == null) {
            homeArticleAdapter = new HomeArticleAdapter(R.layout.item_home_article);
            homeArticleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                    gotoWebViewActivity(dataList.get(position).getLink());
                }
            });
            homeArticleAdapter.addHeaderView(headerView);
            recyclerView.setAdapter(homeArticleAdapter);
        }
        homeArticleAdapter.setNewData(dataList);
    }

    private void gotoWebViewActivity(String link) {
        Bundle bundle = new Bundle();
        bundle.putString("url", link);
        ARouter.getInstance()
                .build("/web/WebViewActivity")
                .with(bundle)
                .navigation();
        getActivity().overridePendingTransition(R.anim.anim_web_enter, R.anim.anim_alpha);
    }

    private List<String> getImages(List<BannerResult> bannerResults) {
        List<String> list = new ArrayList<>();
        if (bannerResults != null) {
            for (BannerResult bannerResult : bannerResults) {
                list.add(bannerResult.getImagePath());
            }
        }
        return list;
    }


    public int getScollYDistance() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisiableChildView = layoutManager.findViewByPosition(position);
        int itemHeight = firstVisiableChildView.getHeight();
        return (position) * itemHeight - firstVisiableChildView.getTop();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tv_home_login) {
            gotoLoginActivity();
        } else if (v.getId() == R.id.tv_home_search) {
            gotoSearchActivity();
        }
    }

    private void gotoSearchActivity() {
        ARouter.getInstance()
                .build("/search/SearchActivity")
                .navigation();
    }

    private void gotoLoginActivity() {
        ARouter.getInstance()
                .build("/user/LoginActivity")
                .navigation();
    }
}
