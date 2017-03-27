package com.example.lemonimagelibrary.config;

import com.example.lemonimagelibrary.request.RequestQueue;

/**
 * Created by ShuWen on 2017/3/22.
 */

@SuppressWarnings("ALL")
public class GlobalConfig {
    //全局队列，必须在Appication中初始化
    private static RequestQueue requestQueue;
    //该手机硬件允许的最大线程数
    private static int threadCountMax = Runtime.getRuntime().availableProcessors();


    public static final String TAG = "LemonImageLoader";

    private GlobalConfig(){

    }

    public static void init(int threadCount){
        if (threadCount < threadCountMax){
            threadCountMax = threadCount;
        }
        requestQueue = new RequestQueue(threadCountMax);
        requestQueue.start();
    }

    public static void initDefault(){
        requestQueue = new RequestQueue(threadCountMax);
        requestQueue.start();
    }

    public static RequestQueue getRequestQueue() {
        return requestQueue;
    }
}
