package com.example.administrator.playvideodemo.asynctask;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;


import android.provider.MediaStore;


import com.example.administrator.playvideodemo.db.VideoDataBase;
import com.example.administrator.playvideodemo.moudle.VideoInfo;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * 避免ANR,开启线程扫描视频
 */

public class ScannerAnsyTask extends AsyncTask<Void, Integer, List<VideoInfo>> {

    private Context mContext;
    private VideoDataBase dbHelper;
    private ProgressDialog progressDialog;

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        showProgressDialog();

    }

    @Override
    protected List<VideoInfo> doInBackground(Void... params) {
        getAudio(); //扫描手机视频

        return null;
    }


    @Override
    protected void onPostExecute(List<VideoInfo> videoInfos) {
        super.onPostExecute(videoInfos);
        closeProgressDialog();

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

    //读取出数据插入到数据库

    public void getContentProvider(Uri uri, String[] projection, String orderBy) {


        dbHelper = VideoDataBase.getInstance(mContext, "Videos.db", null, 1);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        Cursor cursor = mContext.getContentResolver().query(uri, projection, null,
                null, orderBy);

        if (null == cursor) {
            return;
        }
        while (cursor.moveToNext()) {

            values.put("video_name", cursor.getString(1));
            values.put("video_path", cursor.getString(2));

            db.insert("Video", null, values);
            values.clear();

        }

    }

    public void setContext(Context context) {

        mContext = context;
    }

    /**
     * 显示进度对话框
     */

    private void showProgressDialog() {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("正在扫描手机中的视频...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();

    }

    /**
     * 关闭进度对话框
     */
    private void closeProgressDialog() {

        if (progressDialog != null) {

            progressDialog.dismiss();
        }


    }


}
