package com.example.administrator.playvideodemo.moudle;


/**
 * Created by Administrator on 2016/12/27.
 */

public class VideoInfo {

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
