package com.example.gradlebuildersproject;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Login extends AppCompatActivity {
    ImageView i;
    TextInputEditText email,password;
    Button login;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        i=findViewById(R.id.back);
        login = findViewById(R.id.login_button);
        email = findViewById(R.id.email_et);
        password = findViewById(R.id.password_et);
        mAuth = FirebaseAuth.getInstance();
        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Login.this, MainActivity.class);
                startActivity(i);
            }
        });
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = FirebaseAuth.getInstance().getCurrentUser();
                if(mFirebaseUser != null){
                    Toast.makeText(Login.this,"Logged In Successfully", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Login.this, Home.class));
                }
                else{
                    Toast.makeText(Login.this, "Please Login to Continue !",Toast.LENGTH_SHORT).show();
                }
            }
        };
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String eemail = email.getText().toString();
                String epassword = password.getText().toString();
                if(TextUtils.isEmpty(eemail)||TextUtils.isEmpty(epassword)){
                    Toast.makeText(Login.this,"Fill all the fields",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(eemail)){
                    email.setError("Please enter Email to continue");
                    Toast.makeText(Login.this,"Email ID is not there",Toast.LENGTH_SHORT).show();
                }
                else if(TextUtils.isEmpty(epassword)){
                    password.setError("Enter Password");
                    Toast.makeText(Login.this,"Incomplete Password",Toast.LENGTH_SHORT).show();
                }
                else{
                    mAuth.signInWithEmailAndPassword(eemail,epassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Login.this, "Logged in successfully !", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(Login.this, Home.class));
                            } else {
                                Toast.makeText(Login.this, "Login error try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
}
