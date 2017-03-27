package com.example.lemonimagelibrary.cache;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.lemonimagelibrary.request.BitmapRequest;

/**
 * Created by ShuWen on 2017/3/22.
 */

@SuppressWarnings("ALL")
public class DoubleLruCache implements BitmapCache {


    private MemoryLruCache lruCache;

    private DiskBitmapCache bitmapCache;

    public DoubleLruCache(Context context){
       bitmapCache = DiskBitmapCache.getInstance(context);
        lruCache = MemoryLruCache.getInstance();
    }


    @Override
    public void put(BitmapRequest request, Bitmap bitmap) {
        lruCache.put(request,bitmap);
        bitmapCache.put(request,bitmap);
    }

    @Override
    public Bitmap get(BitmapRequest request) {
        Bitmap bitmap = lruCache.get(request);
        if (bitmap == null){
            bitmap = bitmapCache.get(request);
            lruCache.put(request,bitmap);
        }
        return bitmap;
    }

    @Override
    public void remove(BitmapRequest request) {
        lruCache.remove(request);
        bitmapCache.remove(request);
    }
}
