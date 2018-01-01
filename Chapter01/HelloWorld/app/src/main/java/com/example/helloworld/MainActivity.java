package com.example.helloworld;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

/**
 * MainActivity继承自AppCompatActivity，这是一种向下兼容的Activity，可以将Activity在各个版本
 * 中增加的特性和功能最低兼容到Android 2.1系统。
 */
public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    /**
     * 活动被创建的时候被调用
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
        // 给当前活动设置布局
        setContentView(R.layout.activity_main);
    }
}
