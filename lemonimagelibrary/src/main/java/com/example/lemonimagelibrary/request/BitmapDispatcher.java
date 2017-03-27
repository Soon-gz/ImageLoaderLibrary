package com.example.lemonimagelibrary.request;


import com.example.lemonimagelibrary.loader.ILoader;
import com.example.lemonimagelibrary.loader.LoaderManager;

import java.util.concurrent.BlockingQueue;

/**
 * Created by ShuWen on 2017/3/22.
 * 转发器子线程，循环从阻塞式队列中获取图片请求任务
 */

@SuppressWarnings("ALL")
public class BitmapDispatcher extends Thread{

    private BlockingQueue<BitmapRequest> requestQueue;

    public BitmapDispatcher(BlockingQueue<BitmapRequest> requestQueue){
        this.requestQueue = requestQueue;
    }

    @Override
    public void run() {
        while (!isInterrupted()){
            try {

                BitmapRequest request = requestQueue.take();

                ILoader loader = null;
                switch (request.getLoaderMode()){
                    case FILE:
                        loader = LoaderManager.getInstance().getILoader("file");
                        break;
                    case  ASSETS:
                        loader = LoaderManager.getInstance().getILoader("assets");
                        break;
                    case HTTP:
                        loader =  LoaderManager.getInstance().getILoader("http");
                        break;
                }

                if (loader != null){
                    loader.load(request);
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

}
