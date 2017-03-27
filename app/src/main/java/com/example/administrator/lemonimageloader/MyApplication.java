package com.example.administrator.lemonimageloader;

import android.app.Application;

import com.example.lemonimagelibrary.config.GlobalConfig;

/**
 * Created by ShuWen on 2017/3/26.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        GlobalConfig.initDefault();
    }
}
