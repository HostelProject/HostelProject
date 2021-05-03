package com.example.hostelproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.AlphabetIndexer;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class loginActivity extends AppCompatActivity {

    private EditText loginEmail, loginPassword;
    private TextView toSignUp;
    private Button loginButton;
    private FirebaseAuth mAuth;
    private String email5,password5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginEmail =findViewById(R.id.loginemail);
        loginPassword =findViewById(R.id.loginpassword);
        loginButton =findViewById(R.id.loginbutton);
        toSignUp =findViewById(R.id.tosignup);

        toSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignup();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        mAuth = FirebaseAuth.getInstance();
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginUser();
            }
        });
    }

    private void loginUser() {

        email5 = loginEmail.getText().toString().trim();
        password5 = loginPassword.getText().toString().trim();

        if(email5.isEmpty()){
            loginEmail.setError("Please enter an Email.");
            loginEmail.requestFocus();
            return;
        }

        if(password5.isEmpty()){
            loginPassword.setError("Please enter a Password");
            loginPassword.requestFocus();
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email5).matches()){
            loginEmail.setError("Please enter a valid Email address.");
            loginEmail.requestFocus();
            return;
        }


        mAuth.signInWithEmailAndPassword(email5,password5).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(loginActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    gohome();
                }
                else {
                    Toast.makeText(loginActivity.this, "Login Failed.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void gohome() {
        startActivity(new Intent(loginActivity.this, HomeActivity.class));
    }

    private void openSignup() {
        startActivity(new Intent(loginActivity.this, signUpActivity.class));
    }

    /*
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Really Exit?")
                .setMessage("Are you sure you want to exit?")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new View.OnClickListener(){
                    @Override
                            public void onClick(View v) {
                                loginActivity.super.onBackPressed();
                            }
                        }).create().show();



        //super.onBackPressed();
    }

     */
}