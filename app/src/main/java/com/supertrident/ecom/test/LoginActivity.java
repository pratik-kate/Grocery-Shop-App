package com.supertrident.ecom.test;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputLayout;
import com.supertrident.ecom.R;

public class LoginActivity extends AppCompatActivity {

    TextInputLayout phoneEntered, passwordEntered;
    Button btn_sign_in, btn_sign_up;
    TextView btn_forget_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();


        btn_sign_in = findViewById(R.id.sign_in);
        btn_forget_password = findViewById(R.id.forgot_password);
        btn_sign_up = findViewById(R.id.sign_up_btn);
        phoneEntered = findViewById(R.id.logIn_email);
        passwordEntered = findViewById(R.id.logIn_password);

        btn_sign_up.setOnClickListener(v -> {
            startActivity(new Intent(this,SignUpActivity.class));
        });




        btn_sign_in.setOnClickListener(v -> {
            startActivity(new Intent(this,MainActivity.class));
        });
    }
}