package com.example.myfirstapp.luckybankonlinesystem;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.luckybankonlinesystem.LoginActivity;
import com.example.myfirstapp.luckybankonlinesystem.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public  class RegisterActivity extends AppCompatActivity {

    private EditText fullName, dateOfBirth, phoneNumber, email, inputPassword, re_enterPassword, currentAmount, address, nationalID;
    private Button register, cancel;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //EditText
        fullName = (EditText)findViewById(R.id.txtFullName);
        dateOfBirth = (EditText)findViewById(R.id.txtDateOfBirth);
        phoneNumber = (EditText)findViewById(R.id.txtPhoneNumber);
        email = (EditText)findViewById(R.id.txtEmail);
        inputPassword = (EditText)findViewById(R.id.txtInputPassword);
        re_enterPassword = (EditText)findViewById(R.id.txtRe_enterPassword);
        currentAmount = (EditText)findViewById(R.id.txtCurrentAmount);
        address = (EditText)findViewById(R.id.txtAddress);
        nationalID = (EditText)findViewById(R.id.txtNationalID);
        //Buttons
        register = (Button)findViewById(R.id.btnRegister);
        cancel = (Button)findViewById(R.id.btnCancel);
        //Initialize
        auth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
//            public void onClick(View v) {
//                RegisterEvent();
//            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
//    private void RegisterEvent() {
//        String reg_email=email.getText().toString();
//        String reg_password=inputPassword.getText().toString();
//        String reg_fullname=fullName.getText().toString();
//        String reg_phonenum=phoneNumber.getText().toString();
//        String reg_datebirth=dateOfBirth.getText().toString();
//        String reg_repassword=re_enterPassword.getText().toString();
//        String reg_naitionid=nationalID.getText().toString();
//        String reg_address=address.getText().toString();
//        String reg_currentamount=currentAmount.getText().toString();
//        auth.createUserWithEmailAndPassword(reg_email,reg_password)
//                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()){
//                            Toast.makeText(RegisterActivity.this,"Ban da dang ky thanh cong",Toast.LENGTH_LONG).show();
//                        }else{
//                            Toast.makeText(RegisterActivity.this,"Ban da dang ky that bai",Toast.LENGTH_LONG).show();
//                        }
//                    }
//                });
//    }
}