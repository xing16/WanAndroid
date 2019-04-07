package com.xing.main.fragment;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import com.xing.commonbase.base.BaseMVPFragment;
import com.xing.main.R;
import com.xing.main.adapter.ProjectPagerAdapter;
import com.xing.main.bean.ProjectPageItem;
import com.xing.main.contract.ProjectContract;
import com.xing.main.presenter.ProjectPresenter;

import java.util.List;


public class ProjectFragment extends BaseMVPFragment<ProjectPresenter>
        implements ProjectContract.View {

    private View mFakeStatusBar;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    public ProjectFragment() {
    }

    @Override
    protected ProjectPresenter createPresenter() {
        return new ProjectPresenter();
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_project;
    }

    @Override
    protected void initView(View rootView) {
        mFakeStatusBar = rootView.findViewById(R.id.fake_status_bar);
        tabLayout = rootView.findViewById(R.id.tl_project);
        viewPager = rootView.findViewById(R.id.vp_project_page);
        mFakeStatusBar.post(new Runnable() {
            @Override
            public void run() {
                int height = mFakeStatusBar.getHeight();
                Log.e("debufgdbug", "run: " + height);
            }
        });
    }

    @Override
    protected void initData() {
        presenter.getProjectTabs();
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

    @Override
    public void onProjectTabs(List<ProjectPageItem> projectPageItemList) {
        ProjectPagerAdapter pagerAdapter = new ProjectPagerAdapter(getChildFragmentManager());
        pagerAdapter.setPages(projectPageItemList);
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void setStatusBarColor(int color) {
        mFakeStatusBar.setBackgroundColor(color);
    }

}
