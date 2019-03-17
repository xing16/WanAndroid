package com.xing.main.adapter;


import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.xing.main.bean.ProjectPageItem;

import java.util.List;

public class ProjectPagerAdapter extends FragmentPagerAdapter {

    private List<ProjectPageItem> projectPageItems;

    public ProjectPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    public void setPages(List<ProjectPageItem> pageItemList) {
        this.projectPageItems = pageItemList;
        notifyDataSetChanged();
    }

    @Override
    public Fragment getItem(int i) {
        return projectPageItems.get(i).getFragment();
    }

    @Override
    public int getCount() {
        return projectPageItems == null ? 0 : projectPageItems.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return projectPageItems.get(position).getName();
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
    }
}
