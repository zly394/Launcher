package com.zhuleiyue.launcherdemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

public class HomeWatcherReceiver extends BroadcastReceiver {
    private static final String LOG_TAG = "HomeReceiver";
    private static final String SYSTEM_DIALOG_REASON_KEY = "reason";
    private static final String SYSTEM_DIALOG_REASON_RECENT_APPS = "recentapps";
    private static final String SYSTEM_DIALOG_REASON_HOME_KEY = "homekey";
    private static final String SYSTEM_DIALOG_REASON_ASSIST = "assist";

    @Override
    public void onReceive(final Context context, Intent intent) {
        String action = intent.getAction();
        Log.i(LOG_TAG, "onReceive: action: " + action);
        if (action.equals(Intent.ACTION_CLOSE_SYSTEM_DIALOGS)) {
            String reason = intent.getStringExtra(SYSTEM_DIALOG_REASON_KEY);
            Log.i(LOG_TAG, "onReceive: reason: " + reason);
            if (SYSTEM_DIALOG_REASON_HOME_KEY.equals(reason)) {
                // 短按Home键
                Log.i(LOG_TAG, SYSTEM_DIALOG_REASON_HOME_KEY);
            } else {
                if (SYSTEM_DIALOG_REASON_RECENT_APPS.equals(reason)) {
                    // 长按Home键 或者 activity切换键
                    Log.i(LOG_TAG, SYSTEM_DIALOG_REASON_RECENT_APPS);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if (Constant.recentappsIsDialog) {
                                Log.i(LOG_TAG, "关闭最近任务");
                                Intent closeDialog = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
                                closeDialog.putExtra("reason", "globalactions");//可避免关机对话框被关闭
                                MyApplication.getInstance().sendBroadcast(closeDialog);
                            } else {
                                Log.i(LOG_TAG, "重新进入桌面");
                                Intent i = new Intent(Intent.ACTION_MAIN);
                                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                i.addCategory(Intent.CATEGORY_HOME);
                                context.startActivity(i);
                            }
                        }
                    }, 100);
                } else if (SYSTEM_DIALOG_REASON_ASSIST.equals(reason)) {
                    // samsung 长按Home键
                    Log.i(LOG_TAG, SYSTEM_DIALOG_REASON_ASSIST);
                }
            }
        }
    }
}
