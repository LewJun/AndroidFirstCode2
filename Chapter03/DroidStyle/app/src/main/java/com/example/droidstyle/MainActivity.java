package com.example.droidstyle;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void toShape(View view) {
        startActivity(new Intent(this, ShapeActivity.class));
    }

    public void toSelector(View view) {
        startActivity(new Intent(this, SelectorActivity.class));
    }

    public void toLayerList(View view) {
        startActivity(new Intent(this, LayerListActivity.class));
    }

    public void toAnimation(View view) {
        startActivity(new Intent(this, AnimationActivity.class));
    }
}
