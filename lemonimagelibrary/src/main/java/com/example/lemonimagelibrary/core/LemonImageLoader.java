package com.example.lemonimagelibrary.core;

import android.content.Context;

import com.example.lemonimagelibrary.config.BitmapConfig;

/**
 * Created by ShuWen on 2017/3/22.
 */

@SuppressWarnings("ALL")
public class LemonImageLoader {

    public static BitmapConfig.Builder with(Context context){
        return new BitmapConfig.Builder(context);
    }

}
