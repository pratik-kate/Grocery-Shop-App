package com.supertrident.ecom.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.supertrident.ecom.R;

public class ShopActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        TextView text = findViewById(R.id.idd);
        Intent intent = getIntent();
        int a = intent.getIntExtra(MainActivity.CATID,0);
        text.setText(String.valueOf(a));
    }
}