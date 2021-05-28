package com.pratikcodes.shopclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.pratikcodes.ecom.R;

public class SignUpActivity extends AppCompatActivity {

    TextInputLayout phoneEntered, passwordEntered,user;
    Button btn_sign_in, btn_sign_up;
    private FirebaseAuth firebaseAuth;


    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();

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

        if(!email.isEmpty() && !password.isEmpty()){
            progressBar = (ProgressBar) findViewById(R.id.progressBar2);
            // Start long running operation in a background thread
            new Thread(new Runnable() {
                public void run() {
                    while (progressStatus < 100) {
                        progressStatus += 1;
                        // Update the progress bar and display the
                        //current value in the text view
                        handler.post(new Runnable() {
                            public void run() {
                                progressBar.setVisibility(View.VISIBLE);
                                progressBar.setProgress(progressStatus);
                            }
                        });
                        try {
                            // Sleep for 200 milliseconds.
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }).start();
        }

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
                    finish();

                    startActivity(new Intent(SignUpActivity.this,MainActivity.class));
                } else {
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}