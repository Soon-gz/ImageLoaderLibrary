package com.example.lemonimagelibrary.cache;

/**
 * Created by ShuWen on 2017/3/23.
 * 对缓存模式同意管理，暴露给调用层
 */

@SuppressWarnings("ALL")
public enum CacheMode {
    DISK_LRU_CACHE(0),MEMORY_LRU_CACHE(1),DOUBLE_CAHCE(2),NO_CAHCE(3);

    private int value;

    CacheMode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
