package com.example.datastoragetest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by LewJun on 2018/1/15.
 */

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = "MySQLiteOpenHelper";

    private Context mContext;

    public MySQLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    private static final String BOOK_SQL = "create table book (\n" +
            "id integer primary key autoincrement,\n" +
            "name text,\n" +
            "author integer,\n" +
            "price real,\n" +
            "pages integer\n" +
            ")";

    private static final String AUTHOR_SQL = "create table author (\n" +
            "id integer primary key autoincrement,\n" +
            "name text\n" +
            ")";

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "onCreate: ");
        db.execSQL(BOOK_SQL);
        db.execSQL(AUTHOR_SQL);
        Toast.makeText(mContext, "create success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG, "onUpgrade: oldVersion: " + oldVersion + ", newVersion: " + newVersion);

        String[] tables = {
                "book"
                , "author"
        };
        for (String table : tables) {
            String back_sql = "create table " + table + oldVersion + " as select * from " + table;
            db.execSQL(back_sql);
        }


        for (String table : tables) {
            String drop_sql = "drop table if exists " + table;
            db.execSQL(drop_sql);
        }

        onCreate(db);

        String all_table_sql = "select name from table_schema";
        Cursor cursor = db.query("table_schema", new String[]{"name"}, null, null, null, null, null);
//        String table_info_sql = "pragma table_info(["+ table_name +"])";


        for (String table : tables) {
            String drop_sql = "drop table if exists " + table + oldVersion;
            db.execSQL(drop_sql);
        }
    }
}
