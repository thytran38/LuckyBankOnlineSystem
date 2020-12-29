package com.example.myfirstapp.luckybankonlinesystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class ForgetPassActivity extends AppCompatActivity{
    private static final String TAG = "ForgetPassActivity";
    EditText emailEt;
    public String EMAIL_TO_STRING;
    Button btnSend;
    TextView testTv;
    private FirebaseAuth fbauth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        fbauth = FirebaseAuth.getInstance();
        emailEt = (EditText) findViewById(R.id.etEmail);
        //EMAIL_TO_STRING = "augusttran@outlook.com";
        btnSend = findViewById(R.id.btnSend);
        testTv = findViewById(R.id.tvTest);


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EMAIL_TO_STRING = emailEt.getText().toString().trim();
                testTv.setText(EMAIL_TO_STRING);

                if(emailEt.getText().equals("") || !valEmail(EMAIL_TO_STRING) ){
                    testTv.setText("An email, please.");
                    return;
                }
                    try{
                        fbauth.fetchSignInMethodsForEmail(EMAIL_TO_STRING)
                                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                        SignInMethodQueryResult result = task.getResult();
                                        if (result.getSignInMethods().isEmpty()){
                                            Log.d("ForgetPassActivity","Email Not Exist");
                                            testTv.setText("Email not found.");
                                        }
                                    }
                                });

                        if(!valEmail(EMAIL_TO_STRING)){
                            testTv.setText(R.string.valid_email_pls);

                        }
                    }catch(IllegalArgumentException e){
                        testTv.setText(R.string.valid_email_pls);
                        Log.d("ForgetPassActivity","Failed");
                        e.printStackTrace();
                    }finally{
                        fbauth.sendPasswordResetEmail(EMAIL_TO_STRING)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (task.isSuccessful()) {
                                            Log.d(TAG, "Email sent.");
                                            testTv.setText(R.string.email_sent);
                                        }
                                    }
                                });


                    }
            }
        });

    }

        @Override
        public void onBackPressed() {
            super.onBackPressed();
            Intent backHome = new Intent();
            startActivity(backHome);
        }

        private boolean valEmail(String thisEmailtext) {
            thisEmailtext = emailEt.getText().toString().trim();
            String emailCond = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            if (thisEmailtext.isEmpty()) {
                return false;
            } else if (!thisEmailtext.matches(emailCond)) {
                return false;
            } else {
                return true;
            }
        }

}
