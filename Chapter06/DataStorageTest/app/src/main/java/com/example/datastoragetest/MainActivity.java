package com.example.datastoragetest;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    private EditText mEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mEditText = findViewById(R.id.editText);
    }

    public void writeToFile(View view) throws IOException {
//        openFileOutput将指定文件写出到/data/data/<package name>/files/下
//        /data/data/com.example.datastoragetest/files/file_data
        // Context.MODE_PRIVATE表示只有当前应用程序才可以对该文件读写
        FileOutputStream fos = openFileOutput("file_data", Context.MODE_PRIVATE);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        bw.write(mEditText.getText().toString());
        bw.flush();
        bw.close();
        fos.close();

        Log.d(TAG, "writeToFile ok");
    }

    public void readFromFile(View view) throws IOException {
//        openFileInput将从/data/data/<package name>/files/下读取文件
//        /data/data/com.example.datastoragetest/files/file_data
        FileInputStream fis = openFileInput("file_data");

        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        mEditText.setText(br.readLine());
        br.close();
        fis.close();

        Log.d(TAG, "read from file ok");
    }

    /**
     * getPreferences(Context.MODE_PRIVATE);将会在/data/data/<package name>/shared_prefs/产生MainActivity.xml 以当前活动为文件名前缀。
         <map>
             <string name="name">LewJun</string>
             <float name="height" value="1.76" />
             <long name="timestamp" value="1516025768014" />
             <int name="age" value="30" />
             <boolean name="sex" value="true" />
         </map>

     getSharedPreferences("data.prefs", Context.MODE_PRIVATE); 如上目录产生data.prefs.xml

     PreferenceManager.getDefaultSharedPreferences(this);如上目录产生当前包名为文件名.xml
     \data\data\com.example.datastoragetest\shared_prefs\com.example.datastoragetest_preferences.xml
     * @param view
     */
    public void writeToPs(View view) {
        SharedPreferences sp = //getPreferences(Context.MODE_PRIVATE);
//        getSharedPreferences("data.prefs", Context.MODE_PRIVATE);
          PreferenceManager.getDefaultSharedPreferences(this);

        // 得到编辑器
        SharedPreferences.Editor editor = sp.edit();

        // 写入数据
        editor.putString("name", "LewJun");
        editor.putInt("age", 30);
        editor.putFloat("height", 1.76f);
        editor.putBoolean("sex", true);
        editor.putLong("timestamp", System.currentTimeMillis());
        // 保存
        editor.apply();
        Log.d(TAG, "writeToPs: ok");
    }

    public void readFromPs(View view) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(this);
        StringBuilder sb = new StringBuilder();
        sb.append(sp.getString("name", "defValue"))
                .append(sp.getInt("age", 0))
                .append(sp.getFloat("height", 0f))
                .append(sp.getBoolean("sex", false))
                .append(sp.getLong("timestamp", 0L));
        mEditText.setText(sb.toString());
    }
}
