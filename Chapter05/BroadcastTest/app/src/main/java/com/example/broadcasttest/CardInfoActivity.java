package com.example.broadcasttest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class CardInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_info);

        TextView textView = findViewById(R.id.textView);

        Intent intent = getIntent();
        String card_no = intent.getStringExtra("CARD_NO");
        textView.setText(card_no);
    }
}
