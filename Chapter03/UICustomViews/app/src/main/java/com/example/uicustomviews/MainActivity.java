package com.example.uicustomviews;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.uicustomviews.customview.DateSelectorLayout;

import java.util.Date;

public class MainActivity extends AppCompatActivity {

    DateSelectorLayout mDateSelectorLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 将系统自带的ActionBar隐藏
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

        findViewById(R.id.btn_show_popup_menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popupMenu = new PopupMenu(v.getContext(), v);
                popupMenu.getMenuInflater().inflate(R.menu.menu_head, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Toast.makeText(MainActivity.this, item.getTitle(), Toast.LENGTH_SHORT).show();
                        return false;
                    }
                });
                popupMenu.show();
            }
        });

        mDateSelectorLayout = findViewById(R.id.dateSelectorLayout);

        mDateSelectorLayout.setOnDateChangeListener(onDateChangeListener);
    }

    DateSelectorLayout.OnDateChangeListener onDateChangeListener = new DateSelectorLayout.OnDateChangeListener() {
        @Override
        public void action(Date oldDate, Date newDate) {
            Toast.makeText(MainActivity.this
                    , "old date " + oldDate + ", new date " + newDate
                    , Toast.LENGTH_SHORT).show();
        }
    };
}
