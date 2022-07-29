package com.example.bikash_messenger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthProvider;

public class MainActivity extends AppCompatActivity {
    EditText email,password;
    Button login,register;
    FirebaseAuth firebaseAuth;
    Boolean isValid;
    String VerificationID;

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private FirebaseAuth.AuthStateListener authStateListener;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);
        firebaseAuth=FirebaseAuth.getInstance();
        beginning();
        actions();
    }

    public void beginning(){
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        login=findViewById(R.id.button);
        register=findViewById(R.id.button2);

        progressDialog= new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Log In");
        progressDialog.setMessage("Logging in to your Account");

        authStateListener= new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser firebaseUser=firebaseAuth.getCurrentUser();
                if(firebaseUser!=null){
                    Toast.makeText(MainActivity.this,"You are Logged In",Toast.LENGTH_SHORT).show();
                    Intent i=new Intent(MainActivity.this,Home.class);
                    startActivity(i);

                }
            }
        };
    }

    public void actions(){
        login.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                progressDialog.show();
                firebaseAuth.signInWithEmailAndPassword(email.getText().toString(),password.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressDialog.dismiss();
                        if(task.isSuccessful()){
                            Intent i=new Intent(MainActivity.this,Home.class);
                            startActivity(i);
                        }else{
                            Toast.makeText(MainActivity.this,task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        register.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i=new Intent(MainActivity.this,Register.class);
                startActivity(i);
            }
        });


    }


}