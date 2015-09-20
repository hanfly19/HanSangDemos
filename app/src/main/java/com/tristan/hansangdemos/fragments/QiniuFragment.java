package com.tristan.hansangdemos.fragments;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.Configuration;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UpProgressHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.android.storage.UploadOptions;
import com.tristan.hansangdemos.R;
import com.tristan.hansangdemos.utils.HttpUtils;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;


/**
 * A simple {@link Fragment} subclass.
 */
public class QiniuFragment extends Fragment {

    public static UploadManager uploadManager;
    private ProgressBar progressBar;

    public QiniuFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        uploadManager = initQiNiu();
    }

    private Handler handler = new Handler(){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){
                progressBar.setProgress((msg.arg1+msg.arg2)/2);
            }
        }
    };

    private double progress = 0.00;
    private Message message = new Message();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_blank, container, false);
        progressBar = (ProgressBar)view.findViewById(R.id.progressBar);
//        Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.img001);
//        Bitmap img2 = BitmapFactory.decodeResource(getResources(),R.drawable.img002);
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        img.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        final byte[] datas = baos.toByteArray();
//        baos.reset();
//        img2.compress(Bitmap.CompressFormat.PNG, 100, baos);
//        final byte[] data2 = baos.toByteArray();
//        view.findViewById(R.id.upload).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                uploadManager.put(datas, "12580.png",
//                        "Uky_VknD9UzIGsU4_7vRVvz5jaMihA-XtDVFvnM5:him5cHZ4Pt3zV_JJKBzqvEgGCgw=:eyJzY29wZSI6ImN1aWppamkiLCJkZWFkbGluZSI6MTQ0MDEzMTI1MX0=",
//                        new UpCompletionHandler() {
//                            @Override
//                            public void complete(String s, ResponseInfo responseInfo, JSONObject jsonObject) {
//                                Log.i("-->>", "info 1= " + responseInfo);
//                                Log.i("-->>", "json = " + jsonObject);
//                            }
//                        }, new UploadOptions(null, null, false,
//                                new UpProgressHandler() {
//                                    public void progress(String key, double percent) {
//                                        Log.i("qiniu", key + "p1 = " + percent);
////                                        Log.i("-->>","getProgress = "+progressBar.getProgress());
//                                        Log.i("-->>", "progress = " + progress);
////                                       message.arg1 = (int)(percent);
////                                        message.what= 1;
////                                        handler.sendMessage(message);
//
//                                    }
//                                }, null));
//
//
//
//                uploadManager.put(data2, "12375.png",
//                        "Uky_VknD9UzIGsU4_7vRVvz5jaMihA-XtDVFvnM5:him5cHZ4Pt3zV_JJKBzqvEgGCgw=:eyJzY29wZSI6ImN1aWppamkiLCJkZWFkbGluZSI6MTQ0MDEzMTI1MX0=",
//                        new UpCompletionHandler() {
//                            @Override
//                            public void complete(String s, ResponseInfo responseInfo, JSONObject jsonObject) {
//                                Log.i("-->>", "info 2= " + responseInfo);
//                                Log.i("-->>", "json = " + jsonObject);
//                            }
//                        }, new UploadOptions(null, null, false,
//                                new UpProgressHandler() {
//                                    public void progress(String key, double percent) {
//                                        Log.i("qiniu", key + "p2 =  " + percent);
////                                        int progress =(int)(progressBar.getProgress()+percent*50);
////                                        progressBar.setProgress(progress);
////                                        message.arg2 = (int)(percent);
////                                        message.what= 1;
////                                        handler.sendMessage(message);
//                                    }
//                                }, null));
//
//
//            }
//        });


//        HttpUtils.doGet(getActivity(), "http://121.201.7.55:8080/common/images/getToken", new HttpUtils.RequestCallBack() {
//            @Override
//            public void onStart() {
//
//            }
//
//            @Override
//            public void onSuccess(String response) {
//                Log.i("-->>","response = "+response);
//                Log.i("-->>","token = "+response.substring(1,response.length()-1));
//            }
//
//            @Override
//            public void onFail(String response) {
//
//            }
//        });

        return view;
    }

    public static UploadManager initQiNiu() {
        Configuration configuration = new Configuration.Builder().build();
        return new UploadManager(configuration);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
