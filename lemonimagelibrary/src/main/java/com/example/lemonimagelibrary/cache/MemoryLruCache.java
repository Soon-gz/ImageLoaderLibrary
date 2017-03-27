package com.example.lemonimagelibrary.cache;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;

import com.example.lemonimagelibrary.config.GlobalConfig;
import com.example.lemonimagelibrary.request.BitmapRequest;

/**
 * Created by ShuWen on 2017/3/22.
 */

@SuppressWarnings("ALL")
public class MemoryLruCache implements BitmapCache {

    private LruCache<String,Bitmap> lruCache;

    private static volatile MemoryLruCache instance;

    private static final byte[]lock = new byte[0];

    public static MemoryLruCache getInstance(){
        if (instance == null){
            synchronized (lock){
                if (instance == null){
                    instance = new MemoryLruCache();
                }
            }
        }
        return instance;
    }

    private MemoryLruCache(){

        int maxMemorySize= (int) (Runtime.getRuntime().maxMemory()/16);
        Log.i(GlobalConfig.TAG,"内存可用的大小："+maxMemorySize/1024 + "K");

        if (maxMemorySize <= 0){
            maxMemorySize = 10*1024*1024;
        }

        lruCache = new LruCache<String, Bitmap>(maxMemorySize){
            @Override
            protected int sizeOf(String key, Bitmap value) {
                //一张图片的大小
                return value.getRowBytes()*value.getHeight();
            }
        };
    }


    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        if (bitmap != null){
            lruCache.put(request.getUriMD5(),bitmap);
        }
    }

    @Override
    public Bitmap get(BitmapRequest request) {
        return lruCache.get(request.getUriMD5());
    }

    @Override
    public void remove(BitmapRequest request) {
        lruCache.remove(request.getUriMD5());
    }
}
