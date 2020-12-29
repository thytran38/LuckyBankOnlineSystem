package com.example.myfirstapp.luckybankonlinesystem;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.luckybankonlinesystem.Class.PasswordToggleActionListener;
import com.example.myfirstapp.luckybankonlinesystem.Fragment.WaitingDialog;
import com.example.myfirstapp.luckybankonlinesystem.Model.CustomerModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Locale;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private EditText tvUserName, tvPassword;
    private FirebaseAuth firebaseAuth;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tvUserName = (EditText) findViewById(R.id.etEmail);
        tvPassword = (EditText) findViewById(R.id.etPassword);

        PasswordToggleActionListener listener = new PasswordToggleActionListener();
        tvPassword.setOnTouchListener(listener);

        // Connect to Firebase
        firebaseAuth = FirebaseAuth.getInstance();
        findViewById(R.id.btnSend).setOnClickListener(v -> LoginEvent());

        findViewById(R.id.tvRegister).setOnClickListener(v -> startActivity(new Intent(this, RegisterActivity.class)));

        findViewById(R.id.tvForgotPassword).setOnClickListener(v -> {
            Intent forgetPassIntent = new Intent(this, ForgetPassActivity.class);
            startActivity(forgetPassIntent);
        });
    }

    private void LoginEvent() {
        if (!(valUserName() && valPassword())) {
            Toast.makeText(this, "All fields must be correct!", Toast.LENGTH_LONG).show();
            return;
        }

        String username = tvUserName.getText().toString();
        String password = tvPassword.getText().toString();

        Task<AuthResult> signInTask = firebaseAuth.signInWithEmailAndPassword(username, password).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                FirebaseUser user = Objects.requireNonNull(task.getResult()).getUser();
                if (user != null)
                if (!user.isEmailVerified()) {
                    firebaseAuth.signOut();
                    Toast.makeText(this, "Your email address hasn't been verified yet", Toast.LENGTH_LONG).show();
                } else {
                    startActivity(new Intent(this, SplashScreenActivity.class));
                }
            } else {
                Toast.makeText(this, task.getException().getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        WaitingDialog dialog = new WaitingDialog(this, R.raw.loading_animation, signInTask);
        dialog.show(getSupportFragmentManager(), null);
    }

    private boolean valUserName() {
        String username = tvUserName.getText().toString();
        if (username.isEmpty()) {
            tvUserName.setError("Username can't be empty");
            return false;
        } else {
            tvUserName.setError(null);
            return true;
        }
    }

    private boolean valPassword() {
        String password = tvPassword.getText().toString();
        if (password.isEmpty()) {
            tvPassword.setError("Password can't be empty. Please input your password");
            return false;
        } else if (password.length() < CustomerModel.PASSWORD_MIN_LENGTH) {
            tvPassword.setError(String.format(Locale.US, "Password must be >= %d characters! Please input your password again", CustomerModel.PASSWORD_MIN_LENGTH));
            return false;
        }
        tvPassword.setError(null);
        return true;
    }
}