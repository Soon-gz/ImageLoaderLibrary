package com.example.lemonimagelibrary.loader;

import android.util.Log;

import com.example.lemonimagelibrary.config.GlobalConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ShuWen on 2017/3/23.
 * 对所有加载器进行管理
 */

@SuppressWarnings("ALL")
public class LoaderManager {

    private static  LoaderManager instance;

    private static byte[] lock = new byte[0];

    private Map<String,ILoader> loaderMap = new HashMap<>();


    public static LoaderManager getInstance(){
        if (instance == null){
            synchronized (lock){
                if (instance == null){
                    instance = new LoaderManager();
                }
            }
        }
        return instance;
    }

    private LoaderManager(){
        register("http",new UrlLoader());
        register("file",new LocalLoader());
        register("assets",new AssetsLoader());
    }

    //对所有加载器进行管理
    private void register(String s,ILoader loader) {
        loaderMap.put(s,loader);
    }

    //获得加载器
    public ILoader getILoader(String key){
        if (loaderMap.keySet().contains(key)){
           return loaderMap.get(key);
        }else {
            Log.e(GlobalConfig.TAG,"没有对应的加载器！");
            return null;
        }
    }


}
