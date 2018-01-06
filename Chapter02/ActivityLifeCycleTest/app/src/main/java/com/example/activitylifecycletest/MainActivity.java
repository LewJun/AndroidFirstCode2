package com.example.activitylifecycletest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

/**
 * -------------------------启动MainActivity
 * MainActivity: onCreate: 活动第一次被创建的时候调用
 * MainActivity: onStart: 活动由不可见变为可见的时候调用
 * MainActivity: onResume: 在活动准备好和用户交互的时候调用
 * <p>
 * -------------------------启动NormalActivity
 * MainActivity: onPause: 在系统准备启动或恢复另一个活动的时候调用
 * MainActivity: onStop: 在活动完全不可见的时候调用
 * <p>
 * -------------------------按返回键，关闭NormalActivity，返回到MainActivity
 * MainActivity: onRestart: 在活动由停止状态变为运行状态之前调用，也就是活动被重新启动了。
 * MainActivity: onStart: 活动由不可见变为可见的时候调用
 * MainActivity: onResume: 在活动准备好和用户交互的时候调用
 * <p>
 * -------------------------启动DialogActivity
 * MainActivity: onPause: 在系统准备启动或恢复另一个活动的时候调用
 * <p>
 * -------------------------按返回键，关闭DialogActivity，返回到MainActivity
 * MainActivity: onResume: 在活动准备好和用户交互的时候调用
 * <p>
 * -------------------------按返回键，关闭MainActivity，返回到桌面
 * MainActivity: onPause: 在系统准备启动或恢复另一个活动的时候调用
 * MainActivity: onStop: 在活动完全不可见的时候调用
 * MainActivity: onDestroy: 在活动销毁之前调用，之后活动的状态变为销毁状态
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity";

    private Button btn_start_normalActivity;
    private Button btn_start_dialogActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: 活动第一次被创建的时候调用");
        setContentView(R.layout.activity_main);
        // 通常savedInstanceState是null，如果活动被系统回收之前调用onSaveInstanceState保存了数据，此时不为空
        if (savedInstanceState != null) {
            Log.d(TAG, savedInstanceState.getString("k"));
        }

        btn_start_normalActivity = findViewById(R.id.btn_start_normalActivity);
        btn_start_normalActivity.setOnClickListener(this);


        btn_start_dialogActivity = findViewById(R.id.btn_start_dialogActivity);
        btn_start_dialogActivity.setOnClickListener(this);


    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: 活动由不可见变为可见的时候调用");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: 在活动准备好和用户交互的时候调用");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: 在系统准备启动或恢复另一个活动的时候调用");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: 在活动完全不可见的时候调用");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: 在活动由停止状态变为运行状态之前调用，也就是活动被重新启动了。");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: 在活动销毁之前调用，之后活动的状态变为销毁状态");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_start_normalActivity:
                Intent intent = new Intent(this, NormalActivity.class);
                startActivity(intent);
                break;

            case R.id.btn_start_dialogActivity:
                intent = new Intent(this, DialogActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d(TAG, "onSaveInstanceState: 在活动被回收之前一定被调用");
        outState.putString("k", "v");
    }
}
