package com.tristan.hansangdemos.utils;



import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by tristan on 2015/9/11 0011.
 */
public class EncryptUtils {
    private static StringBuilder sb = new StringBuilder();

    public static String ff(TreeMap<String, String> map) {
        for (Map.Entry<String, String> kv : map.entrySet()) {
            sb.append(kv.getKey() + "=" + kv.getValue() + "&");
        }
        sb.append("bull-b@tqq`c0m");
        return SHA(sb.toString().toUpperCase());
    }

    public static String SHA(String decript) {
        try {
            MessageDigest digest = java.security.MessageDigest
                    .getInstance("SHA");
            digest.update(decript.getBytes());
            byte messageDigest[] = digest.digest();
            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            // 字节数组转换为 十六进制 数
            for (int i = 0; i < messageDigest.length; i++) {
                String shaHex = Integer.toHexString(messageDigest[i] & 0xFF);
                if (shaHex.length() < 2) {
                    hexString.append(0);
                }
                hexString.append(shaHex);
            }
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
