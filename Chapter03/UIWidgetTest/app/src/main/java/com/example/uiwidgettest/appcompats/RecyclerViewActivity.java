package com.example.uiwidgettest.appcompats;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.uiwidgettest.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        initDatas2();
        RecyclerView recycler_view = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        设置RecyclerView的布局方式 此处为线性布局
        recycler_view.setLayoutManager(layoutManager);

//        添加分割线（RecyclerView默认没有分割线）
        DividerItemDecoration decor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recycler_view.addItemDecoration(decor);

        FruitAdapter adapter = new FruitAdapter(fruits);
//        设置RecyclerView的适配器
        recycler_view.setAdapter(adapter);
    }

    List<Fruit> fruits = new ArrayList<>();

    private void initDatas2() {
        String[] dataArr = {"Apple", "Banana", "Orange", "Watermelon",
                "Pear", "Grape", "Pineapple", "Strawberry", "Cherry", "Mango",
                "Apple", "Banana", "Orange", "Watermelon", "Pear", "Grape",
                "Pineapple", "Strawberry", "Cherry", "Mango"};

        for (int i = 0; i < dataArr.length; i++) {
            Fruit fruit = new Fruit();
            fruit.setImgId(R.mipmap.ic_launcher_round);
            fruit.setName(dataArr[i]);

            fruits.add(fruit);
        }
    }
}
