package com.example.covax.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covax.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private EditText userLoginEmail, userLoginPassword;
    private Button loginButton;
    private TextView clickToRegister;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userLoginEmail = findViewById(R.id.userLoginEmail);
        userLoginPassword = findViewById(R.id.userLoginPassword);
        loginButton = findViewById(R.id.loginButton);
        clickToRegister = findViewById(R.id.clickToRegister);

        mAuth = FirebaseAuth.getInstance();

        //user wants to register an account
        clickToRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registrationActivity = new Intent (getApplicationContext(), Registration.class);
                startActivity(registrationActivity);
                finish();
            }
        });

        //user wants to log into their account
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = userLoginEmail.getText().toString();
                final String password = userLoginPassword.getText().toString();

                //login requirements
                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                }
                else {
                    signIn(email, password);
                }
            }
        });
    }

    //sign into the user's account through firebase authentication
    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    updateUI();
                }
                else {
                    Toast.makeText(getApplicationContext(), "LOGIN FAILED!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //updates user interface to home page
    private void updateUI() {
        Intent homeActivity = new Intent(getApplicationContext(), Home.class);
        startActivity(homeActivity);
        finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = mAuth.getCurrentUser();

        //keep user logged in if they hav not signed out
        if (user != null) {
            updateUI();
        }
    }
}

