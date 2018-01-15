package com.example.datastoragetest;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;

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
}
