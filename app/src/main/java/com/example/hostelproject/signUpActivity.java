package com.example.hostelproject;

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
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class signUpActivity extends AppCompatActivity {

    private EditText signupEmail, signupPassword;
    private Button signupButton;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();
        signupEmail = findViewById(R.id.signupemail);
        signupPassword = findViewById(R.id.signuppassword);
        signupButton = findViewById(R.id.signupbutton);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    private void register() {
        String username,password;

        username=signupEmail.getText().toString().trim();
        password=signupPassword.getText().toString().trim();

        if(username.isEmpty()){
            signupEmail.setError("Please enter an Email.");
            signupEmail.requestFocus();
            return;
        }

        if(password.isEmpty()){
            signupPassword.setError("Please enter a Password");
            signupPassword.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(username).matches()){
            signupEmail.setError("Please enter a valid Email address.");
            signupEmail.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(username,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(signUpActivity.this, "The user has successfully been registered.", Toast.LENGTH_SHORT).show();
                    tohome();
                }
                else {
                    Toast.makeText(signUpActivity.this, "The user is already registered, or please enter valid email/password,", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private void tohome() {
        startActivity(new Intent(signUpActivity.this, HomeActivity.class));
    }
}