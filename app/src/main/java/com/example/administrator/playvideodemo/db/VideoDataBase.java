package com.example.administrator.playvideodemo.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Administrator on 2016/12/29.
 */

public class VideoDataBase extends SQLiteOpenHelper {

    private Context mContext;

    private static VideoDataBase sInstance;

    private static final String CREATE_VIDEO = "create table Video("
            + "id integer primary key autoincrement, "
            + "video_name text UNIQUE,"
            + "video_path text UNIQUE,"
            + "video_image blob UNIQUE)";

    public static VideoDataBase getInstance(Context context, String DB_Name, SQLiteDatabase.CursorFactory factory, int Version) {
        if (sInstance == null) {
            sInstance = new VideoDataBase(context, DB_Name, factory, Version);
        }
        return sInstance;
    }


    private VideoDataBase(Context context, String name
            , SQLiteDatabase.CursorFactory factory
            , int version) {
        super(context, name, factory, version);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_VIDEO);

        Toast.makeText(mContext, "创建数据库成功", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Video");

        onCreate(db);

    }
}
