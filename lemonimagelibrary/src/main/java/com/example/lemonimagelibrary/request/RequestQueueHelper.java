package com.example.lemonimagelibrary.request;

import com.example.lemonimagelibrary.config.GlobalConfig;

/**
 * Created by ShuWen on 2017/3/22.
 */

@SuppressWarnings("ALL")
public class RequestQueueHelper {
    //添加图片请求
    public static void addRequest(BitmapRequest request){
        if (GlobalConfig.getRequestQueue() == null){
            GlobalConfig.initDefault();
        }
        GlobalConfig.getRequestQueue().addBitmapRequest(request);
    }
}
