package com.example.unibuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class SigninActivity extends AppCompatActivity {

    private EditText mEmail, mPass, mId;
    private TextView mText;
    private Button signInBtn;

    private FirebaseAuth mAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    Boolean var1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        mEmail = findViewById(R.id.sign_email);
        mPass = findViewById(R.id.sign_pass);
        mText = findViewById(R.id.already2);
        mId = findViewById(R.id.signin_id);
        signInBtn = findViewById(R.id.signin_loging_btn);
        mAuth = FirebaseAuth.getInstance();

        mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(SigninActivity.this, MainActivity.class));
            }
        });
        signInBtn.setOnClickListener((view -> {
            login();
        }));
    }

    private void login() {
        String email = mEmail.getText().toString().trim();
        String pass = mPass.getText().toString().trim();
        String id = mId.getText().toString().trim();
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
        Query checkUser = reference.orderByChild("reg_stud_id").equalTo(id);

        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    mEmail.setError(null);
                    mEmail.setEnabled(false);
                    String passwordFromDB = snapshot.child(id).child("reg_password").getValue(String.class);
                    if (passwordFromDB.equals(pass)) {
                        mEmail.setError(null);
                        mEmail.setEnabled(false);
                        startActivity(new Intent(SigninActivity.this, homeActivity.class));
                        finish();

                    } else {
                        mPass.setError("Wrong Password.");
                        mPass.requestFocus();
                    }
                } else {
                    mEmail.setError("No such user exist.");
                    System.out.println("print");
                    mEmail.requestFocus();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        if (!validatePassword(email) || validateUsername(pass)) {
            return;
        }


    }

    private boolean validateUsername(String s1) {

        boolean v1 = false;
        if (s1.isEmpty()) {
            mEmail.setError("Field cannot be empty");
        } else if (s1.length() >= 30) {
            mEmail.setError("Username too long");
        } else if (s1.contains("gmail.com") || s1.contains("yahoo.com")) {
            mEmail.setError("Enter your edu mail");
        } else {
            mEmail.setError(null);
            mEmail.setEnabled(false);
            v1 = true;
        }
        return v1;

    }

    private boolean validatePassword(String s1) {

        boolean v2 = false;
        if (s1.isEmpty()) {
            mPass.setError("Field cannot be empty");
        } else {
            mPass.setError(null);
            mPass.setEnabled(false);
            v2 = true;
        }
        return v2;

    }
}
