package com.zhuleiyue.launcherdemo;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by zhuleiyue on 14/12/8.
 */
public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().addActivity(this);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getInstance().removeActivity(this);
    }
}
