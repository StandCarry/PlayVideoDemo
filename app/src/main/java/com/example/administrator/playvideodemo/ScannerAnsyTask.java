package com.example.administrator.playvideodemo;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;


import android.provider.MediaStore;


import java.util.ArrayList;

import java.util.List;

/**
 * 避免ANR,开启线程扫描视频
 */

public class ScannerAnsyTask extends AsyncTask<Void, Integer, List<VideoInfo>> {

    private Context mContext;

    @Override
    protected List<VideoInfo> doInBackground(Void... params) {
        getAudio(); //扫描手机视频

        return null;
    }

    /**
     * 扫描内存卡的视频
     */

    private void getAudio() {

        String[] projection = {MediaStore.Video.Media._ID,
                MediaStore.Video.Media.DISPLAY_NAME,
                MediaStore.Video.Media.DATA};
        String orderBy = MediaStore.Video.Media.DISPLAY_NAME;
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

        getContentProvider(uri, projection, orderBy);

    }



    public void getContentProvider(Uri uri, String[] projection, String orderBy) {

        Cursor cursor = mContext.getContentResolver().query(uri, projection, null,
                null, orderBy);
        if (null == cursor) {
            return;
        }
        while (cursor.moveToNext()) {
            VideoInfo videoInfo = new VideoInfo(cursor.getString(1), cursor.getString(2));
            videoInfo.save();  //保存到数据库

        }

    }

    public void setmHanlder(Context context) {

        mContext = context;
    }

}
