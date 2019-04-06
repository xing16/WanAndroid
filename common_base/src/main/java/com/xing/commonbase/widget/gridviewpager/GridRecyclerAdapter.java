package com.xing.commonbase.widget.gridviewpager;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public abstract class GridRecyclerAdapter<T> extends RecyclerView.Adapter<GridRecyclerAdapter.ViewHolder> {

    private Context context;
    private List<T> dataList = new ArrayList<>();
    private int page;

    public GridRecyclerAdapter(Context context) {
        this.context = context;
    }

    public GridRecyclerAdapter(Context context, List<T> list, int page,
                               GridViewPager.OnGridItemClickListener listener) {
        this.context = context;
        this.dataList = list;
        this.page = page;
        this.listener = listener;
    }

    @NonNull
    @Override
    public GridRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(context).inflate(getItemLayoutId(), viewGroup, false);
        return new GridRecyclerAdapter.ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull GridRecyclerAdapter.ViewHolder viewHolder, final int position) {
        T data = dataList.get(position);
        bindData(viewHolder, data, position);
        // 设置监听
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    int index = page * 10 + position;
                    listener.onGridItemClick(index, v);
                }
            }
        });
    }


    public abstract int getItemLayoutId();

    public abstract void bindData(ViewHolder viewHolder, T t, int position);

    private GridViewPager.OnGridItemClickListener listener;

    @Override
    public int getItemCount() {
        return dataList == null ? 0 : dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        View itemView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
        }

        public <V extends View> V getView(int id) {
            return itemView.findViewById(id);
        }

        public ViewHolder setText(int id, String text) {
            TextView textView = getView(id);
            textView.setText(text);
            return this;
        }

        public ViewHolder setImageResource(int id, int resid) {
            ImageView imageView = getView(id);
            imageView.setImageResource(resid);
            return this;
        }

        public ViewHolder setBackgroundColor(int id, int color) {
            View view = getView(id);
            view.setBackgroundColor(color);
            return this;
        }

        public ViewHolder setBackground(int id, Drawable drawable) {
            View view = getView(id);
            view.setBackground(drawable);
            return this;
        }
    }
}


