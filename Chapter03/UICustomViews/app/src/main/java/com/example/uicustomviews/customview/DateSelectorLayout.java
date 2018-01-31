package com.example.uicustomviews.customview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uicustomviews.R;
import com.example.uicustomviews.utils.DateUtil;

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

    public Date getTvCurrDate() {
        String s = tv_curr_day.getText().toString();
        return DateUtil.getDate1(s);
    }

    public void setTvCurrDate(Date date) {
        tv_curr_day.setText(DateUtil.getDate1(date));
    }

    private OnClickListener tvCurrDayListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(mContext, "tvCurrDayListener", Toast.LENGTH_SHORT).show();
        }
    };

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
}
