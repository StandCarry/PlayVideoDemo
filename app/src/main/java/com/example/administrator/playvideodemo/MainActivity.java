package com.example.administrator.playvideodemo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import java.util.ArrayList;
import java.util.List;



public class MainActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private ListView listView;
    private List<VideoInfo> videoInfos=new ArrayList<VideoInfo>();


    private Handler mHandler=new Handler(){ //得到子线程的扫描视频集合

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what==1){

                videoInfos=(ArrayList)msg.obj;
                Log.i("tga","传送过来最后的大小"+videoInfos.size());
                VideoAdapter videoAdapter=new VideoAdapter(MainActivity.this,videoInfos);
                listView.setAdapter(videoAdapter);
                closeProgressDialog();
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showProgressDialog();

        setContentView(R.layout.activity_main);
        listView=(ListView)findViewById(R.id.list_video_view);

        ScannerAnsyTask scannerAnsyTask=new ScannerAnsyTask();
        scannerAnsyTask.setmHanlder(mHandler);
        scannerAnsyTask.execute();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                VideoInfo videoInfo=videoInfos.get(position);
                Intent intent=new Intent(MainActivity.this,PlayVideoActivity.class);
                intent.putExtra("video_path",videoInfo.getPath());
                intent.putExtra("video_name",videoInfo.getVideoName());
                startActivity(intent);


            }
        });

    }
    /**
     * 显示进度对话框
     * */

    private void showProgressDialog(){

        if (progressDialog==null){
            progressDialog=new ProgressDialog(MainActivity.this);
            progressDialog.setMessage("正在扫描手机中的视频...");
            progressDialog.setCanceledOnTouchOutside(false);
        }
        progressDialog.show();

    }
    /**
     * 关闭进度对话框
     * */
    private void closeProgressDialog(){

        if (progressDialog!=null){

            progressDialog.dismiss();
        }


    }

}
