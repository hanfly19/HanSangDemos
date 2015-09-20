package com.tristan.hansangdemos.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tristan.hansangdemos.R;


import java.util.List;

/**
 * Created by tristan on 2015/7/28 0028.
 */
public class Madapter extends MCyclerAdapter<DisplayData> {
    @Override
    public int getDisplayType(int postion) {
        return list.get(postion).getType();
    }

    public Madapter(List<DisplayData> list, Context context) {
        super(list, context);
    }

    @Override
    public RecyclerView.ViewHolder onCreateDisplayHolder(ViewGroup parent, int viewType) {

        View view = null;
        RecyclerView.ViewHolder holder = null;
        switch (viewType){
            case DisplayData.TYPE_TXT:
                view = inflater.inflate(R.layout.item_txt,parent,false);
                holder = new TextHolder(view);
                break;
            case DisplayData.TYPE_IMG:
                view = inflater.inflate(R.layout.item_img,parent,false);
                holder = new ImageHolder(view);
                break;
            case DisplayData.TYPE_MULTI:
                break;
        }
        return holder;
    }

    @Override
    public void onBindData(RecyclerView.ViewHolder holder, int position) {

        DisplayData data = list.get(position);
        switch (getDisplayType(position)){
            case DisplayData.TYPE_TXT:
               TextHolder textHolder = (TextHolder)holder;
                textHolder.mTxt.setText(data.getContent());
                break;
            case DisplayData.TYPE_IMG:
              ImageHolder imageHolder = (ImageHolder)holder;
                if (data.getContent().equals("1")){
                    imageHolder.mImg.setImageResource(R.drawable.img001);
                }else{
                    imageHolder.mImg.setImageResource(R.drawable.img002);
                }
                break;
            case DisplayData.TYPE_MULTI:
                break;
        }
    }

    class TextHolder extends BaseHolder{

        TextView mTxt;
        public TextHolder(View itemView) {
            super(itemView, new CycleItemClilkListener() {
                @Override
                public void onCycleItemClick(View view, int position) {
                    Toast.makeText(context,position+"",Toast.LENGTH_SHORT).show();
                }
            });
            mTxt = (TextView)itemView.findViewById(R.id.mText);
        }
    }

    class ImageHolder extends  RecyclerView.ViewHolder{

        ImageView mImg;
        public ImageHolder(View itemView) {
            super(itemView);
            mImg = (ImageView)itemView.findViewById(R.id.mImg);
        }
    }

}
