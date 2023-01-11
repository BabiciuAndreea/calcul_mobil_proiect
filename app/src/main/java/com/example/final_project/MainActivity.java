package com.example.final_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Button register;
    EditText username;
    EditText password;
    Button loginButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        register = (Button) findViewById(R.id.registerButton);
        register.setOnClickListener(this);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.registerButton:
                startActivity(new Intent(this, Register.class));
                break;
            case R.id.loginButton:
                userLogin();
                break;
        }
    }


    private void userLogin() {
        String email = username.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if(email.isEmpty()){
            username.setError("Username is required");
            username.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            username.setError("Please provide valid email!");
            username.requestFocus();
            return;

        }

        if(pass.isEmpty()){
            password.setError("Password is required");
            password.requestFocus();
            return;
        }

        if(password.length()<6){
            password.setError("Min password length should be 6 characters");
            password.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, Menu.class));
                    Toast.makeText(getApplicationContext(), "Welcome to menu page", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MainActivity.this, "Failed to login", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}