package com.xing.commonbase.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * WebView 渐变增长进度的进度条
 */
public class WebProgressBar extends View {
    private int progress = 0;
    private int height = dp2Px(5);
    private Paint paint;

    public WebProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WebProgressBar(Context context) {
        super(context);
        paint = new Paint(Paint.DITHER_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(height);
        paint.setAntiAlias(true);
        paint.setColor(Color.RED);
    }

    /**
     * WebViewProgressBar 作用于 progress 属性进行增长动画，
     * 故需要提供 set() 方法
     *
     * @param progress
     */
    public void setProgress(int progress) {
        this.progress = progress;
        invalidate();
    }

    public int getProgress() {
        return progress;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(0, 0, getWidth() * progress / 100, height, paint);
    }

    private int dp2Px(int dpValue) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpValue, getResources().getDisplayMetrics());
    }
}
