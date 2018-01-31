package com.example.objectboxexample;

import android.app.Application;
import android.util.Log;

import com.example.model.MyObjectBox;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

/**
 * Created by LewJun on 2018/1/31.
 */

public class App extends Application {
    private static final String TAG = "App";
    private BoxStore mBoxStore;

    @Override
    public void onCreate() {
        super.onCreate();
        // do this once, for example in your Application class
        mBoxStore = MyObjectBox.builder().androidContext(this).build();
        if (BuildConfig.DEBUG) {
            new AndroidObjectBrowser(mBoxStore).start(this);
        }

        Log.d(TAG, "Using ObjectBox " + BoxStore.getVersion() + " (" + BoxStore.getVersionNative() + ")");
    }

    public BoxStore getBoxStore() {
        return mBoxStore;
    }
}
