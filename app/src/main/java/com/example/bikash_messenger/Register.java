package com.example.bikash_messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bikash_messenger.Models.Users;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    FirebaseAuth firebaseAuth;
    TextView login,register;
    EditText Username,MobileNo,email,password;
    Boolean isValid;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        firebaseAuth=firebaseAuth.getInstance();
        declarations();
        seaction();
    }
    public void declarations(){
        login=findViewById(R.id.textView2);
        register=findViewById(R.id.butt);
        Username=findViewById(R.id.Name);
        MobileNo=findViewById(R.id.phone);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        database=FirebaseDatabase.getInstance();
        progressDialog= new ProgressDialog(Register.this);
        progressDialog.setTitle("Creating User Account");
        progressDialog.setMessage("We are getting things ready for you");


    }

    public void seaction() {
        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                progressDialog.show();
                firebaseAuth.createUserWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if (task.isSuccessful()){
                            Users user=new Users(Username.getText().toString(),MobileNo.getText().toString(),email.getText().toString(),password.getText().toString());
                            String id=task.getResult().getUser().getUid();
                            database.getReference().child("Users").child(id).setValue(user);

                            Toast.makeText(getApplicationContext(),"User Created Sucessfully",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i=new Intent(Register.this,MainActivity.class);
                startActivity(i);
            }
        });
    }

}