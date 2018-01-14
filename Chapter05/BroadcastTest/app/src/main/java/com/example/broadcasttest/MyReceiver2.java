package com.example.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.util.Log;

public class MyReceiver2 extends BroadcastReceiver {
    private static final String TAG = "MyReceiver2";

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d(TAG, "onReceive: " + intent.getAction());

// java.lang.RuntimeException: Unable to start receiver com.example.broadcasttest.MyReceiver2:
// java.lang.IllegalStateException: You need to use a Theme.AppCompat
// theme (or descendant) with this activity.
        // 静态注册的广播不能弹出对话框这样的UI控件。
        /*new AlertDialog.Builder(context)
                .setTitle("title")
                .setMessage("message")
                .show();*/
    }
}
