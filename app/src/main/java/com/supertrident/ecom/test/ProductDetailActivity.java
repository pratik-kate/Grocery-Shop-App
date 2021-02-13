package com.supertrident.ecom.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.supertrident.ecom.R;

public class ProductDetailActivity extends AppCompatActivity {

    String name,image,desc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Intent intent = getIntent();

        name = intent.getStringExtra(MainActivity.PID);
        image = intent.getStringExtra(MainActivity.PIMG);
        desc = intent.getStringExtra(MainActivity.PDEC);

        TextView text = findViewById(R.id.textView3);
        text.setText(name+image+desc);

    }
}