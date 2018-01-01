package com.example.activitydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * 活动是一种可以包含用户界面的组件，主要用于和用户进行交互
 * 步骤：
 * 1 创建Activity类
 * 2 创建类对应的布局文件
 * 3 为类加载布局文件
 * 4 在AndroidManifest.xml文件的application节点下配置Activity类。
 */
public class FirstActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        给当前活动加载一个布局
        setContentView(R.layout.activity_first);

        // findViewById，方法名很形象，通过id找到view
        Button btn_hello = findViewById(R.id.btn_hello);
        // 给按钮注册一个点击的监听事件
        btn_hello.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 活动本身就是一个Context对象
                Toast.makeText(FirstActivity.this, "hello world", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
