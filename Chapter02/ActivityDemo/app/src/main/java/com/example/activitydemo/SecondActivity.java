package com.example.activitydemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class SecondActivity extends BaseActivity {

    private static final String TAG = "SecondActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        Log.d(TAG, "当前活动实例: " + this.toString() + ", task id: " + getTaskId());

        // 得到启动当前Activity的Intent
        Intent intent = getIntent();
        String param1 = intent.getStringExtra("param1");
        Log.d(TAG, "param1: " + param1);

        intent.getIntExtra("param2",0);
    }

    // 通过按返回键返回
    @Override
    public void onBackPressed() {
//////////////////坑////调用了super.onBackPressed();返回到上一个Activity中拿不到返回的intent的数据。//////////////////
//        super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("result", "Don't mess with him.");
//        向上一个活动返回数据
        setResult(RESULT_OK, intent);
        finish();
    }

    // 通过点击按钮返回
    public void toBack(View view) {
        Intent intent = new Intent();
        intent.putExtra("result", "Don't mess with him.");
//        向上一个活动返回数据
        setResult(RESULT_OK, intent);
        finish();
    }

    public void toFirstActivity(View view) {
        Intent intent = new Intent(this, FirstActivity.class);
        startActivity(intent);
    }

    public void toThirdActivity(View view) {
        Intent intent = new Intent(this, ThirdActivity.class);
        startActivity(intent);
    }
}
