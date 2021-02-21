package com.supertrident.ecom.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.supertrident.ecom.R;

import java.util.HashSet;
import java.util.Set;

public class ProductDetailActivity extends AppCompatActivity {

    String name,image,desc,price;
    TextView pname,pprice,pdesc,pbuy,pcart;
    ImageView pimage,back;
    TextView quantity,add,remove;
    public int count=1;

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

        //quantity
        quantity = findViewById(R.id.quantity);
        add = findViewById(R.id.plus);
        remove = findViewById(R.id.minus);

        add.setOnClickListener(v -> {
            count++;
            quantity.setText(String.valueOf(count));
        });
        remove.setOnClickListener(v -> {
            if(count>0) {
                count--;
                quantity.setText(String.valueOf(count));
            }
        });


        ConstraintLayout layout = findViewById(R.id.layout);
        pname.setText(name);
        pprice.setText(price);
        pdesc.setText(desc);
        Glide.with(this)
                .load(image)
                .override(layout.getWidth(),layout.getHeight())
                .into(pimage);

        back.setOnClickListener(v -> {super.onBackPressed();});

        pcart.setOnClickListener(v -> {

            MainActivity.CARTCOUNTER++;
            String p = MainActivity.PRODUCT+ MainActivity.CARTCOUNTER;
            String i = MainActivity.PRODUCTIMAGE+MainActivity.CARTCOUNTER;
            String pr = MainActivity.PRODUCTPRICE+MainActivity.CARTCOUNTER;
            String q = MainActivity.PRODUCTQUANTITY+MainActivity.CARTCOUNTER;
            String coun = String.valueOf(count);

            SharedPreferences.Editor editor = getSharedPreferences(MainActivity.CART,MODE_PRIVATE).edit();
            editor.putString(p,name);
            editor.putString(i,image);
            editor.putString(pr,price);
            editor.putString(q,coun);
            editor.apply();
            editor.commit();
            Toast.makeText(this, "Added "+p, Toast.LENGTH_SHORT).show();

        });

        pbuy.setOnClickListener(v -> {
            Intent intent1 = new Intent(ProductDetailActivity.this,BuyActivity.class);
            intent1.putExtra(MainActivity.PRODUCT,name);
            intent1.putExtra(MainActivity.PRODUCTQUANTITY,String.valueOf(count));
            startActivity(intent1);
        });

    }
}