package com.kunminx.architecture.utils;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ActivityCollector {
    public static List<AppCompatActivity> activitys = new ArrayList<AppCompatActivity>();

    //向List中添加一个活动
    public static void addActivity(AppCompatActivity activity) {
        activitys.add(activity);
    }

    //从List中移除活动
    public static void removeActivity(AppCompatActivity activity) {
        activitys.remove(activity);
    }

    //将List中存储的活动全部销毁掉
    public static void finishAll() {
        for (AppCompatActivity activity : activitys) {
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }
    }
}
