package com.example.uiwidgettest.appcompats;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.uiwidgettest.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewGridActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_h);

        initDatas2();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        // 设置为网格布局
        GridLayoutManager layoutManager = new GridLayoutManager(this, 3);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        FruitAdapter adapter = new FruitAdapter(R.layout.fruit_list_item_2, fruits);
        recyclerView.setAdapter(adapter);

        DividerItemDecoration decor = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(decor);
    }
}
