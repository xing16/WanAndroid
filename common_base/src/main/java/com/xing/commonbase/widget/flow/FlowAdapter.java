package com.xing.commonbase.widget.flow;

import android.view.View;
import android.view.ViewGroup;

public abstract class FlowAdapter {

    public abstract int getCount();

    public abstract View getView(int position, ViewGroup parent);
}
