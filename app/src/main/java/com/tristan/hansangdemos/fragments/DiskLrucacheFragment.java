package com.tristan.hansangdemos.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tristan.hansangdemos.R;
import com.tristan.hansangdemos.bean.Images;
import com.tristan.hansangdemos.utils.ImageUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class DiskLrucacheFragment extends Fragment {


    public DiskLrucacheFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_disk_lrucache, container, false);
        GridView gridView = (GridView)view.findViewById(R.id.mGrid);
        gridView.setAdapter(new MyAdapter());
        return view;
    }

    class MyAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return Images.imageThumbUrls.length;
        }

        @Override
        public Object getItem(int position) {
            return Images.imageThumbUrls[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ImageView im = null;
            if (convertView==null){
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.item_img,null);
            }
            im = (ImageView)convertView.findViewById(R.id.mImg);

            String url = (String)getItem(position);
            ImageLoader.getInstance().displayImage(url,im,ImageUtils.getImageOption());

            return convertView;
        }
    }

}
