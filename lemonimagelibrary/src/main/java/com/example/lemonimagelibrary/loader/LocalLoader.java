package com.example.lemonimagelibrary.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.lemonimagelibrary.request.BitmapRequest;
import com.example.lemonimagelibrary.util.BitmapDecoder;
import com.example.lemonimagelibrary.util.ImageHelper;

import java.io.File;


/**
 * Created by ShuWen on 2017/3/26.
 */

public class LocalLoader extends AbstractLoader {
    @Override
    protected Bitmap onLoad(BitmapRequest request) {
        final File file =request.getFile();

        if (!file.exists()){
            return null;
        }

        BitmapDecoder bitmapDecoder = new BitmapDecoder() {
            @Override
            public void deleteFile() {

            }

            @Override
            protected Bitmap decodeBitmapWithOptions(BitmapFactory.Options options) {
                Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath(),options);
                return bitmap;
            }
        };

        return bitmapDecoder.decodeBitmap(ImageHelper.getImageWidth(request.getImageView()),
                ImageHelper.getImageHeight(request.getImageView()));
    }
}
