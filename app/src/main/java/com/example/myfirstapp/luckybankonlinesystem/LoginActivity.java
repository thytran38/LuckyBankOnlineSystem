package com.example.myfirstapp.luckybankonlinesystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.example.myfirstapp.luckybankonlinesystem.Model.CustomerModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.logging.Logger;

public class LoginActivity extends AppCompatActivity {

    EditText userName, password;
    Button login;
    TextView register, forgetPass;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName = (EditText) findViewById(R.id.etEmail);
        password = (EditText) findViewById(R.id.txtPassword);
        login = (Button) findViewById(R.id.btnSend);
        register = (TextView) findViewById(R.id.tvRegister);
        forgetPass = (TextView) findViewById(R.id.tvForgotPassword);


        login.setOnClickListener(v -> {
            try {
                Thread.sleep(2000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Intent intent = new Intent(this, SplashScreenActivity.class);
            startActivity(intent);
        });

        register.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        register.setOnClickListener(v -> {

            LoginEvent();

            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });

        forgetPass.setOnClickListener(v -> {
            Intent forgetPassIntent = new Intent(LoginActivity.this, ForgetPassActivity.class);
            startActivity(forgetPassIntent);
        });
    }

    private void LoginEvent() {

        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        if (firebaseUser.isEmailVerified()) {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(LoginActivity.this, "Email chua duoc xac nhan", Toast.LENGTH_LONG).show();
        }
    }


}