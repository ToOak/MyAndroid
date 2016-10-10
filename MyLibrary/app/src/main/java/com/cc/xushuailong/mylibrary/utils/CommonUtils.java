package com.cc.xushuailong.mylibrary.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * 公共工具类
 * Created by xushuailong on 2016/6/16.
 */
public class CommonUtils {
    private static long lastClickTime;

    /**
     * 判断是否快速点击
     *
     * @param context
     * @param intervalTime
     * @return
     */
    public static boolean isFastDoubleClick(Context context, long intervalTime){
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if (0 < timeD && timeD < intervalTime){
            Toast.makeText(context,"double click !",Toast.LENGTH_SHORT).show();
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
