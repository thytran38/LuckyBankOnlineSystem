package com.example.myfirstapp.luckybankonlinesystem;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.luckybankonlinesystem.Class.Date;
import com.example.myfirstapp.luckybankonlinesystem.Fragment.DatePickerDialog;

import com.example.myfirstapp.luckybankonlinesystem.Fragment.WaitingDialog;
import com.example.myfirstapp.luckybankonlinesystem.Model.AccountModel;
import com.example.myfirstapp.luckybankonlinesystem.Model.CustomerModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.stream.Stream;


public class RegisterActivity extends AppCompatActivity {

    private EditText txtFullName, txtDateOfBirth, txtPhoneNumber, txtEmail, txtPassword, txtRePassword, txtAddress;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private RadioGroup genderGroup;

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
                    ((EditText) v).setError(null);
                    ((EditText) v).setText(Date.getInstance(date, month - 1, year).toString());
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
        genderGroup = findViewById(R.id.genderGroup);
        //Button
        findViewById(R.id.btnRegister).setOnClickListener(v -> RegisterEvent());
        //Initialize
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
    }

    private void RegisterEvent() {
        if (!(valFullName() && valDateOfBirth() && valEmail() && valInputPass() && valRePass() && valGenderChosen() && valAddress() && valPhoneNum())) {
            Toast.makeText(this, "All fields must be correct!", Toast.LENGTH_LONG).show();
            return;
        }
        String email = txtEmail.getText().toString();
        String password = txtPassword.getText().toString();
        String fullName = txtFullName.getText().toString();
        String phoneNum = txtPhoneNumber.getText().toString();
        String dateOfBirth = txtDateOfBirth.getText().toString();
        String address = txtAddress.getText().toString();
        Task<AuthResult> registerTask = auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = task.getResult().getUser();
                        String uid = user.getUid();
                        CustomerModel model = new CustomerModel();
                        model.setCustomerId(uid);
                        try {
                            model.setBirthDate(dateOfBirth);
                        } catch (ParseException e) {
                            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                        model.setEmail(email);
                        model.setFullName(fullName);
                        model.setGender(R.id.rdFemale == genderGroup.getCheckedRadioButtonId() ?
                                CustomerModel.CustomerGender.Female : CustomerModel.CustomerGender.Male);
                        model.setPhoneNumber(phoneNum);
                        model.setAddress(address);
                        ArrayList<AccountModel> accounts = new ArrayList<>();
                        AccountModel account = new AccountModel();
                        account.setAccountNumber(uid + "_" + "prm");
                        account.setAccountOwner(uid);
                        account.setAccountType(AccountModel.AccountType.Primary);
                        account.setCurrentBalance(50000);
                        accounts.add(account);
                        model.setAccounts(accounts);
                        db.collection("users").document(uid).set(model);
                        user.sendEmailVerification().addOnCompleteListener(ignore -> {
                            if (ignore.isSuccessful()) {
                                startActivity(new Intent(this, LoginActivity.class));
                            } else {
                                Toast.makeText(this, ignore.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        });
                    } else {
                        Toast.makeText(this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
        WaitingDialog dialog = new WaitingDialog(R.raw.loading_animation, registerTask);
        dialog.show(getSupportFragmentManager(), null);
    }

    private boolean valGenderChosen() {
        return findViewById(genderGroup.getCheckedRadioButtonId()) != null;
    }



    private boolean valFullName() {
        String fullName = txtFullName.getText().toString();
        if (fullName.isEmpty()) {
            txtFullName.setError("Please enter your name");
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
            txtEmail.setError("Please enter your Email");
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
        if (password.isEmpty()) {
            txtPassword.setError("Please enter password");
            return false;
        } else if (password.length() < 8) {
            txtPassword.setError("Password is too short!");
            return false;
        } else if (!containLowerCase(password) || !containUpperCase(password) || !containSpecialChar(password) || toStream(password).anyMatch(c -> c.equals(" "))) {
            txtPassword.setError("Password must contain at least 1 upper case, lower case, special character and don't contain \" \"");
            return false;
        }
        txtPassword.setError(null);
        return true;
    }

    private static boolean containUpperCase(String str) {
        for (char c : str.toCharArray())
            if (Character.isUpperCase(c))
                return true;
        return false;
    }

    private static boolean containLowerCase(String str) {
        for (char c : str.toCharArray())
            if (Character.isLowerCase(c))
                return true;
        return false;
    }

    private static boolean containSpecialChar(String str) {
        String specialChars = ",./!@#$%^&*()-_+=~[]\\|{}[]";
        Supplier<Stream<String>> supplier = () -> toStream(str);
        for (char c : specialChars.toCharArray())
            if (supplier.get().anyMatch(item -> item.equals(String.valueOf(c))))
                return true;
        return false;
    }

    private boolean valRePass() {
        String password = txtRePassword.getText().toString();
        String rePassword = txtPassword.getText().toString().trim();
        if (rePassword.isEmpty()) {
            txtRePassword.setError("Please type your password again");
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
            txtAddress.setError("Please enter your address");
            return false;
        } else {
            txtAddress.setError(null);
            return true;
        }
    }

    private boolean valDateOfBirth() {
        String dateOfBirth = txtDateOfBirth.getText().toString();
        if (dateOfBirth.isEmpty()) {
            txtDateOfBirth.setError("Please enter your birth day");
            return false;
        } else {
            txtDateOfBirth.setError(null);
            return true;
        }
    }

    private boolean valPhoneNum() {
        String phoneNum = txtPhoneNumber.getText().toString();
        if (phoneNum.isEmpty()) {
            txtPhoneNumber.setError("Please enter your phone number");
            return false;
        } else {
            txtPhoneNumber.setError(null);
            return true;
        }
    }

    private static Stream<String> toStream(String text) {
        String[] res = new String[text.length()];
        for (int i = 0; i < text.length(); i++) {
            res[i] = String.valueOf(text.charAt(i));
        }
        return Arrays.stream(res);
    }

}