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

public class BuyActivity extends AppCompatActivity implements Serializable {

    TextInputLayout name,phone,address,pincode,landmark;
    TextView next;
    String namee,quantity,namearr="";
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

        namee = intent.getStringExtra(MainActivity.PRODUCT);
        quantity = intent.getStringExtra(MainActivity.PRODUCTQUANTITY);

            namearr += namee+":"+quantity+";";


        next.setOnClickListener(v -> {
            String n = name.getEditText().getText().toString().trim();
            String p = phone.getEditText().getText().toString().trim();
            String a = address.getEditText().getText().toString().trim();
            String c = pincode.getEditText().getText().toString().trim();
            String l = landmark.getEditText().getText().toString().trim();

            if(n.isEmpty()){
                name.setError("Field cannot be empty");
                name.requestFocus();
                return;
            }
            if(p.isEmpty()){
                phone.setError("Field cannot be empty");
                phone.requestFocus();
                return;

            }
            if(a.isEmpty()){
                address.setError("Field cannot be empty");
                address.requestFocus();
                return;

            }
            if(c.isEmpty()){
                pincode.setError("Field cannot be empty");
                pincode.requestFocus();
                return;
            }
            if(l.isEmpty()){
                landmark.setError("Field cannot be empty");
                landmark.requestFocus();
                return;
            }

            HashMap<String, Object> map = new HashMap<>();
            map.put("name", name.getEditText().getText().toString().trim());
            map.put("phone", phone.getEditText().getText().toString().trim());
            map.put("address", address.getEditText().getText().toString().trim());
            map.put("pincode", pincode.getEditText().getText().toString().trim());
            map.put("landmark", landmark.getEditText().getText().toString().trim());
            map.put("Products", namearr);


            FirebaseDatabase.getInstance().getReference().child("orders").child(name.getEditText().getText().toString().trim()).setValue(map)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(BuyActivity.this, "Success", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                    Toast.makeText(BuyActivity.this, "Fail", Toast.LENGTH_SHORT).show();
                }
            });

        });


    }
}