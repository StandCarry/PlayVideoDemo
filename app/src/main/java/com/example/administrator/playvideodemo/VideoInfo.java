package com.example.administrator.playvideodemo;

import org.litepal.crud.DataSupport;

/**
 * Created by Administrator on 2016/12/27.
 */

public class VideoInfo extends DataSupport{

    private String VideoName;
    private String path;

    public VideoInfo(String videoName, String path) {
        VideoName = videoName;
        this.path = path;
    }

    public String getVideoName() {
        return VideoName;
    }

    public void setVideoName(String videoName) {
        VideoName = videoName;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
