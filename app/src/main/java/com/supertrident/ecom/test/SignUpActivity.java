package com.supertrident.ecom.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.supertrident.ecom.R;

public class SignUpActivity extends AppCompatActivity {

    TextInputLayout phoneEntered, passwordEntered,user;
    Button btn_sign_in, btn_sign_up;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        getSupportActionBar().hide();

        phoneEntered = findViewById(R.id.Sigup_email);
        passwordEntered  = findViewById(R.id.Sigup_password);
        user = findViewById(R.id.Sigup_user);
        btn_sign_in = findViewById(R.id.sign_in_btn);
        btn_sign_up = findViewById(R.id.sign_up);

        btn_sign_in.setOnClickListener(v -> {
            startActivity(new Intent(this,LoginActivity.class));
        });

        firebaseAuth = FirebaseAuth.getInstance();
        btn_sign_up.setOnClickListener(v -> {
            registerUser();
        });
    }

    private void registerUser() {
        final String email = phoneEntered.getEditText().getText().toString().trim();
        String password = passwordEntered.getEditText().getText().toString().trim();
        String username = user.getEditText().getText().toString().trim();


        if(email.isEmpty()){
            phoneEntered.setError("Field cannot be empty");
            phoneEntered.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            phoneEntered.setError("Enter a valid email");
            phoneEntered.requestFocus();
            return;
        }

        if(password.isEmpty()){
            passwordEntered.setError("Field cannot be empty");
            passwordEntered.requestFocus();
            return;
        }
        if(username.isEmpty()){
            user.setError("Field cannot be empty");
            user.requestFocus();
            return;
        }

//        if(confirmPassword.isEmpty()){
//            passwordEntered.setError("Field cannot be empty");
//            passwordEntered.requestFocus();
//            return;
//        }
//
//        if(!confirmPassword.equals(password)){
//            confirmPasswordEntered.setError("Password doesn't match");
//            confirmPasswordEntered.requestFocus();
//            return;
//        }

        firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    SharedPreferences preferences = getSharedPreferences(MainActivity.INFO,MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString(MainActivity.USER,username);
                    editor.putString(MainActivity.EMAIL,email);
                    editor.apply();
                    editor.commit();
                    startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}