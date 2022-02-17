package com.example.loginpage;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Register extends AppCompatActivity {
    EditText Full_name, Phone, Mail, Password,Con_pas;
    Button m_register;
    TextView sign_in;
    ProgressBar progress;
    FirebaseAuth FAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Full_name = findViewById(R.id.Fname);
        m_register = findViewById(R.id.joinm);
        Phone = findViewById(R.id.dial);
        Mail = findViewById(R.id.Mmail);
        Password = findViewById(R.id.mPassword);
        Con_pas = findViewById(R.id.passcon);
        sign_in = findViewById(R.id.login);



        FAuth = FirebaseAuth.getInstance();
        progress = findViewById(R.id.progressBar);

        if (FAuth.getCurrentUser() !=null){
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        m_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = Mail.getText().toString().trim();
                String passcode = Password.getText().toString().trim();

                if (TextUtils.isEmpty(email)){
                    Mail.setError("Email Missing!");
                    return;
                }
                if (TextUtils.isEmpty(passcode)){
                    Password.setError("Password Missing!");
                    return;
                }
                if (passcode.length() < 8){
                    Password.setError("Password Should be a minimum of 8 characters");
                    return;
                }
                progress.setVisibility(View.VISIBLE);
//                registering the user on the firebase database
                FAuth.createUserWithEmailAndPassword(email, passcode).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(Register.this, "user created successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        }else{
                            Toast.makeText(Register.this,"Error !"+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
}