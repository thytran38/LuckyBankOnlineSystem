package com.example.myfirstapp.luckybankonlinesystem;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.logging.Logger;

public class LoginActivity extends AppCompatActivity {

    EditText userName, password;
    Button login;
    TextView register, forgetPass;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName = (EditText)findViewById(R.id.etEmail);
        password = (EditText)findViewById(R.id.etPassword);
        login = (Button)findViewById(R.id.btnSend);
        register = (TextView)findViewById(R.id.tvRegister);
        forgetPass = (TextView)findViewById(R.id.tvForgotPassword);

        // Connect to Firebase
        firebaseAuth = FirebaseAuth.getInstance();

        login.setOnClickListener(v -> {
            LoginEvent();
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
            }

        });

        forgetPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forgetPassIntent = new Intent(LoginActivity.this,ForgetPassActivity.class);
                startActivity(forgetPassIntent);
                finish();
            }
        });
    }

    private void LoginEvent() {
        if (!(UserNameValidCheck() && PassWordValidCheck())){
            Toast.makeText(this, "All fields must be correct!", Toast.LENGTH_LONG).show();
            return;
        }

        String checkusername = userName.getText().toString();
        String checkpassword = password.getText().toString();

        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        firebaseAuth.signInWithEmailAndPassword(checkusername,checkpassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Logger.getLogger("DEBUG").warning("Login successed");
                    Toast.makeText(LoginActivity.this,"logged in successfully",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this,ProfileActivity.class));
                }else {
                    Logger.getLogger("DEBUG").warning(task.getException().getMessage());
                }
            }
        });
    }

    private boolean UserNameValidCheck(){
        String checkusername = userName.getText().toString();
        if (checkusername.isEmpty()){
            userName.setError("UserName Can't Be Empty");
            return false;
        }
        else {
            userName.setError(null);
            return true;
        }
    }
    private boolean PassWordValidCheck(){
        String checkpassword = password.getText().toString();
        if (checkpassword.isEmpty()) {
            password.setError("PassWord can't Be Empty. Please input your password");
            return false;
        }
        else if (checkpassword.length() < 6) {
            password.setError("Password must be >= 6 characters! Please input your password again");
            return false;
        }
        password.setError(null);
        return true;
    }
}