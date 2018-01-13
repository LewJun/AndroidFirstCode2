package com.example.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    NetworkChangeReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        receiver = new NetworkChangeReceiver();
//        动态注册广播 优点：可以自由控制广播的注册和注销 缺点：必须要在程序启动之后才能收到广播
        // 为广播接收器注册广播（网络变化的广播，
        // Android系统在网络发生变化后会发出广播android.net.conn.CONNECTIVITY_CHANGE）
        // 让receiver监听网络变化
        registerReceiver(receiver,
                new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 动态注册的广播必须手动注销，否则会出现异常。如果未关闭直接退出程序，产生异常如下：
        /*
           android.app.IntentReceiverLeaked: Activity com.example.broadcasttest.MainActivity has leaked
        IntentReceiver com.example.broadcasttest.MainActivity$NetworkChangeReceiver@b71754a that was
        originally registered here. Are you missing a call to unregisterReceiver()
        */
        unregisterReceiver(receiver);
    }

    /**
     * 1 声明一个广播接收器的处理类
     */
    class NetworkChangeReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            /*String action = intent.getAction();
            Toast.makeText(context, "Network Changed "
                            + "\n" + action
                    , Toast.LENGTH_SHORT).show();*/

            ConnectivityManager connectionManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectionManager.getActiveNetworkInfo();
            if(activeNetworkInfo!=null && activeNetworkInfo.isAvailable() && activeNetworkInfo.isConnected()) {
                Toast.makeText(context, "network is available",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "network is unavailable",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }
}
