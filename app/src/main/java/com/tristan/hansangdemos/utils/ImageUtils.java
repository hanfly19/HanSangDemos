package com.tristan.hansangdemos.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.tristan.hansangdemos.R;


/**
 * Created by tristan on 2015/8/6 0006.
 * 图片加载工具类
 */
public class ImageUtils {


    public static void init(Context context) {
        ImageLoaderConfiguration configuration = new ImageLoaderConfiguration.Builder(context)
                .diskCacheSize(100 * 1024 * 1024)
                .build();
        ImageLoader.getInstance().init(configuration);
    }


    private static DisplayImageOptions options;

    public static DisplayImageOptions getImageOption() {
        if (options == null) {
            options = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.mipmap.ic_launcher) // resource or drawable
                    .showImageForEmptyUri(R.mipmap.ic_launcher) // resource or drawable
                    .showImageOnFail(R.mipmap.ic_launcher) // resource or drawable
                    .delayBeforeLoading(1000)
                    .cacheInMemory(true) // default
                    .cacheOnDisk(true) // default
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                            //设置显示圆角图片
//	                .displayer(new RoundedBitmapDisplayer(5))
//                  .displayer(new FadeInBitmapDisplayer(500)) 渐进载入图片
                    .build();
        }
        return options;

    }


}
