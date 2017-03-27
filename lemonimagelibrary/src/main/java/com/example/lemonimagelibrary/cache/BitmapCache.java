package com.example.lemonimagelibrary.cache;

import android.graphics.Bitmap;

import com.example.lemonimagelibrary.request.BitmapRequest;

/**
 * Created by ShuWen on 2017/3/22.
 */

@SuppressWarnings("ALL")
public interface BitmapCache {
    /**
     * 存入内存
     * @param request
     * @param bitmap
     */
    void put(BitmapRequest request, Bitmap bitmap);

    /**
     * 读取缓存的图片
     * @param request
     */
    Bitmap get(BitmapRequest request);

    /**
     * 清除缓存的图片
     * @param request
     */
    void remove(BitmapRequest request);
}
