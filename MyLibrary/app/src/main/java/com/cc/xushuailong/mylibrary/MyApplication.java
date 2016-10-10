package com.cc.xushuailong.mylibrary;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Environment;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import de.mindpipe.android.logging.log4j.LogConfigurator;

/**
 * Created by xushuailong on 2016/5/20.
 */
public class MyApplication extends Application {
    private String packageName;
    private static MyApplication app;
    private List<Activity> activities = new ArrayList<>();
    public static MyApplication getApp(){
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        packageName = getPackageName().toString();
        app = this;
        logToSDCard();
    }

    private void logToSDCard() {
        LogConfigurator logConfigurator = new LogConfigurator();
        logConfigurator.setFileName(Environment.getExternalStorageDirectory()
                + File.separator + "MyAppLogs" + File.separator + packageName + File.separator + "log4j.txt");
        logConfigurator.setRootLevel(Level.ALL);
        logConfigurator.setLevel("org.apache", Level.ERROR);
        logConfigurator.setFilePattern("%d %-5p [%c{2}]-[%L] %m%n");
        logConfigurator.setMaxFileSize(1024 * 1024 * 5);
        logConfigurator.setImmediateFlush(true);
        logConfigurator.configure();
        Logger logger = Logger.getLogger(MyApplication.class);
        logger.info("My LogFile create !");
    }

    public void addActivity(Activity activity){
        if (!activities.contains(activity)){
            activities.add(activity);
        }
    }

    public void removeActivity(Activity activity){
        if (activities.contains(activity)){
            activities.remove(activity);
        }
    }

    public void removeAllActivity(){
        if (activities != null){
            for (Activity activity : activities){
                if (!activity.isFinishing()){
                    activity.finish();
                }
            }
//            for(int i=0;i<activities.size();i++){
//                activities.get(i).finish();
//                activities.remove(i);
//            }
        }
    }

    public void reSetLogFile(){
        String filePath = Environment.getExternalStorageDirectory()
                + File.separator + "MyAppLogs" + File.separator + packageName + File.separator + "log4j.txt";
        File file = new File(filePath);
        if (file.exists()){
            file.delete();
        }
        logToSDCard();
    }

}
