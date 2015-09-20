package com.tristan.hansangdemos.utils;

import android.content.Context;


import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;

/**
 * Created by tristan on 2015/8/15 0015.
 */
public class DiskCacheUtils {
    private final static String JSON = "JSON";

    private DiskCacheUtils() {

    }

    public static DiskLruCache getJsonDefaultCache(Context context, int version) {
        File cacheDirectory = PhotoUtils.getDiskCacheDir(context, JSON);
        try {
            return DiskLruCache.open(cacheDirectory, version, 1, 20 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void saveJson(String json, DiskLruCache cache) {

        String key = MD5Utils.md5(System.currentTimeMillis() + "");
        OutputStream os = null;
        ByteArrayInputStream bais = null;
        DiskLruCache.Editor editor = null;
        byte[] buff = new byte[1024];
        try {
            editor = cache.edit(key);
            if (editor != null) {
                os = editor.newOutputStream(0);
                bais = new ByteArrayInputStream(json.getBytes());
                int len;
                while (bais.read(buff) != -1) {
                    len = bais.read(buff);
                    os.write(buff, 0, len);
                }
                editor.commit();
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                editor.abort();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } finally {
            try {
                os.close();
                bais.close();
                buff = null;
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public static String readJson(String key, DiskLruCache cache) {
        try {
            DiskLruCache.Snapshot snapshot = cache.get(key);
            InputStream is = snapshot.getInputStream(0);
            if (is != null) {
                return streamToString(is);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static String streamToString(InputStream is) {

        StringBuffer sb = new StringBuffer();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        String strLine = "";

        try {
            while ((strLine=br.readLine())!=null){
                sb.append(strLine.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return  sb.toString();

    }


}
