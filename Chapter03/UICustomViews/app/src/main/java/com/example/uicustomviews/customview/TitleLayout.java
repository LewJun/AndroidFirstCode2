package com.example.uicustomviews.customview;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.uicustomviews.R;

/**
 * 1 创建布局文件 R.layout.layout_title
 * 2 创建attrs_title_layout 自定义属性
 * 3 创建TitleLayout 添加交互，得到自定义属性值并设置到对应控件
 * 4 在页面使用TitleLayout
 *
 * Created by LewJun on 2018/1/7.
 */
public class TitleLayout extends LinearLayout {

    public TitleLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // this, 表示给加载好的布局添加一个父布局
        LayoutInflater.from(context).inflate(R.layout.layout_title, this);
        init(attrs);

        findViewById(R.id.btn_back).setOnClickListener(v -> {
            Toast.makeText(context, "back", Toast.LENGTH_SHORT).show();
            ((Activity) getContext()).finish();
        });

        findViewById(R.id.btn_exit).setOnClickListener(v -> {
            Toast.makeText(context, "exit", Toast.LENGTH_SHORT).show();
        });
    }

    private void init(AttributeSet attrs) {
// 得到自定义属性值
         TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.TitleLayout);

        TextView tv_title = findViewById(R.id.tv_title);
        tv_title.setText(typedArray.getString(R.styleable.TitleLayout_title));
        tv_title.setTextColor(typedArray.getColor(R.styleable.TitleLayout_titleColor, getResources().getColor(R.color.colorAccent)));
        typedArray.recycle();
    }

}
