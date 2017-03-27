package com.example.lemonimagelibrary.loader;

import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.widget.ImageView;

import com.example.lemonimagelibrary.request.BitmapRequest;

/**
 * Created by ShuWen on 2017/3/22.
 * 抽象的加载器基类，采用模板设计模式，对加载器进行封装
 */

@SuppressWarnings("ALL")
public abstract class AbstractLoader implements ILoader {

    private final byte[] lock = new byte[0];

    private Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void load(BitmapRequest request) {
        //先检查缓存中是否存在
        Bitmap bitmap = null;
        if (request.getLoaderMode() == LoaderMode.HTTP){
            bitmap = request.getCache().get(request);
        }

        if (bitmap == null) {

            showLoadingImage(request);

            bitmap = onLoad(request);

            if (request.getLoaderMode() == LoaderMode.HTTP){
                createCache(request, bitmap);
            }
        }

        deliveryUIThread(request, bitmap);

    }

    /**
     * 在主线程显示图片
     *
     * @param request
     * @param bitmap
     */
    private void deliveryUIThread(final BitmapRequest request, final Bitmap bitmap) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                updateImage(request, bitmap);
            }
        });

        /**
         * 监听图片网络请求是否成功
         */
        if (request.getBitmapListener() != null) {
            if (bitmap != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        request.getBitmapListener().onSuccess(bitmap);
                    }
                });
            } else {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        request.getBitmapListener().onFail();
                    }
                });
            }
        }
    }

    /**
     * 具体更新逻辑
     *
     * @param request
     * @param bitmap
     */
    private void updateImage(BitmapRequest request, Bitmap bitmap) {
        ImageView imageView = request.getImageView();

        if (imageView != null && bitmap != null && imageView.getTag().equals(request.getUriMD5())) {
            imageView.setImageBitmap(bitmap);
        }

    }

    /**
     * 加入缓存
     *
     * @param request
     */
    private void createCache(BitmapRequest request, Bitmap bitmap) {
        if (request.getCache() != null && bitmap != null) {
            synchronized (lock) {
                request.getCache().put(request, bitmap);
            }
        }
    }

    /**
     * 显示加载过程id
     *
     * @param request
     */
    private void showLoadingImage(BitmapRequest request) {
        if (request.getLoadingResId() > 0) {
            final ImageView imageView = request.getImageView();
            final int resId = request.getLoadingResId();
            if (imageView != null) {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        imageView.setImageResource(resId);
                    }
                });
            }
        }
    }

    /**
     * 留给子类具体实现如何加载获得图片
     *
     * @param request
     * @return
     */
    protected abstract Bitmap onLoad(BitmapRequest request);
}
