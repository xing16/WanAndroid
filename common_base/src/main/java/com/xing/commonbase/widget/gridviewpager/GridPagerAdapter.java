package com.xing.commonbase.widget.gridviewpager;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xing.commonbase.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 外层 ViewPager 适配器
 */
public abstract class GridPagerAdapter<T> extends PagerAdapter {

    private Context context;
    private List<T> dataList;
    private int itemLayoutId;
    private int columnSize;
    private int rowSize;
    private int eachPageSize;
    private GridViewPager.OnGridItemClickListener listener;

    public GridPagerAdapter(Context context, List<T> list, int itemLayoutId, int columnSize, int rowSize,
                            GridViewPager.OnGridItemClickListener listener) {
        this.context = context;
        this.dataList = list;
        this.columnSize = columnSize;
        this.rowSize = rowSize;
        this.listener = listener;
        if (itemLayoutId == 0) {
            throw new IllegalArgumentException("the item layout id should be set");
        }
        this.itemLayoutId = itemLayoutId;
        eachPageSize = columnSize * rowSize;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        final View view = LayoutInflater.from(context).inflate(R.layout.item_grid_viewpager, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.rv_pager);
        recyclerView.setLayoutManager(new GridLayoutManager(context, columnSize));
        int itemCount = getCount();
        int end;
        if (position == itemCount - 1) {
            end = position * eachPageSize + dataList.size() % 10;
        } else {
            end = position * eachPageSize + eachPageSize;
        }
        final List<T> list = new ArrayList<>();
        for (int i = position * eachPageSize; i < end; i++) {
            list.add(dataList.get(i));
        }
        recyclerView.setAdapter(new GridRecyclerAdapter<T>(context, list, position, listener) {

            @Override
            public int getItemLayoutId() {
                return itemLayoutId;
            }

            @Override
            public void bindData(ViewHolder viewHolder, T o, int position) {
                bind(viewHolder, o, position);
            }
        });
        container.addView(view);
        return view;
    }

    public abstract void bind(GridRecyclerAdapter.ViewHolder viewHolder, T t, int position);

    @Override
    public int getCount() {
        return dataList == null ? 0 : dataList.size() % eachPageSize == 0 ? dataList.size() / eachPageSize :
                dataList.size() / eachPageSize + 1;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
//        super.destroyItem(container, position, object);
    }
}
