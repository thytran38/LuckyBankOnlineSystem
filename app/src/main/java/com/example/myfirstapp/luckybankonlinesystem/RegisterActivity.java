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

    EditText fullName, dateOfBirth, phoneNumber, email, inputPassword, re_enterPassword, currentAmount, address, nationalID;
    Button register, cancel;
    FirebaseAuth auth;

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
        address = (EditText)findViewById(R.id.txtAddress);
        nationalID = (EditText)findViewById(R.id.txtNationalID);
        //Buttons
        register = (Button)findViewById(R.id.btnRegister);
        cancel = (Button)findViewById(R.id.btnCancel);
        //Initialize
        auth = FirebaseAuth.getInstance();
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegisterEvent();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
    private void RegisterEvent() {
        if (!valAddress()|!valdateofBirth()|!valEmail()|!valfullName()|!valinputPass()|!valrePass()|!valphoneNum()|!valnationid()){
            return;
        }
        String reg_email=email.getText().toString();
        String reg_password=inputPassword.getText().toString();
////        String reg_fullname=fullName.getText().toString();
////        String reg_phonenum=phoneNumber.getText().toString();
////        String reg_datebirth=dateOfBirth.getText().toString();
////        String reg_repassword=re_enterPassword.getText().toString();
////        String reg_naitionid=nationalID.getText().toString();
////        String reg_address=address.getText().toString();
////        String reg_currentamount=currentAmount.getText().toString();
        auth.createUserWithEmailAndPassword(reg_email,reg_password)
                .addOnCompleteListener(this,new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete( Task<AuthResult> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RegisterActivity.this,"Register success",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(RegisterActivity.this,ProfileActivity.class);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(RegisterActivity.this,"Register fail",Toast.LENGTH_LONG).show();
                        }
                    }
                });

    }
    private Boolean valfullName(){
        String valfullName=fullName.getText().toString();
        if (valfullName.isEmpty()){
            fullName.setError("Please Enter Your Name");
            return false;
        }else {
            fullName.setError(null);
            return true;
        }
    }
    private Boolean valEmail(){
        String valEmail= email.getText().toString().trim();
        String emailCond = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (valEmail.isEmpty()){
            email.setError("Please Enter Your Email");
            return false;
        }else if (!valEmail.matches(emailCond)){
            email.setError("Invaild Email");
            return false;
        }else {
            email.setError(null);
            return true;
        }
    }
    private Boolean valinputPass(){
        String valinputPass=inputPassword.getText().toString().trim();
        String passCond="\"(?=\\S+$)\"+\".{4,}\"+\"(?=.*[a-zA-Z])\"";
        if (valinputPass.isEmpty()){
            inputPassword.setError("Please Enter PassWord");
            return false;
        }else if (!valinputPass.matches(passCond)){
            inputPassword.setError("PassWord is too weak");
            return false;
        }else {
            inputPassword.setError(null);
            return true;
        }
    }
    private Boolean valrePass(){
        String valrePass=re_enterPassword.getText().toString();
        String valinputPass=inputPassword.getText().toString().trim();
        if (valrePass.isEmpty()){
            re_enterPassword.setError("Please Type Your PassWord Again");
            return false;
        }else if (!valrePass.matches(valinputPass)){
            re_enterPassword.setError("Dosen't match the IputPassWord");
            return false;
        }else {
            re_enterPassword.setError(null);
            return true;
        }
    }
    private Boolean valAddress(){
        String valAddress=address.getText().toString();
        if (valAddress.isEmpty()){
            address.setError("Please Enter Your Address");
            return false;
        }else {
            address.setError(null);
            return true;
        }
    }
    private Boolean valnationid(){
        String valnationID=nationalID.getText().toString();
        if (valnationID.isEmpty()){
            nationalID.setError("Please Enter Nation ID");
            return false;
        }else {
            nationalID.setError(null);
            return true;
        }
    }
    private Boolean valdateofBirth(){
        String valdateofBirth=dateOfBirth.getText().toString();
        if (valdateofBirth.isEmpty()){
            dateOfBirth.setError("Please Enter Your Date of Birth");
            return false;
        }else {
            dateOfBirth.setError(null);
            return true;
        }
    }
    private Boolean valphoneNum(){
        String valphoneNum=phoneNumber.getText().toString();
        if (valphoneNum.isEmpty()){
            phoneNumber.setError("Please Enter Your Phone Number");
            return false;
        }else {
            phoneNumber.setError(null);
            return true;
        }
    }


}