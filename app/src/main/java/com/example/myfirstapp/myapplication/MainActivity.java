package com.example.myfirstapp.myapplication;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.myapplication.R;

public class MainActivity extends AppCompatActivity {

    EditText userName, password, fullName, dateOfBirth, phoneNumber, email, inputPassword, re_enterPassword, currentAmount, address, nationalID;
    Button login, register_login, register, cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userName = (EditText)findViewById(R.id.txtUserName);
        password = (EditText)findViewById(R.id.txtPassword);
        fullName = (EditText)findViewById(R.id.txtFullName);
        dateOfBirth = (EditText)findViewById(R.id.txtDateOfBirth);
        phoneNumber = (EditText)findViewById(R.id.txtPhoneNumber);
        email = (EditText)findViewById(R.id.txtEmail);
        inputPassword = (EditText)findViewById(R.id.txtInputPassword);
        re_enterPassword = (EditText)findViewById(R.id.txtRe_enterPassword);
        currentAmount = (EditText)findViewById(R.id.txtCurrentAmount);
        address = (EditText)findViewById(R.id.txtAddress);
        nationalID = (EditText)findViewById(R.id.txtNationalID);
    }
}