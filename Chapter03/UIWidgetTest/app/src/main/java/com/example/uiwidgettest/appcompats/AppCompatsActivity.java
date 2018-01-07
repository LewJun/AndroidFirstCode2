package com.example.uiwidgettest.appcompats;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.uiwidgettest.R;

public class AppCompatsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_app_compats);
    }

    public void openRecyclerViewActivity(View view) {
        Intent intent = new Intent(this, RecyclerViewActivity.class);
        startActivity(intent);
    }
}
