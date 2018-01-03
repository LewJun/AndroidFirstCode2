package com.example.activitydemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = "SecondActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        // 得到启动当前Activity的Intent
        Intent intent = getIntent();
        String param1 = intent.getStringExtra("param1");
        Log.d(TAG, "param1: " + param1);

        intent.getIntExtra("param2",0);
    }
}
