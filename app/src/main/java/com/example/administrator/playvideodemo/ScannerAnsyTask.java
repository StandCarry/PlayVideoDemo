package com.example.administrator.playvideodemo;

import android.os.AsyncTask;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * 避免ANR,开启线程扫描视频
 */

public class ScannerAnsyTask extends AsyncTask<Void,Integer,List<VideoInfo>>{

    private Handler mHanlder;

    private List<VideoInfo> videoInfos=new ArrayList<VideoInfo>();
    @Override
    protected List<VideoInfo> doInBackground(Void... params) {
        getVideoFile(videoInfos,Environment.getExternalStorageDirectory());

        Log.i("tga","最后的大小"+videoInfos.size());
        return videoInfos;
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List<VideoInfo> videoInfos) {
        super.onPostExecute(videoInfos);

        Message msg=mHanlder.obtainMessage();
        msg.what=1;
        msg.obj=videoInfos;
        mHanlder.sendMessage(msg);

    }

    /**
     * 扫描内存卡的视频
     */

    private void getVideoFile(final List<VideoInfo> list, File file) {// 获得视频文件

        file.listFiles(new FileFilter() {

            @Override
            public boolean accept(File file) {
                // sdCard找到视频名称
                String name = file.getName();

                int i = name.indexOf('.');
                if (i != -1) {
                    name = name.substring(i);
                    if (name.equalsIgnoreCase(".mkv")
                            || name.equalsIgnoreCase(".3gp")
                            || name.equalsIgnoreCase(".wmv")
                            || name.equalsIgnoreCase(".ts")
                            || name.equalsIgnoreCase(".rmvb")
                            || name.equalsIgnoreCase(".mov")
                            || name.equalsIgnoreCase(".m4v")
                            || name.equalsIgnoreCase(".avi")
                            || name.equalsIgnoreCase(".m3u8")
                            || name.equalsIgnoreCase(".3gpp")
                            || name.equalsIgnoreCase(".3gpp2")
                            || name.equalsIgnoreCase(".mp4")
                            || name.equalsIgnoreCase(".flv")
                            || name.equalsIgnoreCase(".divx")
                            || name.equalsIgnoreCase(".f4v")
                            || name.equalsIgnoreCase(".rm")
                            || name.equalsIgnoreCase(".asf")
                            || name.equalsIgnoreCase(".ram")
                            || name.equalsIgnoreCase(".mpg")
                            || name.equalsIgnoreCase(".v8")
                            || name.equalsIgnoreCase(".swf")
                            || name.equalsIgnoreCase(".m2v")
                            || name.equalsIgnoreCase(".asx")
                            || name.equalsIgnoreCase(".ra")
                            || name.equalsIgnoreCase(".ndivx")
                            || name.equalsIgnoreCase(".xvid")) {
                        VideoInfo vi = new VideoInfo();
                        vi.setVideoName(file.getName());
                        vi.setPath(file.getAbsolutePath());
                        list.add(vi);
                        return true;
                    }
                } else if (file.isDirectory()) {
                    getVideoFile(list, file);
                }
                return false;
            }
        });
    }


    public void setmHanlder(Handler hanlder){

        mHanlder=hanlder;

    }
}
