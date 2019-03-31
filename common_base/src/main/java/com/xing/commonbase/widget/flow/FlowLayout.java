package com.xing.commonbase.widget.flow;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.xing.commonbase.R;
import com.xing.commonbase.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义 View, 实现流式布局
 */
public class FlowLayout extends ViewGroup {
    /**
     * 各子 View 之间水平间距
     */
    private int horizontalMargin;
    /**
     * 各子 View 之间竖直间距
     */
    private int verticalMargin;
    /**
     * 子控件的位置
     */
    private List<FlowChildPostion> childPositions = new ArrayList<>();

    private FlowAdapter adapter;

    public FlowLayout(Context context) {
        this(context, null);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        readAttrs(context, attrs);
    }

    private void readAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.FlowLayout);
        horizontalMargin = typedArray.getDimensionPixelSize(R.styleable.FlowLayout_flow_horizontal_margin, DensityUtil.dp2px(getContext(), 15));
        verticalMargin = typedArray.getDimensionPixelSize(R.styleable.FlowLayout_flow_vertical_margin, DensityUtil.dp2px(getContext(), 10));
        typedArray.recycle();
    }

    /**
     * 测量宽高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        // wrap_content 时，FlowLayout 控件的宽高
        int width = 0;  //
        int height = 0;
        int lineWidth = 0;     // 当前行的子控件已占的宽度
        int maxLineHeight = 0; // 当前行最大的行高度
        int childCount = getChildCount();
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            // 测量子控件的宽高
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
            // 考虑子控件的 margin
            MarginLayoutParams lp = (MarginLayoutParams) childView.getLayoutParams();
            int childViewWidth = childView.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            int childViewHeight = childView.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;

            // 当前行剩余可用宽度
            int availableWidth = widthSize - paddingLeft - paddingRight - lineWidth;
            // 当前剩余可用宽度大于该子控件宽度，不换行
            if (availableWidth >= childViewWidth) {
                childPositions.add(new FlowChildPostion(
                        paddingLeft + lineWidth,
                        paddingTop + height + verticalMargin,
                        paddingLeft + lineWidth + childViewWidth,
                        paddingTop + height + childViewHeight + verticalMargin));

                lineWidth += childViewWidth + horizontalMargin;
                width = Math.max(width, lineWidth);
                maxLineHeight = Math.max(maxLineHeight, childViewHeight);
            } else {   // 换行
                // 换行时，控件的高度为 所有
                lineWidth = childViewWidth + horizontalMargin;
                height += verticalMargin + maxLineHeight;
                maxLineHeight = childViewHeight;

                childPositions.add(new FlowChildPostion(
                        paddingLeft + lp.leftMargin,
                        paddingTop + height + verticalMargin + lp.topMargin,
                        paddingLeft + lp.leftMargin + childViewWidth,
                        paddingTop + height + verticalMargin + lp.topMargin + childViewHeight));


            }
        }
        height += maxLineHeight;

        // FlowLayout 最终的宽高
        int finalWidth = widthMode == MeasureSpec.EXACTLY ? widthSize : width;
        int finalHeight = heightMode == MeasureSpec.EXACTLY ? heightSize : height;
        setMeasuredDimension(finalWidth, finalHeight);
    }

    /**
     * 摆放子控件
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            FlowChildPostion flowChildPostion = childPositions.get(i);
            childView.layout(flowChildPostion.getLeft(), flowChildPostion.getTop(),
                    flowChildPostion.getRight(), flowChildPostion.getBottom());

        }
    }

    public void setAdapter(FlowAdapter adapter) {
        if (adapter == null) {
            throw new IllegalArgumentException("adapter not null");
        }
        this.adapter = adapter;
        int count = adapter.getCount();
        for (int i = 0; i < count; i++) {
            View view = adapter.getView(i, this);
            addView(view);
        }

    }


    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return new MarginLayoutParams(p);
    }
}
