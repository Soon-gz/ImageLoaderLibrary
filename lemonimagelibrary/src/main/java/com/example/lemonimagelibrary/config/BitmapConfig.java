package com.example.lemonimagelibrary.config;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.DrawableRes;
import android.util.Log;
import android.widget.ImageView;

import com.example.lemonimagelibrary.cache.BitmapCache;
import com.example.lemonimagelibrary.cache.CacheMode;
import com.example.lemonimagelibrary.cache.DiskBitmapCache;
import com.example.lemonimagelibrary.cache.DoubleLruCache;
import com.example.lemonimagelibrary.cache.MemoryLruCache;
import com.example.lemonimagelibrary.cache.NoLruCache;
import com.example.lemonimagelibrary.loader.LoaderMode;
import com.example.lemonimagelibrary.policy.IPolicy;
import com.example.lemonimagelibrary.policy.SerialPolicy;
import com.example.lemonimagelibrary.request.BitmapRequest;
import com.example.lemonimagelibrary.request.RequestQueueHelper;

import java.io.File;

/**
 * Created by ShuWen on 2017/3/22.
 * 参数封装，对于外部暴露
 */

@SuppressWarnings("ALL")
public class BitmapConfig {
    //请求地址
    private String url;
    //加载本地图片
    private File file;
    //加载本地资源id图片
    private int resId;
    //显示的imageview控件
    private ImageView imageView;
    //用于创建硬件缓存所需环境
    private Context context;
    //加载器策略
    private IPolicy policy;
    //图片缓存策略
    private BitmapCache cache;
    //加载过程显示策略
    private int loadingResId;
    //请求成功回调接口
    private BitmapListener bitmapListener;
    //用于判断是否需要显示
    private boolean hasListener = false;
    //加载器选择
    private LoaderMode loaderMode;


    private BitmapConfig(Builder builder){
        if (builder.cache == null){
            this.cache = DiskBitmapCache.getInstance(builder.context);
        }else {
            this.cache = builder.cache;
        }
        this.url = builder.url;
        this.file = builder.file;
        this.resId = builder.resId;
        this.context = builder.context;
        this.loadingResId = builder.loadingResId;
        this.imageView = builder.imageView;
        this.policy = builder.policy;
        this.hasListener = builder.hasListener;
        loaderMode = builder.loaderMode;
        this.bitmapListener = builder.bitmapListener;
    }

    public LoaderMode getLoaderMode() {
        return loaderMode;
    }

    public int getLoadingResId() {
        return loadingResId;
    }

    public File getFile() {
        return file;
    }

    public int getResId() {
        return resId;
    }

    public boolean isHasListener() {
        return hasListener;
    }

    public String getUrl() {
        return url;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Context getContext() {
        return context;
    }


    public IPolicy getPolicy() {
        return policy;
    }

    public BitmapCache getCache() {
        return cache;
    }


    public BitmapListener getBitmapListener() {
        return bitmapListener;
    }

    private void show(){
        if (loaderMode != null){
            BitmapRequest bitmapRequest = new BitmapRequest(this);
            RequestQueueHelper.addRequest(bitmapRequest);
        }else {
            Log.e(GlobalConfig.TAG,"请调用load方法设置加载数据来源！");
        }

    }

    public interface BitmapListener{
        void onSuccess(Bitmap bitmap);
        void onFail();
    }


    @SuppressWarnings("SameParameterValue")
    public static class Builder{
        //请求地址
        private String url;
        //加载本地图片
        private File file;
        //加载器模式
        private LoaderMode loaderMode;
        //加载本地资源id图片
        private int resId;
        //显示的imageview控件
        private ImageView imageView;
        //用于创建硬件缓存所需环境
        private Context context;
        //加载器策略
        private IPolicy policy = new SerialPolicy();
        //图片缓存策略
        private BitmapCache cache;
        //加载过程显示策略
        private int loadingResId = -1;
        //请求成功回调接口
        private BitmapListener bitmapListener;
        //用于判断是否需要显示
        private boolean hasListener = false;

        public Builder(Context context){
            this.context = context;
        }

        //加载网络图片
        public Builder load(String url) {
            this.url = url;
            loaderMode = LoaderMode.HTTP;
            return this;
        }

        //加载本地图片
        public Builder load(File file) {
            this.file = file;
            loaderMode = LoaderMode.FILE;
            return this;
        }

        //加载资源id
        public Builder load(@DrawableRes int resId) {
            this.resId = resId;
            loaderMode = LoaderMode.ASSETS;
            return this;
        }


        public Builder loaderPolicy(IPolicy policy) {
            this.policy = policy;
            return this;
        }

        /**
         * 缓存策略设置
         * @param mode
         * @return
         */
        public Builder cachePolicy(CacheMode mode) {
            switch (mode){
                case DISK_LRU_CACHE:
                    this.cache = DiskBitmapCache.getInstance(context);
                    break;
                case MEMORY_LRU_CACHE:
                    this.cache = MemoryLruCache.getInstance();
                    break;
                case NO_CAHCE:
                    this.cache = new NoLruCache();
                    break;
                case DOUBLE_CAHCE:
                    this.cache = new DoubleLruCache(context);
                    break;
            }
            return this;
        }

        /**
         * 占位显示图片
         * @param resId
         * @return
         */
        public Builder placeHolder(@DrawableRes int resId) {
            loadingResId = resId;
            return this;
        }

        /**
         * 显示的控件
         * @param imageView
         */
        public void into(ImageView imageView){
            this.imageView = imageView;
            new BitmapConfig(this).show();
        }

        public void onFinishListener(BitmapListener bitmapListener){
            this.bitmapListener = bitmapListener;
            hasListener = true;
            new BitmapConfig(this).show();
        }
    }




}
