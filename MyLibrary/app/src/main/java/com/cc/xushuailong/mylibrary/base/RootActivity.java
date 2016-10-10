package com.cc.xushuailong.mylibrary.base;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Window;

import com.cc.xushuailong.mylibrary.MyApplication;
import com.cc.xushuailong.mylibrary.utils.Log4jUtil;

/**
 * Created by xushuailong on 2016/5/20.
 */
public class RootActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        Log4jUtil.d("当前所在：" + getClass().getSimpleName().toString());
        MyApplication.getApp().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        MyApplication.getApp().removeActivity(this);
    }
}
