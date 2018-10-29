package com.guojiel.shootarrowdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MicLayout extends ViewGroup {

    private final int Rows = 4;
    private final int ItemWidth = dp2px(72);
    private final int ItemHeight = dp2px(72);

    public MicLayout(Context context) {
        super(context);
    }

    public MicLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        for (int i = 0; i < Rows; i++) {
            ImageView iv = new ImageView(getContext());
            iv.setBackgroundResource(R.drawable.ic_left_mic_seat_bg);
            addView(iv);
        }
        for (int i = 0; i < Rows; i++) {
            ImageView iv = new ImageView(getContext());
            iv.setBackgroundResource(R.drawable.ic_right_mic_seat_bg);
            addView(iv);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int childCount = getChildCount();
        final int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(ItemWidth, MeasureSpec.EXACTLY);
        final int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(ItemHeight, MeasureSpec.EXACTLY);
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            view.measure(childWidthMeasureSpec, childHeightMeasureSpec);
        }
        setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(ItemHeight * Rows, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int childCount = getChildCount();
        final int parentWidth = getMeasuredWidth();
        for (int i = 0; i < childCount; i++) {
            View view = getChildAt(i);
            if(i < Rows){
                view.layout(0, i * ItemHeight, ItemWidth, (i + 1) * ItemHeight);
            }else{
                view.layout(parentWidth - ItemWidth, (i % Rows) * ItemHeight, parentWidth, (i % Rows + 1) * ItemHeight);
            }
        }
    }

    private int dp2px(float dpValue){
        return  (int)(dpValue * getContext().getResources().getDisplayMetrics().density + 0.5f);
    }

}
