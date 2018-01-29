package com.example.uicustomviews.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.uicustomviews.R;

/**
 * Created by LewJun on 2018/01/29.
 */
public class ListBorderRelativeLayout extends RelativeLayout {
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

    /**
     * 标题文字
     */
    private String mHdText;
    /**
     * 标题颜色
     */
    private int mHdTextColor;
    /**
     * 标题大小
     */
//    private float mHdTextSize;

    /**
     * 标题控件
     */
    private TextView mHdTextView;

    /**
     * 显示向右徽章
     */
    private boolean isShowFtChevronRight;
    private ImageView mFtChevronRight;


    /**
     * 尾部描述文字文字
     */
    private String mFtText;
    /**
     * 尾部描述文字颜色
     */
    private int mFtTextColor;
    /**
     * 尾部描述文字大小
     */
//    private float mFtTextSize;

    /**
     * 尾部描述文字控件
     */
    private TextView mFtTextView;

    /**
     * 是否显示头部icon
     */
    private boolean isShowHdIcon;

    private ImageView mHdIcon;
    private Drawable mHdIconSrc;
    private Bitmap mHdIconBitmap;

    private String mHdTextSub;
    private int mHdTextSubColor;
    private TextView mHdTextSubView;


    public ListBorderRelativeLayout(Context context) {
        this(context, null);
    }

    public ListBorderRelativeLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListBorderRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // this, 表示给加载好的布局添加一个父布局
        LayoutInflater.from(context).inflate(R.layout.layout_list, this);
        // 获取自定义属性
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.ListBorderRelativeLayout);
        mBorderColor = ta.getColor(R.styleable.ListBorderRelativeLayout_borderColor, Color.GRAY);
        mBorderStrokeWidth = ta.getFloat(R.styleable.ListBorderRelativeLayout_borderStrokeWidth, 1.0f);
        mBorderBottomLeftBreakSize = ta.getDimension(R.styleable.ListBorderRelativeLayout_borderBottomLeftBreakSize, 0f);
        mBorderBottomRightBreakSize = ta.getDimension(R.styleable.ListBorderRelativeLayout_borderBottomRightBreakSize, 0f);
        isNeedTopBorder = ta.getBoolean(R.styleable.BorderRelativeLayout_borderNeedTop, true);
        isNeedLeftAndRightBorder = ta.getBoolean(R.styleable.BorderRelativeLayout_borderNeedLeftAndRight, false);
        isNeedBottomBorder = ta.getBoolean(R.styleable.BorderRelativeLayout_borderNeedBottom, true);


        mHdText = ta.getString(R.styleable.ListBorderRelativeLayout_hdText);
        mHdTextColor = ta.getColor(R.styleable.ListBorderRelativeLayout_hdTextColor, Color.BLACK);
//        mHdTextSize = ta.getDimension(R.styleable.ListBorderRelativeLayout_hdTextSize, 11.75f);

        isShowFtChevronRight = ta.getBoolean(R.styleable.ListBorderRelativeLayout_showFtChevronRight, false);



        mFtText = ta.getString(R.styleable.ListBorderRelativeLayout_ftText);
        mFtTextColor = ta.getColor(R.styleable.ListBorderRelativeLayout_ftTextColor, Color.GRAY);
