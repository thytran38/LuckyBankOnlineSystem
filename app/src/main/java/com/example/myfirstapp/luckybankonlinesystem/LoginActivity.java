package com.example.myfirstapp.luckybankonlinesystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    EditText userName, password;
    Button login, register_login;
    TextView register, forgetPass;
    FirebaseAuth auth;
    FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName = (EditText)findViewById(R.id.etEmail);
        password = (EditText)findViewById(R.id.etPassword);
        login = (Button)findViewById(R.id.btnSend);
        register = (TextView)findViewById(R.id.tvRegister);
        forgetPass = (TextView)findViewById(R.id.tvForgotPassword);


        login.setOnClickListener(v -> {
            LoginEvent();
//            try {
//                Thread.sleep(2000);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            Intent intent = new Intent(this, SplashScreenActivity.class);
//            startActivity(intent);
//
            Intent intent1 = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent1);

        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                finish();
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
        if (!UserNameValidCheck()|!PassWordValidCheck()){
            return;
        }

        String checkusername = userName.getText().toString();
        String checkpassword = password.getText().toString();

//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
//        if (firebaseUser.isEmailVerified()){
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Intent intent = new Intent(this, SplashScreenActivity.class);
        startActivity(intent);
            Toast.makeText(LoginActivity.this,"Dang nhap thanh cong",Toast.LENGTH_LONG).show();
//
//        }else {
//            Toast.makeText(LoginActivity.this,"Email chua duoc xac nhan",Toast.LENGTH_LONG).show();
//        }
    }

    private Boolean UserNameValidCheck(){
        String checkusername = userName.getText().toString();
        String emailValid =  "[a-zA-Z0-9]+@[a-z]+\\.+[a-z]+";
        if (checkusername.isEmpty()){
            userName.setError("UserName Can't Be Empty");
            return false;
        }else if (!checkusername.matches(emailValid)){
            userName.setError("UserName is validate");
            return false;
        }else {
            userName.setError(null);
            return true;
        }
    }
    private Boolean PassWordValidCheck(){
        String checkpassword = password.getText().toString();
        String passwordValid = ".{4,}"+ "/^\\S{3,}$/";

        if (checkpassword.isEmpty()){
            password.setError("PassWord Can't Be Empty");
            return false;
        }else if (!checkpassword.matches(passwordValid)){
            password.setError("No white spaces and must has more than 4 characters ");
            return false;
        }else {
            password.setError(null);
            return true;
        }
    }

}