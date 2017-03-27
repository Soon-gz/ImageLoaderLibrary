package com.example.lemonimagelibrary.policy;

import com.example.lemonimagelibrary.request.BitmapRequest;

/**
 * Created by ShuWen on 2017/3/22.
 * 用于控制图片加载顺序，比如listview展示时，希望先加载最下面的，也就是以降序来加载图片
 */

public interface IPolicy {
    int compareTo(BitmapRequest request1,BitmapRequest request2);
}
