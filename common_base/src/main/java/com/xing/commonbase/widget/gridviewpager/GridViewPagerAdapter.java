package com.xing.commonbase.widget.gridviewpager;

import java.util.List;

public abstract class GridViewPagerAdapter<T> {

    private List<T> list;

    public GridViewPagerAdapter(List<T> list) {
        this.list = list;
    }

    public List<T> getDataList() {
        return list;
    }

    public abstract void bindData(GridRecyclerAdapter.ViewHolder viewHolder, T t, int position);

}