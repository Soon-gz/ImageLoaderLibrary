package com.example.lemonimagelibrary.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * Created by ShuWen on 2017/3/20.
 */

@SuppressWarnings({"unchecked", "JavaDoc"})
public abstract class BitmapDecoder {

    public Bitmap decodeBitmap(int reqWidth,int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        //只读取图片的大小
        options.inJustDecodeBounds = true;
        decodeBitmapWithOptions(options);
        calculateBitmap(options,reqWidth,reqHeight);
        Bitmap bitmap = decodeBitmapWithOptions(options);
        deleteFile();
        return bitmap;
    }

    public abstract void deleteFile();

    private void calculateBitmap(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        int width = options.outWidth;
        int height = options.outHeight;
        //图片缩放比
        int inSampleSizeX = 1;

        if (width > reqHeight || height > reqHeight){

            int widthSample = Math.round((float)width/(float)reqWidth);

            int heightSample = Math.round((float)height/(float)reqHeight);

            inSampleSizeX = Math.max(widthSample,heightSample);

        }

        options.inJustDecodeBounds = false;

        options.inSampleSize = inSampleSizeX;
        /**
         * bitmap是8888还是565编码,后者内存占用相当于前者一半,前者显示效果要好一点点,但两者效果不会差太多
         */
        options.inPreferredConfig = Bitmap.Config.RGB_565;

        options.inPurgeable = true;

        options.inInputShareable = true;

    }

    protected abstract Bitmap decodeBitmapWithOptions(BitmapFactory.Options options);


}
