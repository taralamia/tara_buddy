package com.example.unibuddy;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private EditText mEmail,mPass,mId;
    private TextView mText,notVerify;
    private Button signUpBtn,verifyBtn;
    private FirebaseAuth firebaseAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    Boolean var1;
    public static final String TAG = "Email has not sent.";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEmail = findViewById(R.id.reg_email);
        mPass = findViewById(R.id.reg_pass);
        mText = findViewById(R.id.already);
        signUpBtn = findViewById(R.id.reg_loging_btn);
        mId = findViewById(R.id.signup_id);
        firebaseAuth = FirebaseAuth.getInstance();
       // FirebaseUser user = firebaseAuth.getCurrentUser();

        notVerify = findViewById(R.id.notVerify);
        verifyBtn = findViewById(R.id.verifyBtn);

        mText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this , SigninActivity.class));
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String email = mEmail.getText().toString();
                String pass = mPass.getText().toString();
                String id = mId.getText().toString();

                createUser(email,pass,id);

                if(var1 == true) {
                   /* firebaseAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                Toast.makeText(MainActivity.this,"Registered Successfully!Please check your email.",Toast.LENGTH_LONG).show();

                            }
                        }
                    });

                  firebaseAuth.getCurrentUser().sendEmailVerification()
                          .addOnCompleteListener(new OnCompleteListener<Void>() {
                              @Override
                              public void onComplete(@NonNull Task<Void> task) {

                                  if(task.isSuccessful())
                                  {
                                      System.out.println("Sign in failed"+task.getException().getMessage());
                                      Toast.makeText(MainActivity.this,"Registered Successfully!Please check your email.",Toast.LENGTH_LONG).show();
                                      Log.d(TAG,"Hehe");
                                  }
                                  else
                                  {
                                      Toast.makeText(MainActivity.this,task.getException().getMessage(),Toast.LENGTH_LONG).show();
                                  }
                              }
                          });*/

                    rootNode = FirebaseDatabase.getInstance();
                   // reference = rootNode.getReference("User");
                    reference = rootNode.getReference().child("User");
                    helperClass obj1 = new helperClass(email,pass,id);
                    reference.child(id).setValue(obj1);
                    startActivity(new Intent(MainActivity.this,SigninActivity.class));
                    finish();

                }
                else if( var1 == false)
                {
                    Toast.makeText(MainActivity.this, "Registration Error !!", Toast.LENGTH_SHORT).show();
                    mEmail.setError(null);
                    mPass.setError(null);
                    mId.setError(null);
                    mEmail.setEnabled(false);
                    mPass.setEnabled(false);
                    mId.setEnabled(false);


                }


            }
        });

    }
    private  void createUser(String s1,String s2,String s3)
    {
        if(!s1.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(s1).matches() && s1.contains("aust.edu")) {

            if (!s2.isEmpty()) {

                var1 = true;
            }
            else{
                var1 = false;
            }
        }
        else if(s1.isEmpty())
        {
            var1 = false;

        }
        else if(s1.contains("@gmail.com") || s1.contains("@yahoo.com"))
        {
            // mEmail.setError("Please Give your edu mail !!");
            System.out.println("Edu mail Edu mail");
            var1 = false;
        }
        else if(s1.length()>=15)
        {
            mEmail.setError("Username too long");
        }


    }




}