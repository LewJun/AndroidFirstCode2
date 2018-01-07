package com.example.uiwidgettest.containers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.uiwidgettest.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

//        为ListView控件准备数据
        initDatas();

//        获取ListView控件
        ListView listView = findViewById(R.id.list_view);
//        初始化适配器ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,
                datas
        );
//        设置适配器
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Toast.makeText(this, datas.get(position), Toast.LENGTH_SHORT).show();
        });
    }

    private List<String> datas = new ArrayList<>();

    private void initDatas() {
        String[] dataArr = {"Apple", "Banana", "Orange", "Watermelon",
                "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango",
                "Apple", "Banana", "Orange", "Watermelon", "Pear", "Grape",
                "Pineapple", "Strawberry", "Cherry", "Mango"};

        datas = Arrays.asList(dataArr);
    }
}
