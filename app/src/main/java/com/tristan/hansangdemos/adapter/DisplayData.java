package com.tristan.hansangdemos.adapter;

/**
 * Created by triatan on 2015/7/28 0028.
 */
public class DisplayData {

    public final static int TYPE_TXT = 10;
    public final static int TYPE_IMG = 11;
    public final static int TYPE_MULTI = 12;

    private int type;
    private String content;

    public int getType() {
        return type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setType(int type) {
        this.type = type;
    }
}
