package com.xing.commonbase.widget.flow;

import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class FlowAdapter<T> {

    private List<T> dataList;

    public FlowAdapter(List<T> list) {
        this.dataList = list;
    }

    public FlowAdapter(T[] list) {
        this.dataList = new ArrayList<>(Arrays.asList(list));
    }

    public T getItem(int position) {
        return dataList.get(position);
    }

    public int getCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public abstract View getView(int position, T t, ViewGroup parent);

}
