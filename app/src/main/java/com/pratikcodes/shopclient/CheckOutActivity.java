package com.pratikcodes.shopclient;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.pratikcodes.ecom.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

public class CheckOutActivity extends AppCompatActivity implements Serializable, PaymentResultListener {

    TextInputLayout name,phone,address,pincode,landmark;
    TextView next;
    ArrayList<String> namee = new ArrayList<>();
    ArrayList<String> price = new ArrayList<>();
    ArrayList<String> quantity = new ArrayList<>();
    String namearr="";
    int amount,round;
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
        price = (ArrayList<String>) intent.getSerializableExtra(MainActivity.PRODUCTPRICE);
        quantity = (ArrayList<String>) intent.getSerializableExtra(MainActivity.PRODUCTQUANTITY);

        for(int i=0;i<namee.size();i++){
            namearr += namee.get(i)+":"+quantity.get(i)+" ";
        }
        //Calculating total
        for(int j=0;j<price.size();j++){
            round += Integer.parseInt(price.get(j));
        }
        amount = Math.round(Float.parseFloat(String.valueOf(round))*100);

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

            Checkout checkout = new Checkout();
            checkout.setKeyID("rzp_test_GX2xUOm621SR7N");
            checkout.setImage(R.drawable.ic_shop);
            JSONObject object = new JSONObject();
            try {
                object.put("name","Grocery Shop");
                object.put("description","Make Payment For Your Order");
                object.put("theme.color","#FF6D00");
                object.put("currency","INR");
                object.put("amount",amount);
//                object.put("prefill.contact","8530899088");
//                object.put("prefill.email","shopclient@razorpay.com");

                checkout.open(CheckOutActivity.this,object);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        });

    }

    @Override
    public void onPaymentSuccess(String s) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name.getEditText().getText().toString().trim());
        map.put("phone", phone.getEditText().getText().toString().trim());
        map.put("address", address.getEditText().getText().toString().trim());
        map.put("pincode", pincode.getEditText().getText().toString().trim());
        map.put("landmark", landmark.getEditText().getText().toString().trim());
        map.put("products", namearr);
        map.put("paymentId",s);
        map.put("amount",amount/100);

        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.HASHMAP, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MainActivity.MAP, name.getEditText().getText().toString().trim());
        editor.apply();


        FirebaseDatabase.getInstance().getReference().child("orders").child(name.getEditText().getText().toString().trim()).setValue(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {

                        Toast.makeText(CheckOutActivity.this, "Order Placed", Toast.LENGTH_SHORT).show();
                        Intent home = new Intent(CheckOutActivity.this,MainActivity.class);
                        startActivity(home);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(CheckOutActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed "+s, Toast.LENGTH_SHORT).show();

        HashMap<String, Object> map = new HashMap<>();
        map.put("name", name.getEditText().getText().toString().trim());
        map.put("phone", phone.getEditText().getText().toString().trim());
        map.put("address", address.getEditText().getText().toString().trim());
        map.put("pincode", pincode.getEditText().getText().toString().trim());
        map.put("landmark", landmark.getEditText().getText().toString().trim());
        map.put("products", namearr);
        map.put("paymentId",s);
        map.put("amount",amount/100);

        String jsonString = new Gson().toJson(map);
        SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.HASHMAP, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(MainActivity.MAP, jsonString);
        editor.apply();

    }
}