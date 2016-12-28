package com.example.administrator.playvideodemo;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;

import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private ProgressDialog progressDialog;
    private ListView listView;
    private List<VideoInfo> videoInfos = new ArrayList<VideoInfo>();
    private DrawerLayout mDrawerLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_video_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView = (NavigationView) findViewById(R.id.nav_view);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.home);

        }

        navView.setCheckedItem(R.id.internet_video);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.local_video:
                        //运行时权限判断
                        if (ContextCompat.checkSelfPermission(MainActivity.this,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                                PackageManager.PERMISSION_GRANTED
                                ) {
                            ActivityCompat.requestPermissions(MainActivity.this
                                    , new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);

                        } else {
                            LoadLocalVideo();          //加载本地视频
                        }

                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.internet_video:

                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.call_phone:

                        mDrawerLayout.closeDrawers();
                        break;
                    case R.id.personal_center:

                        mDrawerLayout.closeDrawers();
                        break;

                }

                return true;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "你点击悬浮按钮", Toast.LENGTH_SHORT).show();

            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                VideoInfo videoInfo = videoInfos.get(position);
                Intent intent = new Intent(MainActivity.this, PlayVideoActivity.class);
                intent.putExtra("video_path", videoInfo.getPath());
                intent.putExtra("video_name", videoInfo.getVideoName());
                startActivity(intent);


            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar, menu);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.backup:
                //do something
                Toast.makeText(this, "你点击一下", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                //do something
                Toast.makeText(this, "你点击一下", Toast.LENGTH_SHORT).show();
                break;

            case R.id.settings:
                //do something
                Toast.makeText(this, "你点击一下", Toast.LENGTH_SHORT).show();
                break;
            case android.R.id.home:

                mDrawerLayout.openDrawer(GravityCompat.START);

                break;

            default:
        }

        return true;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {

            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager
                        .PERMISSION_GRANTED) {
                    LoadLocalVideo();
                } else {
                    Toast.makeText(MainActivity.this, "你拒绝了某个权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:

        }


    }

    /**
     * 显示进度对话框
     */

    private void showProgressDialog() {

        if (progressDialog == null) {
            progressDialog = new ProgressDialog(MainActivity.this);
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

    /**
     * 加载本地视频
     */

    private void LoadLocalVideo() {

        Connector.getDatabase(); //创建数据库

        videoInfos = DataSupport.findAll(VideoInfo.class);
        if (videoInfos.isEmpty()) {
            showProgressDialog();
            ScannerAnsyTask scannerAnsyTask = new ScannerAnsyTask();
            scannerAnsyTask.setmHanlder(MainActivity.this);
            scannerAnsyTask.execute();

            videoInfos = DataSupport.findAll(VideoInfo.class);
            VideoAdapter videoAdapter = new VideoAdapter(MainActivity.this, videoInfos);
            listView.setAdapter(videoAdapter);
            closeProgressDialog();

        } else {
            showProgressDialog();
            VideoAdapter videoAdapter = new VideoAdapter(MainActivity.this, videoInfos);
            listView.setAdapter(videoAdapter);
            closeProgressDialog();
        }
    }


}
