package com.zhuleiyue.launcherdemo;

import android.app.Activity;
import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuleiyue on 14/12/8.
 */
public class MyApplication extends Application {
    private static final String LOG_TAG = "HomeReceiver";

    protected HomeWatcherReceiver homeWatcherReceiver = null;

    private static MyApplication mInstance;
    private List<Activity> activitys;

    public MyApplication(){
        activitys = new ArrayList<>();
    }

    public static MyApplication getInstance(){
        if (mInstance == null){
            mInstance = new MyApplication();
        }
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

    public void addActivity(Activity activity){
        if (activitys != null){
            activitys.add(activity);
        }
    }

    public void exit(){
        for (Activity activity : activitys){
            if (activity != null){
                activity.finish();
            }
        }
        activitys.clear();
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addCategory(Intent.CATEGORY_HOME);
        startActivity(i);
    }

    public void removeActivity(Activity activity){
        if (activitys != null && activitys.size() != 0){
            activitys.remove(activity);
        }
    }

    public void registerHomeKeyReceiver() {
        Log.i(LOG_TAG, "registerHomeKeyReceiver");
        homeWatcherReceiver = new HomeWatcherReceiver();
        IntentFilter homeFilter = new IntentFilter(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        registerReceiver(homeWatcherReceiver, homeFilter);
    }

    public void unregisterHomeKeyReceiver() {
        if (null != homeWatcherReceiver) {
            Log.i(LOG_TAG, "unregisterHomeKeyReceiver");
            unregisterReceiver(homeWatcherReceiver);
        }
    }
}