//        mFtTextSize = ta.getDimension(R.styleable.ListBorderRelativeLayout_ftTextSize, 9f);


        isShowHdIcon = ta.getBoolean(R.styleable.ListBorderRelativeLayout_showHdIcon, false);
        mHdIconSrc = ta.getDrawable(R.styleable.ListBorderRelativeLayout_hdIconSrc);

        mHdTextSub = ta.getString(R.styleable.ListBorderRelativeLayout_hdTextSub);
        mHdTextSubColor = ta.getColor(R.styleable.ListBorderRelativeLayout_hdTextSubColor, Color.GRAY);

        ta.recycle();

        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(mBorderColor);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(mBorderStrokeWidth);

        initHdText();

        initHdTextSub();

        initFtChevronRight();

        initFtText();

        initHdIcon();
    }

    private void initHdTextSub() {
        mHdTextSubView = findViewById(R.id.hd_text_sub);
        if(TextUtils.isEmpty(mHdTextSub)) {
            mHdTextSubView.setVisibility(GONE);
        } else {
            mHdTextSubView.setVisibility(VISIBLE);
            mHdTextSubView.setText(mHdTextSub);
            mHdTextSubView.setTextColor(mHdTextSubColor);
        }
    }

    private void initHdIcon() {
        mHdIcon = findViewById(R.id.hd_icon);
        if(isShowHdIcon) {
            mHdIcon.setVisibility(VISIBLE);
            mHdIcon.setImageDrawable(mHdIconSrc);
        } else {
            mHdIcon.setVisibility(GONE);
        }
    }

    private void initFtText() {
        mFtTextView = findViewById(R.id.ft_text);
        if(!TextUtils.isEmpty(mFtText)) {
            mFtTextView.setVisibility(VISIBLE);
            mFtTextView.setText(mFtText);
            mFtTextView.setTextColor(mFtTextColor);
//        由于传入的值被转换成了PX，所以这里使用单位TypedValue.COMPLEX_UNIT_PX
//            mFtTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mFtTextSize);
        } else {
            mFtTextView.setVisibility(GONE);
        }
    }

    private void initFtChevronRight() {
        mFtChevronRight = findViewById(R.id.ft_chevron_right);
        mFtChevronRight.setVisibility(isShowFtChevronRight ? VISIBLE : GONE);
    }

    private void initHdText() {
        mHdTextView = findViewById(R.id.hd_text);
        mHdTextView.setText(mHdText);
        mHdTextView.setTextColor(mHdTextColor);
//        由于传入的值被转换成了PX，所以这里使用单位TypedValue.COMPLEX_UNIT_PX
//        mHdTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mHdTextSize);
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

    public void setHdText(String hdText) {
        mHdText = hdText;
        mHdTextView.setText(mHdText);
        invalidate();
    }

    public void setHdTextColor(int hdTextColor) {
        mHdTextColor = hdTextColor;
        mHdTextView.setTextColor(mHdTextColor);
        invalidate();
    }

/*    public void setHdTextSize(float hdTextSize) {
        mHdTextSize = hdTextSize;
        mHdTextView.setTextSize(mHdTextSize);
        invalidate();
    }*/

    public void setShowFtChevronRight(boolean showFtChevronRight) {
        isShowFtChevronRight = showFtChevronRight;
        mFtChevronRight.setVisibility(isShowFtChevronRight ? VISIBLE : GONE);
        invalidate();
    }

    public void setFtText(String ftText) {
        mFtText = ftText;
        mFtTextView.setText(mFtText);
        invalidate();
    }

    public void setFtTextColor(int ftTextColor) {
        mFtTextColor = ftTextColor;
        mFtTextView.setTextColor(mFtTextColor);
        invalidate();
    }

/*    public void setFtTextSize(float ftTextSize) {
        mFtTextSize = ftTextSize;
        mFtTextView.setTextSize(mFtTextSize);
        invalidate();
    }*/

    public void setShowHdIcon(boolean showHdIcon) {
        isShowHdIcon = showHdIcon;
        mHdIcon.setVisibility(isShowHdIcon ? VISIBLE : GONE);
        invalidate();
    }

    public void setHdIconBitmap(Bitmap hdIconBitmap) {
        mHdIconBitmap = hdIconBitmap;
        mHdIcon.setImageBitmap(mHdIconBitmap);
        invalidate();
    }

    public void setHdTextSub(String hdTextSub) {
        mHdTextSub = hdTextSub;
        mHdTextSubView.setText(mHdTextSub);
        invalidate();
    }

    public void setHdTextSubColor(int hdTextSubColor) {
        mHdTextSubColor = hdTextSubColor;
        mHdTextSubView.setTextColor(mHdTextSubColor);
        invalidate();
    }
}
