package com.example.activitydemo;

import android.app.Activity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LewJun on 2018/1/6.
 * 活动控制器
 */
public class ActivityCollector {

    private static List<Activity> activities = new ArrayList<>();

    public static void addActivity(Activity activity) {
        activities.add(activity);
    }

    public static void removeActivity(Activity activity) {
        activities.remove(activity);
    }

    public static void finishAll() {
        for (Activity activity : activities) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
        // 杀掉当前进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
