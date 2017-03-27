package com.example.lemonimagelibrary.policy;

import com.example.lemonimagelibrary.request.BitmapRequest;

/**
 * Created by ShuWen on 2017/3/22.
 * 以id降序加载图片
 */

public class ReversePolicy implements IPolicy {
    @Override
    public int compareTo(BitmapRequest request1, BitmapRequest request2) {
        return request2.getRequestId() - request1.getRequestId();
    }
}
