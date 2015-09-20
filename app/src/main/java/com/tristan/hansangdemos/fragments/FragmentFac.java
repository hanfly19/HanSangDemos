package com.tristan.hansangdemos.fragments;

import android.app.Fragment;

import com.tristan.hansangdemos.utils.FragmentType;

import java.io.FileInputStream;

/**
 * Created by tristan on 2015/7/30 0030.
 */
public class FragmentFac {


    public static Fragment getFragment(int type){
        Fragment f = null;
        switch (type){
            case FragmentType.TOOLBAR:
                f = new ToolBarFragment();
                break;
            case FragmentType.EVENTBUS:
                f = new EventBusFragment();
                break;
            case FragmentType.RECYCLER:
                f = new RecyclerVIewFragment();
                break;
            case FragmentType.EMPTYVIEW:
                f = new EmptyViewFragment();
                break;
            case FragmentType.DISKLRUCACHE:
                f = new DiskLrucacheFragment();
                break;
            case FragmentType.QINIU:
                f = new QiniuFragment();
                break;
            case FragmentType.SWIPE:
                f = new SwipeLayoutFragment();
                break;
            default:
                throw new UnsupportedOperationException("no such type Fragement");
        }
        return f;
    }


}
