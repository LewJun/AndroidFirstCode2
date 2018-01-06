package com.example.activitydemo;

import android.os.Bundle;
import android.util.Log;

public class ThirdActivity extends BaseActivity {
    private static final String TAG = "ThirdActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Log.d(TAG, "当前活动实例: " + this.toString() + ", task id: " + getTaskId());

        // 退出应用
        findViewById(R.id.btn_exit).setOnClickListener(view -> {
            ActivityCollector.finishAll();
        });

        // 打开second
        findViewById(R.id.btn_open_second).setOnClickListener(view -> {
            SecondActivity.actionStart(ThirdActivity.this, "param1", 333);
        });
    }
}
