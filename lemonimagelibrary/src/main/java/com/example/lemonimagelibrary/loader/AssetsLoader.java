package com.example.lemonimagelibrary.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.lemonimagelibrary.request.BitmapRequest;
import com.example.lemonimagelibrary.util.BitmapDecoder;
import com.example.lemonimagelibrary.util.ImageHelper;

/**
 * Created by ShuWen on 2017/3/26.
 */

@SuppressWarnings("ALL")
public class AssetsLoader extends AbstractLoader {
    @Override
    protected Bitmap onLoad(final BitmapRequest request) {

        BitmapDecoder bitmapDecoder = new BitmapDecoder() {
            @Override
            public void deleteFile() {

            }

            @Override
            protected Bitmap decodeBitmapWithOptions(BitmapFactory.Options options) {
                Bitmap bitmap = BitmapFactory.decodeResource(request.getContext().getResources(),request.getResId(),options);
                return bitmap;
            }
        };

        return bitmapDecoder.decodeBitmap(ImageHelper.getImageWidth(request.getImageView()),
                ImageHelper.getImageHeight(request.getImageView()));
    }
}
