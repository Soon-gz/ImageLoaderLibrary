package com.example.lemonimagelibrary.request;

import android.content.Context;
import android.widget.ImageView;

import com.example.lemonimagelibrary.cache.BitmapCache;
import com.example.lemonimagelibrary.config.BitmapConfig;
import com.example.lemonimagelibrary.loader.LoaderMode;
import com.example.lemonimagelibrary.policy.IPolicy;
import com.example.lemonimagelibrary.util.MD5Utils;

import java.io.File;
import java.lang.ref.SoftReference;

/**
 * Created by ShuWen on 2017/3/22.
 * 对请求参数的二次封装，对内部封装的暴露
 */

@SuppressWarnings("ALL")
public class BitmapRequest implements Comparable<BitmapRequest>{

    private int requestId;

    private String uri;

    private LoaderMode loaderMode;

    private SoftReference<ImageView> softReference;

    private IPolicy policy;

    private String uriMD5;

    private int loadingResId;

    private BitmapCache cache;

    private boolean hasListener;

    private File file;

    private int resId = -1;

    private BitmapConfig.BitmapListener bitmapListener;

    private Context context;

    public BitmapRequest(BitmapConfig config){
        this.context = config.getContext();
        this.bitmapListener = config.getBitmapListener();
        this.cache = config.getCache();
        this.loadingResId = config.getLoadingResId();
        this.hasListener = config.isHasListener();
        this.policy = config.getPolicy();
        this.softReference = new SoftReference<>(config.getImageView());
        this.uri = config.getUrl();
        this.file = config.getFile();
        this.resId = config.getResId();
        loaderMode = config.getLoaderMode();
        uriMD5Str();
        if (config.getImageView() != null){
            config.getImageView().setTag(uriMD5);
        }
    }

    private void uriMD5Str() {
        switch (loaderMode){
            case  HTTP:
                this.uriMD5 = MD5Utils.toMD5(uri);
                break;
            case  ASSETS:
                this.uriMD5 = MD5Utils.toMD5(resId+"8023");
                break;
            case FILE:
                this.uriMD5 = MD5Utils.toMD5(file.getAbsolutePath());
                break;
        }
    }

    public LoaderMode getLoaderMode() {
        return loaderMode;
    }


    public File getFile() {
        return file;
    }

    public int getResId() {
        return resId;
    }

    public int getRequestId() {
        return requestId;
    }

    public void setRequestId(int requestId) {
        this.requestId = requestId;
    }

    public String getUri() {
        return uri;
    }

    public SoftReference<ImageView> getSoftReference() {
        return softReference;
    }

    public ImageView getImageView() {
        return softReference.get();
    }


    public IPolicy getPolicy() {
        return policy;
    }

    public String getUriMD5() {
        return uriMD5;
    }

    public int getLoadingResId() {
        return loadingResId;
    }

    public BitmapCache getCache() {
        return cache;
    }

    public boolean isHasListener() {
        return hasListener;
    }

    public BitmapConfig.BitmapListener getBitmapListener() {
        return bitmapListener;
    }

    public Context getContext() {
        return context;
    }

    @Override
    public int compareTo(BitmapRequest o) {
        return policy.compareTo(this,o);
    }
}
