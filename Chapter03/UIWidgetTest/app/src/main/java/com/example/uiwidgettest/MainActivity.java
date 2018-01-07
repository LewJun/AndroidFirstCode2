package com.example.uiwidgettest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.uiwidgettest.advanced.AdvancedActivity;
import com.example.uiwidgettest.appcompats.AppCompatsActivity;
import com.example.uiwidgettest.containers.ContainersActivity;
import com.example.uiwidgettest.date.DateActivity;
import com.example.uiwidgettest.design.DesignActivity;
import com.example.uiwidgettest.google.GoogleActivity;
import com.example.uiwidgettest.images.ImagesActivity;
import com.example.uiwidgettest.layouts.LayoutsActivity;
import com.example.uiwidgettest.text.TextActivity;
import com.example.uiwidgettest.transitions.TransitionsActivity;
import com.example.uiwidgettest.widgets.WidgetsActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void openWidgetsActivity(View v) {
        Intent intent = new Intent(this, WidgetsActivity.class);
        startActivity(intent);
    }

    public void openTextActivity(View v) {
        Intent intent = new Intent(this, TextActivity.class);
        startActivity(intent);
    }

    public void openLayoutsActivity(View v) {
        Intent intent = new Intent(this, LayoutsActivity.class);
        startActivity(intent);
    }

    public void openContainersActivity(View v) {
        Intent intent = new Intent(this, ContainersActivity.class);
        startActivity(intent);
    }

    public void openImagesActivity(View v) {
        Intent intent = new Intent(this, ImagesActivity.class);
        startActivity(intent);
    }

    public void openDateActivity(View v) {
        Intent intent = new Intent(this, DateActivity.class);
        startActivity(intent);
    }

    public void openTransitionsActivity(View v) {
        Intent intent = new Intent(this, TransitionsActivity.class);
        startActivity(intent);
    }

    public void openAdvancedActivity(View v) {
        Intent intent = new Intent(this, AdvancedActivity.class);
        startActivity(intent);
    }

    public void openGoogleActivity(View v) {
        Intent intent = new Intent(this, GoogleActivity.class);
        startActivity(intent);
    }

    public void openDesignActivity(View v) {
        Intent intent = new Intent(this, DesignActivity.class);
        startActivity(intent);
    }

    public void openAppCompatsActivity(View v) {
        Intent intent = new Intent(this, AppCompatsActivity.class);
        startActivity(intent);
    }
}
