package com.example.lemonimagelibrary.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.example.lemonimagelibrary.config.GlobalConfig;
import com.example.lemonimagelibrary.request.BitmapRequest;
import com.example.lemonimagelibrary.util.BitmapDecoder;
import com.example.lemonimagelibrary.util.ImageHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by ShuWen on 2017/3/22.
 * 网络加载器具体逻辑
 */

public class UrlLoader extends AbstractLoader {
    private final byte[]lock = new byte[0];

    @Override
    protected Bitmap onLoad(final BitmapRequest request) {
        downloadImage(request.getUri(),getCachePath(request.getUriMD5()));
        BitmapDecoder bitmapDecoder = new BitmapDecoder() {
            @Override
            public void deleteFile() {
                synchronized (lock){
                    if (getCachePath(request.getUriMD5()).exists() && getCachePath(request.getUriMD5()).delete()){
                    }else {
                    }
                }
            }

            @Override
            protected Bitmap decodeBitmapWithOptions(BitmapFactory.Options options) {
                Bitmap bitmap = BitmapFactory.decodeFile(getCachePath(request.getUriMD5()).getAbsolutePath(),options);
                return bitmap;
            }
        };
        return bitmapDecoder.decodeBitmap(ImageHelper.getImageWidth(request.getImageView()),
                        ImageHelper.getImageHeight(request.getImageView()));
    }

    private void downloadImage(String uri, File cachePath) {
        FileOutputStream fos = null;
        InputStream is = null;
        try
        {
            URL url = new URL(uri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            is = conn.getInputStream();
            fos = new FileOutputStream(cachePath);
            byte[] buf = new byte[512];
            int len;
            while ((len = is.read(buf)) != -1)
            {
                fos.write(buf, 0, len);
            }
            fos.flush();

        } catch (Exception e)
        {
            e.printStackTrace();
            Log.e(GlobalConfig.TAG,"图片下载异常");
        } finally
        {
            try
            {
                if (is != null)
                    is.close();
            } catch (IOException e)
            {
                Log.e(GlobalConfig.TAG,"图片下载异常");
            }

            try
            {
                if (fos != null)
                    fos.close();
            } catch (IOException e)
            {
                Log.e(GlobalConfig.TAG,"图片下载异常");
            }
        }
    }

    private File getCachePath(String uriMD5) {
        return new File(Environment.getExternalStorageDirectory(),uriMD5);
    }
}
