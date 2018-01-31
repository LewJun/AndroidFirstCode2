package com.example.uicustomviews.customview;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.uicustomviews.R;
import com.example.uicustomviews.utils.DateUtil;

import java.util.Calendar;
import java.util.Date;

/**
 * 自定义日期选择器
 * Created by LewJun on 2018/01/31.
 */
public class DateSelectorLayout extends LinearLayout {

    private Button btn_pre_day;
    private Button btn_next_day;
    private TextView tv_curr_day;
    private Context mContext;
    private OnDateChangeListener mOnDateChangeListener;

    public String getTvCurrDateStr() {
        return tv_curr_day.getText().toString();
    }

    public Date getTvCurrDate() {
        return DateUtil.getDate1(getTvCurrDateStr());
    }

    public void setTvCurrDate(Date date) {
        if (!DateUtil.getDate1(date).equals(getTvCurrDateStr())) {
            Date oldDate = getTvCurrDate();
            tv_curr_day.setText(DateUtil.getDate1(date));
            mOnDateChangeListener.action(oldDate, date);
        }
    }

    private OnClickListener tvCurrDayListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            showPopupDatePickerWindow(v);
        }
    };

    /**
     * 底部弹出日期选择控件
     *
     * @param v
     */
    private void showPopupDatePickerWindow(View v) {
        View contentView = LayoutInflater.from(mContext).inflate(R.layout.layout_window_popup_date_picker, null);
        PopupWindow pw = new PopupWindow(contentView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        // 点击空白处隐藏
        pw.setFocusable(true);

        pw.setBackgroundDrawable(new BitmapDrawable());

        //添加弹出、弹入的动画
        pw.setAnimationStyle(R.style.Popupwindow);

/*
        PopupWindow的显示方法有三个:
            showAsDropDown(anchor);
            showAsDropDown(anchor, xoff, yoff);

            showAtLocation(parent, gravity, x, y)。

        前两个showAsDropDown方法是让PopupWindow相对于某个控件显示，而showAtLocation是相对于整个窗口的。
        第一个参数是View类型的parent,虽然这里参数名是parent，其实，不是把PopupWindow放到这个parent里，
        并不要求这个parent是一个ViewGroup，这个参数名让人误解。官方文档“a parent view to get the android
        .view.View.getWindowToken() token from”,这个parent的作用应该是调用其getWindowToken()方法获取
        窗口的Token,所以，只要是该窗口(父类窗口)上的控件（例如：按钮控件）就可以了。
        第二个参数是Gravity，(Gravity.CENTER)可以使用|附加多个属性，如Gravity.LEFT|Gravity.BOTTOM。
        第三四个参数是x,y偏移。*/
//        int[] loc = new int[2];
//        v.getLocationOnScreen(loc);
        pw.showAtLocation(v, Gravity.LEFT | Gravity.BOTTOM, 0, 0);

        Calendar calendar = Calendar.getInstance();
        DatePicker datePicker = contentView.findViewById(R.id.datePicker);
        calendar.setTime(getTvCurrDate());
        datePicker.init(calendar.get(Calendar.YEAR)
                , calendar.get(Calendar.MONTH)
                , calendar.get(Calendar.DAY_OF_MONTH)
                , new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                    }
                }
        );

        Button btnToday = contentView.findViewById(R.id.btn_today);
        btnToday.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.setTime(new Date());
                datePicker.updateDate(calendar.get(Calendar.YEAR)
                        , calendar.get(Calendar.MONTH)
                        , calendar.get(Calendar.DAY_OF_MONTH)
                );
            }
        });

        Button btnConfirm = contentView.findViewById(R.id.btn_confirm);
        btnConfirm.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                int year = datePicker.getYear();
                int month = datePicker.getMonth();
                int dayOfMonth = datePicker.getDayOfMonth();
                calendar.set(year, month, dayOfMonth);
                Date date = calendar.getTime();
                setTvCurrDate(date);
                pw.dismiss();
            }
        });

    }

    private OnClickListener btnPreDayListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            setTvCurrDate(DateUtil.add(getTvCurrDate(), -1));
        }
    };

    private OnClickListener btnNextDayListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            setTvCurrDate(DateUtil.add(getTvCurrDate(), 1));
        }
    };

    public DateSelectorLayout(Context context) {
        this(context, null);
    }

    public DateSelectorLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DateSelectorLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_date_selector, this);
        mContext = context;
        init();
    }

    private void init() {
        tv_curr_day = findViewById(R.id.tv_curr_day);
        tv_curr_day.setText(DateUtil.getDate1(DateUtil.getToday()));
        tv_curr_day.setOnClickListener(tvCurrDayListener);

        btn_pre_day = findViewById(R.id.btn_pre_day);
        btn_pre_day.setOnClickListener(btnPreDayListener);
        btn_next_day = findViewById(R.id.btn_next_day);
        btn_next_day.setOnClickListener(btnNextDayListener);
    }

    public void setOnDateChangeListener(OnDateChangeListener onDateChangeListener) {
        mOnDateChangeListener = onDateChangeListener;
    }

    /**
     * 日期改变监听器
     */
    public interface OnDateChangeListener {
        void action(Date oldDate, Date newDate);
    }
}
