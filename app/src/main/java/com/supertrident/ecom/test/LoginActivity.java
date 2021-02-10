package com.supertrident.ecom.test;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.supertrident.ecom.R;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "tag";
    TextInputLayout phoneEntered, passwordEntered;
    Button btn_sign_in, btn_sign_up;
    TextView btn_forget_password;
    private FirebaseAuth firebaseAuth;

    private ProgressBar progressBar;
    private int progressStatus = 0;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();


        btn_sign_in = findViewById(R.id.sign_in);
        //btn_forget_password = findViewById(R.id.forgot_password);
        btn_sign_up = findViewById(R.id.sign_up_btn);
        phoneEntered = findViewById(R.id.logIn_email);
        passwordEntered = findViewById(R.id.logIn_password);

        btn_sign_up.setOnClickListener(v -> {
            startActivity(new Intent(this,SignUpActivity.class));
        });

        firebaseAuth = FirebaseAuth.getInstance();



        btn_sign_in.setOnClickListener(v -> {

            final String email = phoneEntered.getEditText().getText().toString().trim();
            String password = passwordEntered.getEditText().getText().toString().trim();


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

            String username = email.substring(0,email.indexOf("@"));
            if(!email.isEmpty() && !password.isEmpty()){
                progressBar = (ProgressBar) findViewById(R.id.progressBar);
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

            firebaseAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                SharedPreferences preferences = getSharedPreferences(MainActivity.INFO,MODE_PRIVATE);
                                SharedPreferences.Editor editor = preferences.edit();
                                editor.putString(MainActivity.USER,username);
                                editor.putString(MainActivity.EMAIL,email);
                                editor.apply();
                                editor.commit();
                                finish();

                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            } else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(LoginActivity.this, "Please Check Email Or Password", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        });
    }
}