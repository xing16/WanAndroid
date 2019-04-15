package com.xing.commonbase.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xing.commonbase.R;

public class ItemView extends LinearLayout {

    private int leftIcon;
    private int rightIcon;
    private ImageView leftImageView;
    private TextView titleTextView;
    private ImageView rightImageView;
    private String title;
    private int itemBackgroundColor;
    private boolean dividerVisible;
    private float dividerLeftMargin;
    private float dividerRightMargin;

    public ItemView(Context context) {
        this(context, null);
    }

    public ItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        readAttrs(context, attrs);
        init(context);
    }

    private void readAttrs(Context context, AttributeSet attrs) {
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ItemView);
        leftIcon = typedArray.getResourceId(R.styleable.ItemView_leftIcon, 0);
        rightIcon = typedArray.getResourceId(R.styleable.ItemView_rightIcon, 0);
        title = typedArray.getString(R.styleable.ItemView_itemTitle);
        itemBackgroundColor = typedArray.getColor(R.styleable.ItemView_itemBackgroundColor, 0);
        dividerVisible = typedArray.getBoolean(R.styleable.ItemView_dividerVisible, true);
        dividerLeftMargin = typedArray.getDimension(R.styleable.ItemView_dividerLeftMargin, dp2px(40));
        dividerRightMargin = typedArray.getDimension(R.styleable.ItemView_dividerRightMargin, 0);
        typedArray.recycle();
    }

    private void init(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_item_view, this, true);
        leftImageView = view.findViewById(R.id.iv_item_left_icon);
        titleTextView = view.findViewById(R.id.tv_item_title);
        rightImageView = view.findViewById(R.id.iv_right_icon);
        View dividerView = view.findViewById(R.id.v_divider);
        LinearLayout.LayoutParams layoutParams = (LayoutParams) dividerView.getLayoutParams();
        layoutParams.leftMargin = (int) dividerLeftMargin;
        layoutParams.rightMargin = (int) dividerRightMargin;

        if (itemBackgroundColor != 0) {
            view.setBackgroundColor(itemBackgroundColor);
        }
        if (leftIcon != 0) {
            leftImageView.setImageResource(leftIcon);
        }
        if (rightIcon != 0) {
            rightImageView.setImageResource(rightIcon);
        }
        titleTextView.setText(title);
        dividerView.setVisibility(dividerVisible ? VISIBLE : GONE);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            return false;
        }
        return super.onTouchEvent(event);
    }

    private int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, getResources().getDisplayMetrics());
    }
}
