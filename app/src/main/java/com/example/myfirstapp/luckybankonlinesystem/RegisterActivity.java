package com.example.myfirstapp.luckybankonlinesystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.luckybankonlinesystem.Class.Date;
import com.example.myfirstapp.luckybankonlinesystem.Fragment.DatePickerDialog;
import com.example.myfirstapp.luckybankonlinesystem.Fragment.WaitingDialog;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;
import java.util.logging.Logger;


public class RegisterActivity extends AppCompatActivity {

    EditText txtFullName, txtDateOfBirth, txtPhoneNumber, txtEmail, txtPassword, txtRePassword, txtAddress;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //EditText
        txtFullName = findViewById(R.id.txtFullName);
        txtDateOfBirth = findViewById(R.id.txtDateOfBirth);
        txtDateOfBirth.setOnClickListener(v -> {
            DatePickerDialog dialog = new DatePickerDialog(new DatePickerDialog.OnDatePickedListener() {
                @Override
                public void onDateOk(int date, int month, int year) {
                    ((EditText) v).setText(Date.getInstance(date, month, year).toString());
                }

                @Override
                public void onDateError(int date, int month, int year) {
                    v.requestFocus();
                    ((EditText) v).setError("Age must be equal or greater than 18");
                }
            }, (date, month, year) -> {
                Date dateObj = Date.getInstance();
                Date minValidDate = Date.getInstance(date, month, year + 18);
                return minValidDate.getEpochSecond() <= dateObj.getEpochSecond();
            });
            dialog.show(getSupportFragmentManager(), null);
        });
        txtPhoneNumber = findViewById(R.id.txtPhoneNumber);
        txtEmail = findViewById(R.id.txtEmail);
        txtPassword = findViewById(R.id.txtInputPassword);
        txtRePassword = findViewById(R.id.txtRe_enterPassword);
        txtAddress = findViewById(R.id.txtAddress);
        //Button
        findViewById(R.id.btnRegister).setOnClickListener(v -> RegisterEvent());
        //Initialize
        auth = FirebaseAuth.getInstance();
    }

    private void RegisterEvent() {
//        if (!valAddress() || !valDateOfBirth() || !valEmail() || !valFullName() || !valInputPass() || !valRePass() || !valPhoneNum()) {
//            return;
//        }
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
//        String fullName = txtFullName.getText().toString();
//        String phoneNum = txtPhoneNumber.getText().toString();
//        String dateOfBirth = txtDateOfBirth.getText().toString();
//        String rePassword = txtRePassword.getText().toString();
//        String address = txtAddress.getText().toString();
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        String uid = Objects.requireNonNull(Objects.requireNonNull(task.getResult()).getUser()).getUid();
                        Logger.getLogger("DEBUG").warning("create successfully");
                    } else {
                        Toast.makeText(RegisterActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private boolean valFullName() {
        String fullName = txtFullName.getText().toString();
        if (fullName.isEmpty()) {
            txtFullName.setError("Please Enter Your Name");
            return false;
        } else {
            txtFullName.setError(null);
            return true;
        }
    }

    private boolean valEmail() {
        String email = txtEmail.getText().toString().trim();
        String emailCond = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.isEmpty()) {
            txtEmail.setError("Please Enter Your Email");
            return false;
        } else if (!email.matches(emailCond)) {
            txtEmail.setError("Invalid Email");
            return false;
        } else {
            txtEmail.setError(null);
            return true;
        }
    }

    private boolean valInputPass() {
        String password = txtPassword.getText().toString().trim();
        String passCond = "\"(?=\\S+$)\"+\".{4,}\"+\"(?=.*[a-zA-Z])\"";
        if (password.isEmpty()) {
            txtPassword.setError("Please Enter PassWord");
            return false;
        } else if (!password.matches(passCond)) {
            txtPassword.setError("Password is too weak");
            return false;
        } else {
            txtPassword.setError(null);
            return true;
        }
    }

    private boolean valRePass() {
        String password = txtRePassword.getText().toString();
        String rePassword = txtPassword.getText().toString().trim();
        if (rePassword.isEmpty()) {
            txtRePassword.setError("Please Type Your Password Again");
            return false;
        } else if (!rePassword.equals(password)) {
            txtRePassword.setError("Doesn't match the InputPassword");
            return false;
        } else {
            txtRePassword.setError(null);
            return true;
        }
    }

    private boolean valAddress() {
        String valAddress = txtAddress.getText().toString();
        if (valAddress.isEmpty()) {
            txtAddress.setError("Please Enter Your Address");
            return false;
        } else {
            txtAddress.setError(null);
            return true;
        }
    }

    private boolean valDateOfBirth() {
        String dateOfBirth = txtDateOfBirth.getText().toString();
        if (dateOfBirth.isEmpty()) {
            txtDateOfBirth.setError("Please Enter Your Date of Birth");
            return false;
        } else {
            txtDateOfBirth.setError(null);
            return true;
        }
    }

    private boolean valPhoneNum() {
        String phoneNum = txtPhoneNumber.getText().toString();
        if (phoneNum.isEmpty()) {
            txtPhoneNumber.setError("Please Enter Your Phone Number");
            return false;
        } else {
            txtPhoneNumber.setError(null);
            return true;
        }
    }

}