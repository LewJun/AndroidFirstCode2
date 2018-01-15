package com.example.datastoragetest;

import android.app.Application;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by LewJun on 2018/1/15.
 */

public class BaseApplication extends Application {
    private static SQLiteDatabase mSQLiteDatabase;

    public static SQLiteDatabase getSQLiteDatabase() {
        return mSQLiteDatabase;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        MySQLiteOpenHelper helper = new MySQLiteOpenHelper(this, "mydb", null, 1);
        mSQLiteDatabase = helper.getWritableDatabase();
    }
}
