package com.example.gradlebuildersproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class Register extends AppCompatActivity {
    Button SignUp;
    ImageView i;
    TextInputEditText email,password,phone,name;
    FirebaseAuth mAuth;
    String emailPattern =  "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    Uri uri;

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        SignUp=findViewById(R.id.signup_button);
        i=findViewById(R.id.back);
        email=findViewById(R.id.email_edittext);
        password=findViewById(R.id.password_edittext);
        phone=findViewById(R.id.phone_edittext);
        name=findViewById(R.id.name_edittext);
        mAuth=FirebaseAuth.getInstance();

        progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Have Patience, your device is lazy!");
        progressDialog.setCancelable(false);

        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Register.this, MainActivity.class);
                startActivity(i);
            }
        });
        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                String eemail=email.getText().toString();
                String ename = name.getText().toString();
                String epassword = password.getText().toString();
                String ephone = phone.getText().toString();

                if(TextUtils.isEmpty(ename)||TextUtils.isEmpty(epassword)||TextUtils.isEmpty(ephone)||TextUtils.isEmpty(eemail)){
                    Toast.makeText(Register.this,"Incomplete Details", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else if(!eemail.matches(emailPattern)){
                    email.setError("Enter Valid Email ID");
                    Toast.makeText(Register.this,"Invalid Email ID", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else if(epassword.length()<8){
                    Toast.makeText(Register.this, "Password is Short", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else if(ephone.length()<10){
                    phone.setError("Check your Phone Number");
                    Toast.makeText(Register.this, "Invalid Phone Number", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }else{
                    mAuth.createUserWithEmailAndPassword(eemail,epassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                           if(task.isSuccessful()){
                               Toast.makeText(Register.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                               FirebaseUser user = mAuth.getCurrentUser();
                               startActivity(new Intent(Register.this,Home.class));
                               progressDialog.dismiss();
                           }else{
                               Toast.makeText(Register.this,"Oops, Registration Failed! Try Again",Toast.LENGTH_SHORT).show();
                           }

                        }
                    });
                }
            }
        });
    }
}