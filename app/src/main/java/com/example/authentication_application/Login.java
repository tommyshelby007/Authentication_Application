package com.example.authentication_application;

import android.content.Intent;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {
EditText email,password;
Button login_btn;
TextView signUp_btn;
ProgressBar progressBar;
FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=findViewById(R.id.email_login);
        password=findViewById(R.id.password_login);
        login_btn=findViewById(R.id.Login_btn);
        signUp_btn=findViewById(R.id.textView_signup);
        progressBar=findViewById(R.id.progressBar_login);
        firebaseAuth=FirebaseAuth.getInstance();
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_box=email.getText().toString().trim();
                String password_box=password.getText().toString().trim();
                if (TextUtils.isEmpty(email_box)){
                    email.setError("Email required");
                    return;
                }
                if (TextUtils.isEmpty(password_box)){
                    password.setError("password required");
                    return;
                }
                if (password_box.length()<6){
                    password.setError("your password is too short");
                    return;
                }
                progressBar.setVisibility(View.VISIBLE);
                //Authenticate the user
                firebaseAuth.signInWithEmailAndPassword(email_box,password_box).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Login.this,"user created",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            Toast.makeText(Login.this,"Error happned",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });
        signUp_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Registring.class));
            }
        });
    }
}