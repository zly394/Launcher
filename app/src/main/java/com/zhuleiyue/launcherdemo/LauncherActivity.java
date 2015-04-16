package com.zhuleiyue.launcherdemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

public class LauncherActivity extends BaseActivity {
    public static final int FLAG_HOMEKEY_DISPATCHED = 0x80000000; //需要自己定义标志

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
            getWindow().setFlags(FLAG_HOMEKEY_DISPATCHED, FLAG_HOMEKEY_DISPATCHED);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
        setContentView(R.layout.activity_launcher);
        MyApplication.getInstance().registerHomeKeyReceiver();
        String value = getIntent().getStringExtra("key");
        Log.i("HomeReceiver", "value=" + value);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        Constant.recentappsIsDialog = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i("HomeReceiver", "onPause------>");
        Constant.recentappsIsDialog = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i("HomeReceiver", "onStop------>");
    }

    public void onAttachedToWindow() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.GINGERBREAD_MR1) {
            this.getWindow().setType(WindowManager.LayoutParams.TYPE_KEYGUARD_DIALOG);
        }
        super.onAttachedToWindow();
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button2:
                exit();
                break;
            case R.id.set:
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
                break;
            case R.id.browers:
                Intent i = new Intent();
                i.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("http://www.baidu.com");
                i.setData(content_url);
                startActivity(i);
                break;
        }
    }

    private void exit() {
        MyApplication.getInstance().unregisterHomeKeyReceiver();
        startActivity(new Intent(this, MainActivity.class));
        // 创建一个组件
        final ComponentName mhCN = new ComponentName("com.zhuleiyue.launcherdemo",
                "com.zhuleiyue.launcherdemo.LauncherActivity");
        final PackageManager pm = getPackageManager();
        // 设置指定的Component是否能让系统初始化,在Mainfest设置enabled默认值为false，
        // 设置mhCN为enabled，清除默认桌面
        pm.setComponentEnabledSetting(mhCN,
                PackageManager.COMPONENT_ENABLED_STATE_DEFAULT, 1);
        MyApplication.getInstance().exit();
    }

    @Override
    public void onBackPressed() {

    }

}
