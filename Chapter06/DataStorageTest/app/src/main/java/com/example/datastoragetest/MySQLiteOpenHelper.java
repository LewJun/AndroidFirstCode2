package com.example.datastoragetest;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

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
            "id integer primary key autoincrement not null,\n" +
            "name text not null,\n" +
            "author integer,\n" +
            "price real,\n" +
            "pages integer\n" +
            ")";

    private static final String AUTHOR_SQL = "create table author (\n" +
            "id integer primary key autoincrement not null,\n" +
            "name text not null\n" +
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

        // 将要升级的表名都放在这里
        String[] tables = {
                "book"
                , "author"
        };

        // 对需要升级的表创建临时表
        for (String table : tables) {
            String back_sql = "create table " + table + "" + oldVersion + " as select * from " + table;
            db.execSQL(back_sql);
        }

        // 删除需要升级的表
        for (String table : tables) {
            String drop_sql = "drop table if exists " + table;
            db.execSQL(drop_sql);
        }

        // 执行创建或修改表语句
        onCreate(db);

        // 数据转移
        for (String table : tables) {
            // 得到现在表结构信息
            Cursor cursor_newVersion = db.rawQuery("pragma table_info([" + table + "])", null);
            // cid, name, type, notnull, dflt_value, pk
            if (cursor_newVersion.moveToFirst()) {
                List<String> newVersion_column_list = new ArrayList<>();
                do {
                    String column = cursor_newVersion.getString(cursor_newVersion.getColumnIndex("name"));
                    newVersion_column_list.add(column);
                } while (cursor_newVersion.moveToNext());

                // 得到以前表的表结构信息
                Cursor cursor_oldVersion = db.rawQuery("pragma table_info([" + table + oldVersion + "])", null);
                if (cursor_oldVersion.moveToFirst()) {
                    List<String> oldVersion_column_list = new ArrayList<>();
                    do {
                        String column = cursor_oldVersion.getString(cursor_oldVersion.getColumnIndex("name"));
                        if (newVersion_column_list.contains(column)) {
                            oldVersion_column_list.add(column);
                        }
                    } while (cursor_oldVersion.moveToNext());

                    if (oldVersion_column_list.size() > 0) {
                        StringBuilder sb = new StringBuilder();
                        for (String column : oldVersion_column_list) {
                            sb.append(column).append(",");
                        }
                        sb.deleteCharAt(sb.lastIndexOf(","));
                        String column_ = sb.toString();

                        // 往现有表字段插入以前的必要数据
                        String insertSql = "insert into " + table + " (" + column_ + ") as select ("
                                + column_ + ") from " + table + "" + oldVersion;
                        db.execSQL(insertSql);
                    }

                }
                cursor_oldVersion.close();
            }
            cursor_newVersion.close();
        }

        // 删除临时表
        for (String table : tables) {
            String drop_sql = "drop table if exists " + table + oldVersion;
            db.execSQL(drop_sql);
        }
    }
}
