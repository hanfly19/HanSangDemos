package com.tristan.hansangdemos.fragments;

import android.os.Bundle;


import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.melnykov.fab.FloatingActionButton;
import com.tristan.hansangdemos.R;
import com.tristan.hansangdemos.adapter.DisplayData;
import com.tristan.hansangdemos.adapter.MCyclerAdapter;
import com.tristan.hansangdemos.adapter.Madapter;
import com.tristan.hansangdemos.custome.DividerGridItemDecoration;
import com.tristan.hansangdemos.custome.DividerItemDecoration;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * create an instance of this fragment.
 */
public class RecyclerVIewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


    private RecyclerView recyclerView;

    private Madapter madapter;

    public RecyclerVIewFragment() {
        // Required empty public constructor
    }

    ArrayList<DisplayData> datas;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        datas = new ArrayList<>();
        DisplayData data = new DisplayData();
        data.setType(DisplayData.TYPE_TXT);
        data.setContent("走吧，走吧");
        datas.add(data);
        data = new DisplayData();
        data.setContent("1");
        data.setType(DisplayData.TYPE_IMG);
        datas.add(data);
        data = new DisplayData();
        data.setContent("dd");
        data.setType(DisplayData.TYPE_IMG);
        datas.add(data);
        data = new DisplayData();
        data.setContent("能看见雨中的你吗");
        data.setType(DisplayData.TYPE_TXT);
        datas.add(data);
        data = new DisplayData();
        data.setContent("1");
        data.setType(DisplayData.TYPE_IMG);
        datas.add(data);
        data = new DisplayData();
        data.setContent("1");
        data.setType(DisplayData.TYPE_IMG);
        datas.add(data);
        data = new DisplayData();
        data.setContent("fa");
        data.setType(DisplayData.TYPE_IMG);
        datas.add(data);
        data = new DisplayData();
        data.setContent("把说了没做的事慢慢做完");
        data.setType(DisplayData.TYPE_TXT);
        datas.add(data);
        data = new DisplayData();
        data.setContent("感性的讲现在开始都不会晚");
        data.setType(DisplayData.TYPE_TXT);
        datas.add(data);
        data = new DisplayData();
        data.setContent("记得去年夏天的冷吗？");
        data.setType(DisplayData.TYPE_TXT);
        datas.add(data);
        data = new DisplayData();
        data.setContent("韩桑");
        data.setType(DisplayData.TYPE_TXT);
        datas.add(data);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_recycler_view, container, false);
        FloatingActionButton fab = (FloatingActionButton)view.findViewById(R.id.fab);
        recyclerView = (RecyclerView) view.findViewById(R.id.mCycle);
        fab.attachToRecyclerView(recyclerView);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),3));
        madapter = new Madapter(datas, getActivity());
        madapter.setIsMultiType(true);
        madapter.setIsLoadMore(true);
//        madapter.setCustomFootView(LayoutInflater.from(getActivity()).inflate(R.layout.custom_bottom, null));
        madapter.setMoreListerner(new MCyclerAdapter.OnLoadMoreListerner() {
            @Override
            public void loadMore() {

//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        try {
////                            Thread.sleep(2000);
//                            handler.sendEmptyMessage(110);
//                            Log.i("-->>", "handler send");
//                        } catch (InterruptedException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                }).run();
                addData();

            }
        });
        recyclerView.addItemDecoration(new DividerGridItemDecoration(getActivity()));
        recyclerView.setAdapter(madapter);

        return view;
    }

//     Handler handler = new Handler(){
//        @Override
//        public void handleMessage(Message msg) {
//            super.handleMessage(msg);
//
//                addData();
//        }
//    };


    private void addData() {
        DisplayData data = new DisplayData();
        data.setContent("能看见雨中的你吗");
        data.setType(DisplayData.TYPE_TXT);
        datas.add(data);
        data = new DisplayData();
        data.setContent("1");
        data.setType(DisplayData.TYPE_IMG);
        datas.add(data);
        data = new DisplayData();
        data.setContent("1");
        data.setType(DisplayData.TYPE_IMG);
        datas.add(data);
        data = new DisplayData();
        data.setContent("fa");
        data.setType(DisplayData.TYPE_IMG);
        datas.add(data);
        data = new DisplayData();
        data.setContent("把说了没做的事慢慢做完");
        data.setType(DisplayData.TYPE_TXT);
        datas.add(data);
        data = new DisplayData();
        data.setContent("感性的讲现在开始都不会晚");
        data.setType(DisplayData.TYPE_TXT);
        datas.add(data);
        data = new DisplayData();
        data.setContent("记得去年夏天的冷吗？");
        data.setType(DisplayData.TYPE_TXT);
        datas.add(data);
        data = new DisplayData();
        data.setContent("韩桑");
        data.setType(DisplayData.TYPE_TXT);
        datas.add(data);
        madapter.notifyDataSetChanged();
    }

}
