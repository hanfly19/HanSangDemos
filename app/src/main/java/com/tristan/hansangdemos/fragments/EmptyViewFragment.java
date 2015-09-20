package com.tristan.hansangdemos.fragments;


import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.loopj.android.http.RequestParams;
import com.tristan.hansangdemos.R;
import com.tristan.hansangdemos.utils.EncryptUtils;
import com.tristan.hansangdemos.utils.HttpUtils;

import java.util.HashMap;
import java.util.TreeMap;

/**
 * A simple {@link Fragment} subclass.
 */
public class EmptyViewFragment extends Fragment {



    public EmptyViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_empty_view, container, false);
        View emptyView = inflater.inflate(R.layout.custom_bottom,container,false);
        ListView listView = (ListView)view.findViewById(R.id.mList);
        ViewGroup parentView = (ViewGroup)listView.getParent();
        parentView.addView(emptyView);
        listView.setEmptyView(emptyView);
        TreeMap<String,String> map = new TreeMap<>();
        map.put("image_name","guodongshuai.jpg");
        map.put("version","0.1");
        map.put("md5", EncryptUtils.ff(map));
        map.remove("version");
        HttpUtils.doPost(getActivity(), "http://test.taoquanqiu.com/common/Test/testCode", new RequestParams(map), new HttpUtils.RequestCallBack() {
            @Override
            public void onStart() {

            }

            @Override
            public void onSuccess(String response) {
                Log.i("-->>","response = "+response);
            }

            @Override
            public void onFail(String response) {

            }
        });
        return view;
    }


}
