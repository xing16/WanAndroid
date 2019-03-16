package com.xing.commonbase.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatRadioButton;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.xing.commonbase.R;


/**
 * 自定义 RadioButton,实现带角标未读数
 */
public class BadgeRadioButton extends AppCompatRadioButton {

    private final int DEFAULT_BADGE_TEXT_COLOR = 0xffffffff;
    private final int DEFAULT_BADGE_TEXT_SIZE = 10;
    private final int DEFAULT_BADGE_BACKGROUND_COLOR = 0xfff45050;
    private int badgeTextColor;
    private int badgeTextSize;
    private int badgeBackgroundColor;
    private Paint mPaint;
    private boolean isShowDot;
    private boolean isShowNumberDot;

    private int width;
    private int height;

    /**
     * 顶部图片宽
     */
    private int IntrinsicWidth;

    private String badgeNumber;
    /**
     * 圆点和未读消息的坐标
     */
    private float pivotX;
    private float pivotY;
    /**
     * 圆点半径
     */
    private final int circleDotRadius = dp2px(5);

    public BadgeRadioButton(Context context) {
        this(context, null);
    }

    public BadgeRadioButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BadgeRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttrs(context, attrs);
        init();
    }

    /**
     * 获取自定义属性
     *
     * @param context
     * @param attrs
     */
    private void readAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = null;
        try {
            typedArray = context.obtainStyledAttributes(attrs, R.styleable.BadgeRadioButton);
            badgeTextColor = typedArray.getColor(R.styleable.BadgeRadioButton_badgeTextColor, DEFAULT_BADGE_TEXT_COLOR);
            badgeTextSize = typedArray.getDimensionPixelSize(R.styleable.BadgeRadioButton_badgeTextSize, DEFAULT_BADGE_TEXT_SIZE);
            badgeBackgroundColor = typedArray.getColor(R.styleable.BadgeRadioButton_badgeBackground, DEFAULT_BADGE_BACKGROUND_COLOR);

        } catch (Exception e) {
        } finally {
            if (typedArray != null) {
                typedArray.recycle();
            }
        }
    }

    private void init() {
        setClickable(true);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStrokeWidth(2);


        // get android:drawableTop value
        Drawable drawable = getCompoundDrawables()[1];
        if (drawable != null) {
            IntrinsicWidth = drawable.getIntrinsicWidth();
        }
        /**
         * 给RadioButton增加一定的padding
         * */
//        if (getPaddingBottom() == 0) {
//            setPadding(dp2px(10), dp2px(10), dp2px(10), dp2px(10));
//        }
    }

    @Override
    public void setCompoundDrawables(@Nullable Drawable left, @Nullable Drawable top, @Nullable Drawable right, @Nullable Drawable bottom) {
        super.setCompoundDrawables(left, top, right, bottom);
        // get android:drawableTop value
        Drawable drawable = getCompoundDrawables()[1];
        if (drawable != null) {
            IntrinsicWidth = drawable.getIntrinsicWidth();
        }
        /**
         * 给RadioButton增加一定的padding
         * */
        if (getPaddingBottom() == 0) {
            setPadding(0, dp2px(9), 0, dp2px(0));
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        width = w;
        height = h;

        pivotX = (float) (IntrinsicWidth / 2 > width / 2 ? width / 2 : IntrinsicWidth / 4) + circleDotRadius;
        pivotY = (float) (height * 0.3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.translate(width / 2, height / 2);
        mPaint.setColor(badgeBackgroundColor);
        if (isShowDot) {
            canvas.drawCircle(pivotX, -pivotY, circleDotRadius, mPaint);
        } else if (isShowNumberDot && !TextUtils.isEmpty(badgeNumber)) {
            mPaint.setTextSize(sp2px(badgeTextSize));
            float textWidth = mPaint.measureText(badgeNumber);
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
            float textHeight = Math.abs((fontMetrics.top + fontMetrics.bottom));
            // 数字左右增加一定的边距
            RectF rectF = new RectF(pivotX - dp2px(4), -pivotY - textHeight / 2 - dp2px(2),
                    pivotX + textWidth + dp2px(4), -pivotY + textHeight);
            canvas.drawRoundRect(rectF, rectF.height() / 2f, rectF.height() / 2f, mPaint);
            mPaint.setColor(badgeTextColor);
            canvas.drawText(badgeNumber, pivotX, -pivotY + textHeight / 2, mPaint);
        }
        canvas.restore();
    }

    /**
     * 设置显示小圆点
     */
    public void setShowSmallDot() {
        this.isShowDot = true;
        invalidate();
    }

    /**
     * 清除小红点的显示
     */
    public void clearSmallDot() {
        this.isShowDot = false;
        invalidate();
    }

    /**
     * 设置是否显示数字
     */
    public void setBadgeNumber(@NonNull String text) {
        this.isShowNumberDot = true;
        this.isShowDot = false;
        this.badgeNumber = text;
        invalidate();
    }

    /**
     * 清除未读数的显示
     */
    public void clearBadgeNumber() {
        this.isShowNumberDot = false;
        this.badgeNumber = null;
        invalidate();
    }

    public void setBadgeTextColor(int badgeTextColor) {
        this.badgeTextColor = badgeTextColor;
        invalidate();
    }

    /**
     * 设置字体大小
     *
     * @param badgeTextSize 单位 dp
     */
    public void setBadgeTextSize(int badgeTextSize) {
        this.badgeTextSize = badgeTextSize;
        mPaint.setTextSize(dp2px(badgeTextSize));
        invalidate();
    }

    /**
     * 将dp转为px
     *
     * @param dpValue
     * @return
     */
    private int dp2px(int dpValue) {
        return (int) (dpValue * getContext().getResources().getDisplayMetrics().density);
    }

    /**
     * 将sp值转换为px值
     *
     * @param spValue
     * @return
     */
    private int sp2px(int spValue) {
        float fontScale = getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
