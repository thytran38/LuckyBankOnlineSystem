package com.example.myfirstapp.luckybankonlinesystem;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.luckybankonlinesystem.R;

public class LoginActivity extends AppCompatActivity {

    EditText userName, password, fullName, dateOfBirth, phoneNumber, email, inputPassword, re_enterPassword, currentAmount, address, nationalID;
    Button login, register_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = (EditText)findViewById(R.id.txtUserName);
        password = (EditText)findViewById(R.id.txtPassword);
        login = (Button)findViewById(R.id.btnLogin);
        register_login = (Button)findViewById(R.id.btnRegister_Login);

    }
}