package com.example.authentication_application;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class MainActivity extends AppCompatActivity {
    TextView full_name,email;
    ImageView image;
    FirebaseAuth firebaseAuth;
    FirebaseFirestore firestore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       full_name=findViewById(R.id.full_name_profile);
       email=findViewById(R.id.email_profile);
       image=findViewById(R.id.Image_profile);
       firebaseAuth=FirebaseAuth.getInstance();
       firestore=FirebaseFirestore.getInstance();
       userId=firebaseAuth.getCurrentUser().getUid();
        DocumentReference documentReference=firestore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                full_name.setText(documentSnapshot.getString("name"));
                email.setText(documentSnapshot.getString("email"));
            }
        });
    }
    public void logout(View view){
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }
}