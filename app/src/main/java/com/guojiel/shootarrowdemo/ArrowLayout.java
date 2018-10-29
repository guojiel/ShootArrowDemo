package com.guojiel.shootarrowdemo;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.PointFEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.ImageView;

public class ArrowLayout extends ViewGroup {

    public final int ItemHeight = dp2px(72);
    public final int ItemWidth = dp2px(72);
    public final int BowWidth = dp2px(41);
    public final int ArrowWidth = dp2px(55);
    public final int ArrowBorderMargin = dp2px(50);

    public static final int Rows = 4;

    public ImageView[] mBows = new ImageView[8];
    public ImageView[] mArrows = new ImageView[8];

    public ArrowLayout(Context context) {
        super(context);
    }

    public ArrowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    private void initView() {
        for (int i = 0; i < mBows.length; i++) {
            mBows[i] = new ImageView(getContext());
            mArrows[i] = new ImageView(getContext());

            mBows[i].setPivotY(ItemHeight / 2);
            mArrows[i].setPivotY(ItemHeight / 2);

            mBows[i].setScaleType(ImageView.ScaleType.CENTER);
            mArrows[i].setScaleType(ImageView.ScaleType.CENTER);

            if(i < Rows){
                mBows[i].setPivotX(0);
                mArrows[i].setPivotX(0);
                mBows[i].setImageResource(R.drawable.ic_left_bow);

                mArrows[i].setImageResource(R.drawable.ic_left_arrow);
            }else{

                mBows[i].setPivotX(BowWidth);
                mArrows[i].setPivotX(ArrowWidth);

                mBows[i].setImageResource(R.drawable.ic_right_bow);
                mArrows[i].setImageResource(R.drawable.ic_right_arrow);
            }


            addView(mBows[i]);
            addView(mArrows[i]);

        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        for (int i = 0; i < 2 * Rows; i++) {
            mBows[i].measure(MeasureSpec.makeMeasureSpec(BowWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(ItemHeight, MeasureSpec.EXACTLY));
            mArrows[i].measure(MeasureSpec.makeMeasureSpec(ArrowWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(ItemHeight, MeasureSpec.EXACTLY));
        }
        setMeasuredDimension(widthMeasureSpec, MeasureSpec.makeMeasureSpec(4 * ItemHeight, MeasureSpec.EXACTLY));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int margin = ArrowBorderMargin;
        final int parentWidth = getMeasuredWidth();
        for (int i = 0; i < 2 * Rows; i++) {
            if(i < Rows){
                mBows[i].layout(margin, i * ItemHeight, margin + BowWidth, (i + 1) * ItemHeight);
                mArrows[i].layout(margin, i * ItemHeight, margin + ArrowWidth, (i + 1) * ItemHeight);
            }else{
                mBows[i].layout(parentWidth - BowWidth - margin, (i % Rows) * ItemHeight, parentWidth - margin, (i % Rows + 1) * ItemHeight);
                mArrows[i].layout(parentWidth - ArrowWidth - margin, (i % Rows) * ItemHeight, parentWidth - margin, (i % Rows + 1) * ItemHeight);
            }
        }
    }

    private int dp2px(float dpValue){
        return (int)(dpValue * getResources().getDisplayMetrics().density + 0.5f);
    }

    /**
     * 左边的人射箭
     * @param from 左边的人的位置{1/2/3/4}
     * @param to 右边的人的位置{1/2/3/4}
     */
    public void leftElection(int from, int to){

        final ImageView arrow = mArrows[from - 1];
        arrow.setVisibility(VISIBLE);
        final ImageView bow = mBows[from - 1];
        bow.setVisibility(VISIBLE);

        float startX = ArrowBorderMargin;
        float startY = (from - 0.5f) * ItemHeight;

        float targetX = this.getWidth() - ItemWidth / 2;
        float targetY = (to - 0.5f) * ItemHeight;


        double angle = Math.atan(1d * (targetY - startY) / (targetX - startX));
        float degree = (float) Math.toDegrees(angle);

        float xOffset = (float) (Math.cos(angle) * ArrowWidth);
        float yOffset = (float) (Math.sin(angle) * ArrowWidth);

        ValueAnimator mAnim = ValueAnimator.ofObject(new PointFEvaluator(), new PointF(0, 0), new PointF(targetX - ArrowBorderMargin - xOffset, targetY - 0.5f * ItemHeight - yOffset - (from - 1) * ItemHeight));

        mAnim.setDuration(500);
        mAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF p = (PointF) animation.getAnimatedValue();
                arrow.setTranslationX(p.x);
                arrow.setTranslationY(p.y);
            }
        });
        mAnim.start();

        bow.setRotation(degree);
        arrow.setRotation(degree);
    }

    /**
     * 右边的人射箭
     * @param from 右边的人的位置{1/2/3/4}
     * @param to 左边的人的位置{1/2/3/4}
     */
    public void rightElection(int from, int to){

        final ImageView arrow = mArrows[from - 1 + Rows];
        arrow.setVisibility(VISIBLE);
        final ImageView bow = mBows[from - 1 + Rows];
        bow.setVisibility(VISIBLE);

        float startX = this.getWidth() - ArrowBorderMargin;
        float startY = (from - 0.5f) * ItemHeight;

        float targetX = 0.5f * ItemWidth;
        float targetY = (to - 0.5f) * ItemHeight;

        double angle = Math.atan(1d * (targetY - startY) / (targetX - startX));
        float degree = (float) Math.toDegrees(angle);

        float xOffset = (float) (ArrowWidth - Math.cos(angle) * ArrowWidth);
        float yOffset = (float) (Math.sin(angle) * ArrowWidth);

        ValueAnimator mAnim = ValueAnimator.ofObject(
                new PointFEvaluator(),
                new PointF(0, 0),
                new PointF(targetX + ArrowBorderMargin + ArrowWidth - this.getWidth() - xOffset, targetY + 0.5f * ItemHeight - this.getHeight() + yOffset + (Rows - from) * ItemHeight)
        );

        mAnim.setDuration(500);
        mAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                PointF p = (PointF) animation.getAnimatedValue();
                arrow.setTranslationX(p.x);
                arrow.setTranslationY(p.y);
            }
        });
        mAnim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                bow.setVisibility(INVISIBLE);
            }
        });
        mAnim.start();

        arrow.setRotation(degree);
        bow.setRotation(degree);
    }






}
