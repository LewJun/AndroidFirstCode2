package com.example.uiwidgettest.appcompats;

import android.app.ActionBar;
import android.content.Intent;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.uiwidgettest.R;

public class FruitDetailActivity extends AppCompatActivity {

    private FloatingActionButton mFab;

    private TextView mFruitContentText;

    private ImageView mImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fruit_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent intent = getIntent();
        String fruit_name = intent.getStringExtra("fruit_name");
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(fruit_name);

        int fruit_image_id = intent.getIntExtra("fruit_image_id", 0);
        mImageView = findViewById(R.id.iv_fruit_pic);
        Glide.with(this).load(fruit_image_id).into(mImageView);

        mFab = findViewById(R.id.fab);
        mFab.setOnClickListener((view) -> {
            Snackbar.make(view, "comment", Snackbar.LENGTH_SHORT).show();
        });

        mFruitContentText = findViewById(R.id.fruit_content_text);
        mFruitContentText.setText(generateFruitName(fruit_name));
    }

    private String generateFruitName(String fruitName) {
        StringBuilder sb = new StringBuilder("");
        for (int i = 0; i < 500; i++) {
            sb.append(fruitName);
        }
        return sb.toString();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
