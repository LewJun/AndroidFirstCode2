package com.example.activitydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ThirdActivity extends AppCompatActivity {
    private static final String TAG = "ThirdActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Log.d(TAG, "当前活动实例: " + this.toString() + ", task id: " + getTaskId());
    }
}
