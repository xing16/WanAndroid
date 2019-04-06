package com.xing.commonbase.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

import com.xing.commonbase.R;


public class IndicatorView extends View {

    private final int DEFAULT_DISTANCE = dp2px(6);
    private final int DEFAULT_RADIUS = dp2px(3);
    private int count;
    private int distance;
    private int radius;
    private int normalColor;
    private int selectedColor;
    private int width;
    private int height;
    private Paint paint;
    private int curPosition;

    public IndicatorView(Context context) {
        this(context, null);

    }

    public IndicatorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        readAttrs(context, attrs);
        initPaint();
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
    }


    private void readAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.IndicatorView);
        distance = typedArray.getDimensionPixelSize(R.styleable.IndicatorView_distance, DEFAULT_DISTANCE);
        radius = typedArray.getDimensionPixelSize(R.styleable.IndicatorView_radius, DEFAULT_RADIUS);
        normalColor = typedArray.getColor(R.styleable.IndicatorView_normalColor, 0xffd93c34);
        selectedColor = typedArray.getColor(R.styleable.IndicatorView_selectedColor, 0xffaf2720);
        typedArray.recycle();
    }


    public void setCount(int count) {
        if (count <= 0) {
            throw new IllegalArgumentException("count should greater than 0");
        }
        this.count = count;
    }

    public void setSelectedPosition(int position) {
        if (count <= 0) {
            throw new UnsupportedOperationException("you should call setCount() before this");
        }
        if (position < 0 || position >= count) {
            throw new IllegalArgumentException("the position illegal");
        }
        this.curPosition = position;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width;
        int height;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            width = count * 2 * radius + (count - 1) * distance;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = 2 * radius;
        }
        setMeasuredDimension(width, height);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(width / 2f, height / 2f);
        for (int i = 0; i < count; i++) {
            float x = -width / 2f + (2 * i + 1) * radius + i * distance;  // 0
            float y = 0;
            if (i == curPosition) {
                paint.setColor(selectedColor);
            } else {
                paint.setColor(normalColor);
            }
            canvas.drawCircle(x, y, radius, paint);
        }
        canvas.restore();
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
