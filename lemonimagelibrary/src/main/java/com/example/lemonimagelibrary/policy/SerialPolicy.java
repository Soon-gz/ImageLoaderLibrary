package com.example.lemonimagelibrary.policy;

import com.example.lemonimagelibrary.request.BitmapRequest;

/**
 * Created by ShuWen on 2017/3/22.
 * 正常默认的加载顺序，请求存入队列，先进先出
 */

public class SerialPolicy implements IPolicy {
    @Override
    public int compareTo(BitmapRequest request1, BitmapRequest request2) {
        return request1.getRequestId() - request2.getRequestId();
    }
}
