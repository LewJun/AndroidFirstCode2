package com.example.uicustomviews.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.example.uicustomviews.R;

/**
 * Created by LewJun on 2018/01/27.
 */
public class BorderRelativeLayout extends RelativeLayout {
    /**
     * 画笔
     */
    private Paint mPaint;
    /**
     * 边框颜色
     */
    private int mBorderColor;
    /**
     * 边框粗细
     */
    private float mBorderStrokeWidth;
    /**
     * 底边边线左边断开距离
     */
    private float mBorderBottomLeftBreakSize;
    /**
     * 底边边线右边断开距离
     */
    private float mBorderBottomRightBreakSize;
    /**
     * 是否需要上边框
     */
    private boolean isNeedTopBorder;
    /**
     * 是否需要左右边框
     */
    private boolean isNeedLeftAndRightBorder;
    /**
     * 是否需要下边框
     */
    private boolean isNeedBottomBorder;


    public BorderRelativeLayout(Context context) {
        this(context, null);
    }

    public BorderRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BorderRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 获取自定义属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.BorderRelativeLayout);
        mBorderColor = ta.getColor(R.styleable.BorderRelativeLayout_borderColor, Color.GRAY);
        mBorderStrokeWidth = ta.getDimension(R.styleable.BorderRelativeLayout_borderStrokeWidth, 1.0f);
        mBorderBottomLeftBreakSize = ta.getDimension(R.styleable.BorderRelativeLayout_borderBottomLeftBreakSize, 0f);
        mBorderBottomRightBreakSize = ta.getDimension(R.styleable.BorderRelativeLayout_borderBottomRightBreakSize, 0f);
        isNeedTopBorder = ta.getBoolean(R.styleable.BorderRelativeLayout_borderNeedTop, true);
        isNeedLeftAndRightBorder = ta.getBoolean(R.styleable.BorderRelativeLayout_borderNeedLeftAndRight, false);
        isNeedBottomBorder = ta.getBoolean(R.styleable.BorderRelativeLayout_borderNeedBottom, true);
        ta.recycle();
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(mBorderColor);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mBorderStrokeWidth);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        //  画4个边
        if (isNeedTopBorder) {
            canvas.drawLine(0, 0, this.getWidth(), 0, mPaint);
        }
        if (isNeedBottomBorder) {
            canvas.drawLine(mBorderBottomLeftBreakSize, this.getHeight(), this.getWidth() - mBorderBottomRightBreakSize, this.getHeight(), mPaint);
        }
        if (isNeedLeftAndRightBorder) {
            canvas.drawLine(0, 0, 0, this.getHeight(), mPaint);
            canvas.drawLine(this.getWidth(), 0, this.getWidth(), this.getHeight(), mPaint);
        }
    }

    /**
     * 设置边框颜色
     *
     * @param color
     */
    public void setBorderColor(int color) {
        mPaint.setColor(color);
        invalidate();
    }

    /**
     * 设置边框宽度
     *
     * @param size
     */
    public void setBorderStrokeWidth(float size) {
        mPaint.setStrokeWidth(size);
        invalidate();
    }


    /**
     * 设置是否需要顶部边框
     *
     * @param needtopborder
     */
    public void setNeedTopBorder(boolean needtopborder) {
        isNeedTopBorder = needtopborder;
        invalidate();
    }

    /**
     * 设置是否需要底部边框
     *
     * @param needbottomborder
     */
    public void setNeedBottomBorder(boolean needbottomborder) {
        isNeedBottomBorder = needbottomborder;
        invalidate();
    }

    public void setBorderBottomLeftBreakSize(float borderBottomLeftBreakSize) {
        mBorderBottomLeftBreakSize = borderBottomLeftBreakSize;
        invalidate();
    }

    public void setBorderBottomRightBreakSize(float borderBottomRightBreakSize) {
        mBorderBottomRightBreakSize = borderBottomRightBreakSize;
        invalidate();
    }

    public void setNeedLeftAndRightBorder(boolean needLeftAndRightBorder) {
        isNeedLeftAndRightBorder = needLeftAndRightBorder;
        invalidate();
    }
}