package com.zhuleiyue.launcherdemo;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.text.TextUtils;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhuleiyue on 14/12/8.
 */
public class Util {

    public static boolean isMIUI() {
        /**
         * 读取到有这个key ro.miui.ui.version.name就是小米的rom 若值>=V5，
         */
        Object result = null;
        try {
            @SuppressWarnings("rawtypes")
            Class cl = Class.forName("android.os.SystemProperties");
            Object invoker = cl.newInstance();
            @SuppressWarnings("unchecked")
            Method m = cl.getMethod("get", new Class[]{String.class, String.class});
            result = m.invoke(invoker, new Object[]{"ro.miui.ui.version.name", ""});
        } catch (Exception ignore) {
        }
        if (result == null) {
            return false;
        }
        if (TextUtils.isEmpty(String.valueOf(result))) {
            return false;
        }
        if ("V5".compareTo(String.valueOf(result).toUpperCase()) > 0) {
            return false;
        } else {
            return true;
        }
    }

    public static ArrayList<String> getDefaultHome() {
        ArrayList<String> homes = new ArrayList<>();
        ArrayList<IntentFilter> intentList = new ArrayList<>();
        ArrayList<ComponentName> cnList = new ArrayList<>();
        MyApplication.getInstance().getPackageManager().getPreferredActivities(intentList, cnList, null);
        IntentFilter dhIF;
        for (int i = 0; i < cnList.size(); i++) {
            dhIF = intentList.get(i);
            if (dhIF.hasAction(Intent.ACTION_MAIN) &&
                    dhIF.hasCategory(Intent.CATEGORY_HOME)) {
                homes.add(cnList.get(i).getPackageName());
            }
        }
        return homes;
    }

    public static boolean isDefaultHome() {
        for (String string : getDefaultHome()) {
            if ("com.zhuleiyue.launcherdemo".equals(string)) {
                return true;
            }
        }
        return false;
    }

    public static void clearDefaultHome () {
        // 创建一个组件
        final ComponentName mhCN = new ComponentName("com.zhuleiyue.launcherdemo", "com.zhuleiyue.launcherdemo.MockHomeActivity");

        final PackageManager pm = MyApplication.getInstance().getPackageManager();
        // 设置指定的Component是否能让系统初始化,在Mainfest设置enabled默认值为false，
        // 设置mhCN为enabled，清除默认桌面
        pm.setComponentEnabledSetting(mhCN, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, 1);
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                pm.setComponentEnabledSetting(mhCN, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT, 1);
//            }
//        }, 50);
    }

//    public static void clearDefaultHome2(){
//        ArrayList<IntentFilter> intentList = new ArrayList<>();
//        ArrayList<ComponentName> cnList = new ArrayList<>();
//        MyApplication.getInstance().getPackageManager().getPreferredActivities(intentList, cnList, null);
//        IntentFilter dhIF;
//        for (int i = 0; i < cnList.size(); i++) {
//            dhIF = intentList.get(i);
//            if (dhIF.hasAction(Intent.ACTION_MAIN) &&
//                    dhIF.hasCategory(Intent.CATEGORY_HOME)) {
//                MyApplication.getInstance().getPackageManager().clearPackagePreferredActivities(cnList.get(i).getPackageName());
//            }
//        }
//    }

}
