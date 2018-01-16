package com.example.litepaltest;

import android.app.Application;

import org.litepal.LitePal;

/**
 * Created by LewJun on 2018/1/16.
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        initLitePal();
    }

    private void initLitePal() {
        LitePal.initialize(this);
        LitePal.getDatabase();
    }
}
