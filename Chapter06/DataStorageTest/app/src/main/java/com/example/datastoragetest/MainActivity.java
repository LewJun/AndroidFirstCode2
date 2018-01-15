package com.example.datastoragetest;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
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
        FileOutputStream fos = openFileOutput("file_data", Context.MODE_PRIVATE);
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
        bw.write(mEditText.getText().toString());
        bw.flush();
        bw.close();
        fos.close();

        Log.d(TAG, "writeToFile ok");
    }

}
