package com.example.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * 开机完成广播接收器
 */
public class BootCompleteReceiver extends BroadcastReceiver {
    private static final String TAG = "BootCompleteReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "boot complete", Toast.LENGTH_SHORT).show();
        Log.d(TAG, "onReceive: boot complete");

        // 开机启动应用程序本身
        intent.setClass(context, MainActivity.class);
        context.startActivity(intent);

        // 开机启动服务
//        context.startService(service);
    }
}
