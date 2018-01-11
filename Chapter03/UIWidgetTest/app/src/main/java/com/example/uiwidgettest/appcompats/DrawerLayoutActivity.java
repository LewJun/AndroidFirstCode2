package com.example.uiwidgettest.appcompats;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.uiwidgettest.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DrawerLayoutActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;

    private SwipeRefreshLayout mSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawer_layout);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // 实际上，toolbar最左侧的按钮叫做HomeAsUp按钮，默认图标是一个返回的箭头，含义是返回到上一个活动。

        drawerLayout = findViewById(R.id.drawer_layout);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // 让导航按钮显示出来
            actionBar.setDisplayHomeAsUpEnabled(true);
            // 设置一个导航按钮图标
            actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }

        NavigationView nav_view = findViewById(R.id.nav_view);
        // 默认选中
        nav_view.setCheckedItem(R.id.nav_call);
        // 添加点击事件
        nav_view.setNavigationItemSelectedListener(item -> {
            // 关闭滑动菜单
            drawerLayout.closeDrawers();
            return true;
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(v-> {
//            Toast.makeText(DrawerLayoutActivity.this, "fab is clicked", Toast.LENGTH_SHORT).show();
            Snackbar.make(v, "删除成功", Snackbar.LENGTH_SHORT)
                    .setAction("撤销", v2->{
                        Toast.makeText(DrawerLayoutActivity.this, "撤销成功", Toast.LENGTH_SHORT).show();
                    }).show();
        });


        initDatas2();
        RecyclerView recycler_view = findViewById(R.id.recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(this,2 );
//        设置RecyclerView的布局方式 此处为表格布局
        recycler_view.setLayoutManager(layoutManager);

        adapter = new FruitAdapter2(R.layout.fruit_list_item_3, fruits);
//        设置RecyclerView的适配器
        recycler_view.setAdapter(adapter);

        mSwipeRefreshLayout = findViewById(R.id.swipe_refresh);
        // 设置下拉刷新进度条的颜色
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
//        下拉刷新回调函数
        mSwipeRefreshLayout.setOnRefreshListener(()-> refreshFruits());
    }

    private void refreshFruits() {
        new Thread(()->{
            try {
                Thread.sleep(2000);
                runOnUiThread(()->{
                    initDatas2();
                    adapter.notifyDataSetChanged();
                    // 设置正在刷新中（false表示取消正在进行时）
                    mSwipeRefreshLayout.setRefreshing(false);
                });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }

    FruitAdapter2 adapter =null;

    List<Fruit> fruits = new ArrayList<>();

    private void initDatas2() {
        Fruit[] fruits = {new Fruit("Apple", R.drawable.apple), new Fruit("Banana",
                R.drawable.banana),
                new Fruit("Orange", R.drawable.orange), new Fruit("Watermelon", R.
                drawable.watermelon),
                new Fruit("Pear", R.drawable.pear), new Fruit("Grape", R.drawable.
                grape),
                new Fruit("Pineapple", R.drawable.pineapple), new Fruit("Strawberry",
                R.drawable.strawberry),
                new Fruit("Cherry", R.drawable.cherry), new Fruit("Mango", R.drawable.
                mango)};

        this. fruits.addAll(Arrays.asList(fruits));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_backup:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_delete:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_settings:
                Toast.makeText(this, item.getTitle(), Toast.LENGTH_SHORT).show();
                break;
//                HomeAsUp按钮的id是android.R.id.home
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
        }
        return false;
    }
}
