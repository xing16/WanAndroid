package com.xing.commonbase.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;

import com.xing.commonbase.R;

public class ZoomScrollView extends ScrollView {
    /**
     * 要放大的目标控件
     */
    private View zoomView;
    /**
     * 要放大的目标控件最大距离
     */
    private int zoomMax;
    private float downY;
    private float scaleRadio = 0.5f;
    private int zoomViewWidth;
    private int zoomViewHeight;
    /**
     * 当前是否是正在缩放中
     */
    private boolean scaling = false;

    public ZoomScrollView(Context context) {
        this(context, null);
    }

    public ZoomScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        readAttrs(context, attrs);
    }

    public void setZoomView(View targetView) {
        this.zoomView = targetView;
    }

    private void readAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ZoomScrollView);
        zoomMax = typedArray.getDimensionPixelSize(R.styleable.ZoomScrollView_zoomMax, 20);
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        setOverScrollMode(OVER_SCROLL_NEVER);

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        zoomViewWidth = zoomView.getMeasuredWidth();
        zoomViewHeight = zoomView.getMeasuredHeight();
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollListener != null) {
            onScrollListener.onScrollChange(this, l, t, oldl, oldt);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_MOVE:
                if (!scaling) {
                    if (getScrollY() == 0) {
                        downY = ev.getY();
                    } else {
                        break;
                    }
                }
                float distance = (ev.getY() - downY) * scaleRadio;
                if (distance < 0) {   // 上拉
                    break;
                }
                scaling = true;
                // 开始缩放
                startZoom(distance);
                return true;
            case MotionEvent.ACTION_UP:
                scaling = false;
                // 手指抬起，图片回弹
                restoreZoomView();
                break;
        }
        return super.onTouchEvent(ev);

    }

    private void restoreZoomView() {
        float distance = zoomView.getMeasuredWidth() - zoomViewWidth;
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(distance, 0f)
                .setDuration(300);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startZoom((Float) animation.getAnimatedValue());
            }
        });
        valueAnimator.start();
    }

    private void startZoom(float distance) {
        if (zoomViewWidth == 0 || zoomViewHeight == 0) {
            return;
        }
        ViewGroup.LayoutParams layoutParams = zoomView.getLayoutParams();
        layoutParams.width = (int) (zoomViewWidth + distance);
        // 高度等比例放大
        layoutParams.height = (int) (((zoomViewWidth + distance) / zoomViewWidth) * zoomViewHeight);
        // 左移图片，显示图片中间位置
        ((MarginLayoutParams) layoutParams).setMargins(-(layoutParams.width - zoomViewWidth) / 2, 0, 0, 0);
        zoomView.setLayoutParams(layoutParams);
        Log.e("devugf", "startZoom: " + zoomView.getMeasuredHeight());

    }

    /**
     * 返回是否可以开始放大
     *
     * @return
     */
    protected boolean isReadyForPullStart() {
        return getScrollY() == 0;
    }

    private OnScrollListener onScrollListener;

    public interface OnScrollListener {
        void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY);
    }

    public void setOnScrollListener(OnScrollListener listener) {
        this.onScrollListener = listener;
    }

}
