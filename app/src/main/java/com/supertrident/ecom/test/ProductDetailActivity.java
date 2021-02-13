package com.supertrident.ecom.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.supertrident.ecom.R;

public class ProductDetailActivity extends AppCompatActivity {

    String name,image,desc,price;
    TextView pname,pprice,pdesc,pbuy,pcart;
    ImageView pimage,back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);

        Intent intent = getIntent();

        name = intent.getStringExtra(MainActivity.PID);
        image = intent.getStringExtra(MainActivity.PIMG);
        desc = intent.getStringExtra(MainActivity.PDEC);
        price = intent.getStringExtra(MainActivity.PPRICE);
       getSupportActionBar().hide();

       //
        pname = findViewById(R.id.pname);
        pimage = findViewById(R.id.pimg);
        pprice = findViewById(R.id.pprice);
        pdesc = findViewById(R.id.pdesc);
        pbuy = findViewById(R.id.pbuy);
        pcart = findViewById(R.id.pcart);
        back = findViewById(R.id.pback);

        pname.setText(name);
        pprice.setText(price);
        pdesc.setText(desc);
        Glide.with(this)
                .load(image)
                .override(500,500)
                .into(pimage);

        back.setOnClickListener(v -> {super.onBackPressed();});

    }
}