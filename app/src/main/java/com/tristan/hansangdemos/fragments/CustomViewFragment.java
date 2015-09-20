package com.tristan.hansangdemos.fragments;


import android.graphics.Canvas;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.tristan.hansangdemos.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CustomViewFragment extends Fragment {


    public CustomViewFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_custom_view, container, false);
    }


}
