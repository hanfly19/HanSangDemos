package com.tristan.hansangdemos.utils;

import android.content.Context;
import android.text.TextUtils;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;

import org.apache.http.Header;


/**
 * Created by tristan on 2015/8/14 0014.
 */
public class HttpUtils {

    public static AsyncHttpClient httpClient = new AsyncHttpClient();
    static {
        httpClient.setTimeout(25000);
        httpClient.setMaxConnections(15);
        httpClient.setMaxRetriesAndTimeout(8, 20000);
    }

    public static void doGet(Context context, String url, RequestParams params,
                             final RequestCallBack callBack) {

        httpClient.get(context, url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                if (!TextUtils.isEmpty(s)){
                    if (callBack!=null){
                        callBack.onSuccess(s);
                    }
                }
            }
        });
    }

    public static void doGet(Context context, String url,
                             final RequestCallBack callBack) {
        httpClient.get(context, url, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                if (!TextUtils.isEmpty(s)){
                    if (callBack!=null){
                        callBack.onSuccess(s);
                    }
                }
            }
        });
    }

    public static void doPost(Context context, String url,
                              RequestParams params, final RequestCallBack callBack) {

        httpClient.post(context, url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int i, Header[] headers, String s, Throwable throwable) {

            }

            @Override
            public void onSuccess(int i, Header[] headers, String s) {
                if (!TextUtils.isEmpty(s)){
                    if (callBack!=null){
                        callBack.onSuccess(s);
                    }
                }
            }
        });
    }


    public static void cancelRequest(Context context) {
        httpClient.cancelRequests(context, true);
    }

    public interface RequestCallBack {
        public void onStart();

        public void onSuccess(String response);

        public void onFail(String response);

    }
}
