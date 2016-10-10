package com.cc.xushuailong.mylibrary.utils;

import android.util.Log;

import com.cc.xushuailong.mylibrary.constant.PublicControl;

import org.apache.log4j.Logger;

/**
 * Created by xushuailong on 2016/5/20.
 */
public class Log4jUtil {

    private static final boolean isDebug = PublicControl.isDebug;

    private static final Logger logger = Logger.getLogger(Log4jUtil.class);

    public static void d(String msg){
        if (isDebug) {
            logger.debug(msg);
            Log.d("xsl", msg);
        }
    }

    public static void i(String msg){
        if (isDebug) {
            logger.info(msg);
            Log.i("xsl", msg);
        }
    }

    public static void e(String msg){
        if (isDebug) {
            logger.error(msg);
            Log.e("xsl", msg);
        }
    }

    public static void d(String tag, String msg){
        if (isDebug) {
            logger.debug(msg);
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg){
        if (isDebug) {
            logger.info(msg);
            Log.i(tag, msg);
        }
    }

    public static void e(String tag, String msg){
        if (isDebug) {
            logger.error(msg);
            Log.e(tag, msg);
        }
    }
}
