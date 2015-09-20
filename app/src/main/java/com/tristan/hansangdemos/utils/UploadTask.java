package com.tristan.hansangdemos.utils;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tristan on 2015/8/10 0010.
 */
public class UploadTask extends AsyncTask<File, Void, List<File>> {

    private Dialog dialog;
    private Context context;

    private List<Bitmap> imgs;
    public UploadTask(Context context,List<Bitmap> imgs) {
        this.context = context;
        this.imgs = imgs;
        dialog = new ProgressDialog(context);
    }



    @Override
    protected void onPreExecute() {
        if (dialog != null) {
            dialog.show();
        }
        super.onPreExecute();
    }

    @Override
    protected List<File> doInBackground(File... params) {
        File baseFile = PhotoUtils.getDiskCacheDir(context, "imgCache");
        List<File> files = new ArrayList<>();
        for (int i = 0; i < imgs.size(); i++) {
            String imgName = MD5Utils.md5(System.currentTimeMillis()+".png");
            File filePath = new File(baseFile,imgName);
            files.add(filePath);
            PhotoUtils.compressImage(imgs.get(i),filePath,60);
        }
        return files;
    }

    @Override
    protected void onPostExecute(List<File> files) {

        super.onPostExecute(files);
        if (dialog != null) {
            dialog.dismiss();
        }
        //七牛上传图片
    }
}
