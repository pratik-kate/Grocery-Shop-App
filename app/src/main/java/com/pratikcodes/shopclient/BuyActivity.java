package com.pratikcodes.shopclient;

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
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import com.pratikcodes.ecom.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;

public class BuyActivity extends AppCompatActivity implements Serializable, PaymentResultListener {

    TextInputLayout name,phone,address,pincode,landmark;
    TextView next;
    int amt;
    String namee,quantity,amount,namearr="";
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
        amount = intent.getStringExtra(MainActivity.PPRICE);
        amt = Math.round(Float.parseFloat(amount)*100);



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

            Checkout checkout = new Checkout();
            checkout.setKeyID("rzp_test_GX2xUOm621SR7N");
            checkout.setImage(R.drawable.ic_shop);
            JSONObject object = new JSONObject();
            try {
                object.put("name","Grocery Shop");
                object.put("description","Make Payment For Your Order");
                object.put("theme.color","#FF6D00");
                object.put("currency","INR");
                object.put("amount",amt);
//                object.put("prefill.contact","8530899088");
//                object.put("prefill.email","shopclient@razorpay.com");

                checkout.open(BuyActivity.this,object);

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
        map.put("amount",amt/100);


        FirebaseDatabase.getInstance().getReference().child("orders").child(name.getEditText().getText().toString().trim()).setValue(map)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Intent home = new Intent(BuyActivity.this,MainActivity.class);
                        startActivity(home);
                        Toast.makeText(BuyActivity.this, "Order Placed", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(BuyActivity.this, "Please Try Again", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Failed "+s, Toast.LENGTH_SHORT).show();

    }
}