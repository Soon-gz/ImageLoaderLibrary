package com.example.lemonimagelibrary.request;

import android.util.Log;

import com.example.lemonimagelibrary.config.GlobalConfig;

import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by ShuWen on 2017/3/22.
 */

@SuppressWarnings("ALL")
public class RequestQueue {

    //允许的转发器数量
    private int threadCount;

    //转发器管理
    private BitmapDispatcher[] dispatchers;

    //阻塞式队列
    private PriorityBlockingQueue<BitmapRequest> requestQueue = new PriorityBlockingQueue<>();

    //每个请求特有的Id
    private AtomicInteger requestId = new AtomicInteger(0);


    public RequestQueue(int threadCount) {
        this.threadCount = threadCount;
    }


    /**
     * 开启后台线程
     */
    public void start(){
        stop();
        startAllDispatchers();
    }

    /**
     * 创建转发器
     */
    private void startAllDispatchers() {
        dispatchers = new BitmapDispatcher[threadCount];
        for (int i = 0; i < dispatchers.length; i++) {
            BitmapDispatcher bitmapDispatcher = new BitmapDispatcher(requestQueue);
            bitmapDispatcher.start();
            dispatchers[i] = bitmapDispatcher;
        }
    }

    /**
     * 停止所有请求图片线程
     */
    private void stop(){
        if (dispatchers != null && dispatchers.length > 0){
            for (BitmapDispatcher bitmapDispatcher:dispatchers) {
                if (!bitmapDispatcher.isInterrupted()){
                    bitmapDispatcher.interrupt();
                }
            }
        }
    }

    /**
     * 添加网络请求
     * @param request
     */
    public void addBitmapRequest(BitmapRequest request){
        if (!requestQueue.contains(request)){
            request.setRequestId(requestId.incrementAndGet());
            requestQueue.add(request);
        }else {
            Log.i(GlobalConfig.TAG,"任务已存在，不用再次添加。");
        }
    }
}
