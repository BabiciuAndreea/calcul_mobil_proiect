package com.example.final_project;

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
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Pattern;

public class Register extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private TextView banner;
    private EditText user_reg, password_reg, email;
    private Button registerUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        banner = (TextView) findViewById(R.id.registerText);
        banner.setOnClickListener(this);

        registerUser = (Button) findViewById(R.id.registerBtn);
        registerUser.setOnClickListener(this);

        user_reg = (EditText) findViewById(R.id.user_reg);
        password_reg = (EditText) findViewById(R.id.password_reg);
        email = (EditText) findViewById(R.id.email);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.registerText:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registerBtn:
                registerUser();
                break;
        }
    }

    private void registerUser() {
        String password = password_reg.getText().toString().trim();
        String userName = user_reg.getText().toString().trim();
        String email_user = email.getText().toString().trim();


        if(password.isEmpty()){
            password_reg.setError("Password is required");
            password_reg.requestFocus();
            return;
        }

        if(password.length()<6){
            password_reg.setError("Min password length should be 6 characters");
            password_reg.requestFocus();
            return;
        }

        if(userName.isEmpty()){
            user_reg.setError("Username is required");
            user_reg.requestFocus();
            return;
        }

        if(email_user.isEmpty()){
            email.setError("Username is required");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email_user).matches()){
            email.setError("Please provide valid email!");
            email.requestFocus();
            return;

        }

        mAuth.createUserWithEmailAndPassword(email_user, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                             User user = new User(userName, email_user);

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful()){
                                                Toast.makeText(Register.this, "User has been registered successfully", Toast.LENGTH_LONG).show();
                                            } else {
                                                Toast.makeText(Register.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                                            }
                                        }
                                    });

                            startActivity(new Intent(Register.this, Menu.class));

                        } else{
                            Toast.makeText(Register.this, "Failed to register! Try again!", Toast.LENGTH_LONG).show();
                        }
                    }
                });



    }
}