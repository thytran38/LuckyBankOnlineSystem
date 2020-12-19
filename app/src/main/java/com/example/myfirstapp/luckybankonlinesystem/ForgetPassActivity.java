package com.example.myfirstapp.luckybankonlinesystem;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.SignInMethodQueryResult;

public class ForgetPassActivity extends AppCompatActivity{
    private static final String TAG = "ForgetPassACtivity";
    EditText email;
    private String EMAIL_TO_STRING = "";
    Button btnSend;
    private FirebaseAuth fbauth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);
        fbauth = FirebaseAuth.getInstance();
        email = findViewById(R.id.etEmail);
        //EMAIL_TO_STRING = email.getText().toString().trim();
        EMAIL_TO_STRING = "augusttran@outlook.com";
        btnSend = findViewById(R.id.btnSend);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(email.getText().equals("")){
                    Toast.makeText(ForgetPassActivity.this, "Please enter an email", Toast.LENGTH_SHORT).show();
                }
                    try{
                        fbauth.fetchSignInMethodsForEmail(EMAIL_TO_STRING)
                                .addOnCompleteListener(new OnCompleteListener<SignInMethodQueryResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<SignInMethodQueryResult> task) {
                                        SignInMethodQueryResult result = task.getResult();
                                        if (result.getSignInMethods().isEmpty()){
                                            Log.d("ForgetPassActivity","Email Not Exist");
                                            Toast.makeText(ForgetPassActivity.this, "Email not existed!", Toast.LENGTH_SHORT).show();
                                        }
                                        else{
                                            fbauth.sendPasswordResetEmail(EMAIL_TO_STRING)
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Log.d(TAG, "Email sent.");
                                                            }
                                                        }
                                                    });
                                        }
                                    }
                                });
                    }catch(IllegalArgumentException e){
                        Log.d("ForgetPassActivity","Failed");
                        e.printStackTrace();
                    }
            }
        });

    }

}
