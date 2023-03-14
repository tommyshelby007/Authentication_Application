package com.example.authentication_application;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Registring extends AppCompatActivity {
EditText full_name,email,password;
Button register_btn;
TextView login_btn;
ProgressBar progressBar;
FirebaseAuth firebaseAuth;
FirebaseFirestore firestore;
String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registring);
        full_name=findViewById(R.id.full_name);
        email=findViewById(R.id.email_register);
        password=findViewById(R.id.password_register);
        register_btn=findViewById(R.id.register_btn);
        login_btn=findViewById(R.id.login_text);
        progressBar=findViewById(R.id.progressBar_register);
        firebaseAuth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();
        if (firebaseAuth.getCurrentUser()!=null){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_box=email.getText().toString().trim();
                String password_box=password.getText().toString().trim();
                String user_name=full_name.getText().toString();


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
                //register the user in firebase
                firebaseAuth.createUserWithEmailAndPassword(email_box,password_box).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Registring.this,"user created",Toast.LENGTH_SHORT).show();
                            userId=firebaseAuth.getCurrentUser().getUid();
                            DocumentReference documentReference=firestore.collection("users").document(userId);
                            Map<String ,Object> user=new HashMap<>();
                            user.put("name",user_name);
                            user.put("email",email_box);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Log.d("Tag","onSuccess:user profile created"+userId);
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                   Log.d("Tag","OnFailure:"+e.toString());
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else {
                            Toast.makeText(Registring.this,"Error happned",Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                    }
                }
            });

        }
    });
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
            }
        });
}
}