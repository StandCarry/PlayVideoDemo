package com.example.administrator.playvideodemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/27.
 */

public class VideoAdapter extends BaseAdapter {

    private Context mContext;
    private List<VideoInfo> videoInfos;

    public VideoAdapter(Context mContext, List<VideoInfo> videoInfos) {
        this.mContext = mContext;
        this.videoInfos = videoInfos;
    }

    @Override
    public int getCount() {
        return videoInfos.size();
    }

    @Override
    public VideoInfo getItem(int position) {
        return videoInfos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        VideoInfo videoInfo=getItem(position);
        View view;
        ViewHolder viewHolder;
        if(convertView==null){
            view= LayoutInflater.from(mContext).inflate(R.layout.item_video,parent,false);
            viewHolder=new ViewHolder();
            viewHolder.item_video_name=(TextView)view.findViewById(R.id.tv_video_name);

            view.setTag(viewHolder);
        }
        else {
            view=convertView;
            viewHolder=(ViewHolder)view.getTag();
        }
        viewHolder.item_video_name.setText(videoInfo.getVideoName());


        return view;
    }

    class ViewHolder{

        TextView item_video_name;
    }
}
