package com.xing.commonbase.widget.gridviewpager;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.xing.commonbase.R;
import com.xing.commonbase.widget.IndicatorView;


public class GridViewPager extends RelativeLayout {

    private Context context;
    private ViewPager viewPager;
    private int itemLayoutId;
    private IndicatorView indicatorView;
    /**
     * 列数
     */
    private int columnSize;
    /**
     * 行数
     */
    private int rowSize;

    public GridViewPager(Context context) {
        this(context, null);
    }

    public GridViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        readAttrs(context, attrs);
        init();
    }

    private void readAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.GridViewPager);
        itemLayoutId = typedArray.getResourceId(R.styleable.GridViewPager_itemLayoutId, 0);
        columnSize = typedArray.getInt(R.styleable.GridViewPager_columnSize, 5);
        rowSize = typedArray.getInt(R.styleable.GridViewPager_rowSize, 2);
        typedArray.recycle();
    }

    private void init() {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_grid_viewpager, this, true);
        viewPager = view.findViewById(R.id.vp_grid_viewpager);
        indicatorView = view.findViewById(R.id.iv_grid_indicator);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int height;
        if (heightMeasureSpec == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            for (int i = 0; i < getChildCount(); i++) {
                View view = getChildAt(i);
                measureChild(view, widthMeasureSpec, heightMeasureSpec);
            }
            height = viewPager.getMeasuredHeight() + indicatorView.getMeasuredHeight();
        }
        setMeasuredDimension(widthMeasureSpec, height);
    }

    public <T> void setAdapter(final GridViewPagerAdapter<T> adapter) {
        if (adapter == null) {
            throw new IllegalArgumentException("the adapter can't be null");
        }
        GridPagerAdapter pagerAdapter = new GridPagerAdapter<T>(context, adapter.getDataList(),
                itemLayoutId, columnSize, rowSize, listener) {

            @Override
            public void bind(GridRecyclerAdapter.ViewHolder viewHolder, T data, int position) {
                adapter.bindData(viewHolder, data, position);
            }
        };
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                indicatorView.setSelectedPosition(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        indicatorView.setCount(pagerAdapter.getCount());
        viewPager.setAdapter(pagerAdapter);
    }


    public interface OnGridItemClickListener {
        void onGridItemClick(int position, View view);
    }

    private OnGridItemClickListener listener;

    public void setOnGridItemClickListener(OnGridItemClickListener listener) {
        this.listener = listener;
    }
}
