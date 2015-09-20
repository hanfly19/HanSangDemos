package com.tristan.hansangdemos;

import android.app.Application;

import com.tristan.hansangdemos.utils.ImageUtils;

/**
 * Created by tristan on 2015/8/6 0006.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        ImageUtils.init(this);
    }
}
