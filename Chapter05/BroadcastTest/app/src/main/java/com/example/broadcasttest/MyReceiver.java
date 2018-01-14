package com.example.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * 接受自定义广播
 */
public class MyReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        String cardNo = intent.getStringExtra("CARD_NO");
        Log.d(TAG, "cardNo: " + cardNo);
        Toast.makeText(context, "cardNo: " + cardNo, Toast.LENGTH_SHORT).show();

        // 打开一个活动
        intent.setClass(context, CardInfoActivity.class);
        context.startActivity(intent);
        // 阻断广播继续传递
        abortBroadcast();
    }
}
