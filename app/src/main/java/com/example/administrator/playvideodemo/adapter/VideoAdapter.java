package com.example.administrator.playvideodemo.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.playvideodemo.R;
import com.example.administrator.playvideodemo.Util.VideoThumbLoader;
import com.example.administrator.playvideodemo.moudle.VideoInfo;

import java.util.List;

/**
 * Created by Administrator on 2016/12/27.
 */

public class VideoAdapter extends BaseAdapter {

    private Context mContext;
    private List<VideoInfo> videoInfos;

    private VideoThumbLoader mVideoThumbLoader=new VideoThumbLoader();

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
        VideoInfo videoInfo = getItem(position);
        View view;
        ViewHolder viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_video, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.item_video_name = (TextView) view.findViewById(R.id.tv_video_name);
            viewHolder.item_video_image=(ImageView) view.findViewById(R.id.im_video_thumnail);

            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.item_video_name.setText(videoInfo.getVideoName());
        viewHolder.item_video_image.setBackgroundResource(R.mipmap.ic_launcher);
        String path = videoInfo.getPath();
        viewHolder.item_video_image.setTag(path);//绑定imageview
        mVideoThumbLoader.showThumbByAsynctack(path, viewHolder.item_video_image);

        return view;
    }

    class ViewHolder {

        TextView item_video_name;
        ImageView item_video_image;
    }


}
