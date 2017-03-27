package com.example.lemonimagelibrary.util;

import android.view.ViewGroup;
import android.widget.ImageView;

import java.lang.reflect.Field;

/**
 * Created by ShuWen on 2017/3/20.
 */

@SuppressWarnings("ALL")
public class ImageHelper {
    //默认的图片宽高
    private static int DEFAULT_WIDTH = 200;
    private static int DEFAULT_HEIGHT = 200;

    public static int getImageWidth(ImageView imageView){
        if (imageView != null){
            int width = 0;
            ViewGroup.LayoutParams params = imageView.getLayoutParams();
            if (params != null && params.width != ViewGroup.LayoutParams.WRAP_CONTENT){
                width = imageView.getWidth();
            }

            if (width <= 0 && params != null){
                width = params.width;
            }

            if (width <= 0 ){
                width = getImageViewFieldValue(imageView,"mMaxWidth");
            }
            return width;
        }
        return DEFAULT_WIDTH;

    }

    public static int getImageHeight(ImageView imageView){
        if (imageView != null){
            int height = 0;
            ViewGroup.LayoutParams params = imageView.getLayoutParams();
            if (params != null && params.height != ViewGroup.LayoutParams.WRAP_CONTENT){
                height = imageView.getHeight();
            }

            if (height <= 0 && params != null){
                height = params.height;
            }

            if (height <= 0 ){
                height = getImageViewFieldValue(imageView,"mMaxHeight");
            }
            return height;
        }
        return DEFAULT_HEIGHT;

    }

    private static int getImageViewFieldValue(ImageView imageView, String mMaxWidth) {
        try {
            Field field = ImageView.class.getDeclaredField(mMaxWidth);
            field.setAccessible(true);
            int fieldValue = (Integer) field.get(imageView);
            if (fieldValue < 0 && fieldValue < Integer.MAX_VALUE){
                return fieldValue;
            }
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
