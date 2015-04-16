package com.zhuleiyue.launcherdemo;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	public void onClick(View view){
		switch (view.getId()) {
		case R.id.button1:
            gotoHome();
        	break;
		}
	}

    private void gotoHome(){
        if (!Util.isMIUI() && !Util.isDefaultHome()){
            Util.clearDefaultHome();
        }
        // 创建一个组件
        PackageManager pm = MyApplication.getInstance().getPackageManager();
        ComponentName mhCN = new ComponentName("com.zhuleiyue.launcherdemo", "com.zhuleiyue.launcherdemo.LauncherActivity");
        ComponentName mockHome = new ComponentName("com.zhuleiyue.launcherdemo", "com.zhuleiyue.launcherdemo.MockHomeActivity");
        // 设置指定的Component是否能让系统初始化,在Mainfest设置enabled默认值为false，
        // 设置mhCN为enabled
        pm.setComponentEnabledSetting(mhCN, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, 1);
        // 弹出默认桌面选择框
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.addCategory(Intent.CATEGORY_HOME);
        if (Util.isMIUI() && !Util.isDefaultHome()){
            i.setComponent(new ComponentName("android", "com.android.internal.app.ResolverActivity"));
        }
        i.putExtra("key","value");
        startActivity(i);
        pm.setComponentEnabledSetting(mockHome, PackageManager.COMPONENT_ENABLED_STATE_DEFAULT, 1);

    }
}
