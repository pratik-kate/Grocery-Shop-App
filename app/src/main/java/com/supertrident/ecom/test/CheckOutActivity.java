package com.supertrident.ecom.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;
import com.supertrident.ecom.R;
import com.supertrident.ecom.test.models.CartModel;
import com.supertrident.ecom.test.models.FinalModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class CheckOutActivity extends AppCompatActivity implements Serializable {

    TextInputLayout name,phone,address,pincode,landmark;
    TextView next;
    ArrayList<String> namee = new ArrayList<>();
    ArrayList<String> price = new ArrayList<>();
    ArrayList<String> quantity = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
        getSupportActionBar().hide();

        name = findViewById(R.id.c_name);
        phone = findViewById(R.id.c_mobile);
        address = findViewById(R.id.c_address);
        pincode = findViewById(R.id.c_pincode);
        landmark = findViewById(R.id.c_landmark);
        next = findViewById(R.id.next);

        Intent intent = getIntent();
        namee = (ArrayList<String>) intent.getSerializableExtra(MainActivity.PRODUCT);
        // price = (ArrayList<String>) intent.getSerializableExtra(MainActivity.PRODUCTPRICE);
        quantity = (ArrayList<String>) intent.getSerializableExtra(MainActivity.PRODUCTQUANTITY);

        next.setOnClickListener(v -> {
            for (int i = 0; i < namee.size(); i++) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("name", namee.get(i));
                map.put("quantity", quantity.get(i));

                FirebaseDatabase.getInstance().getReference().child("orders").child(String.valueOf(i)).child("product").setValue(map)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                Toast.makeText(CheckOutActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        Toast.makeText(CheckOutActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}