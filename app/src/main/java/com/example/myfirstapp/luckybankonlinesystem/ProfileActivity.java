package com.example.myfirstapp.luckybankonlinesystem;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.luckybankonlinesystem.Class.Date;
import com.example.myfirstapp.luckybankonlinesystem.Fragment.DatePickerDialog;
import com.example.myfirstapp.luckybankonlinesystem.Model.CustomerModel;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {

    private EditText fullname, dateofbirth, phonenumber, email, address;
    private Button save;
    private RadioGroup gender;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    FirebaseUser firebaseUser;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

//        fullname = (EditText)findViewById(R.id.txtProfile_FullName);
//        dateofbirth = (EditText)findViewById(R.id.txtProfile_DateOfBirth);
//        phonenumber = (EditText)findViewById(R.id.txtProfile_PhoneNumber);
//        email = (EditText)findViewById(R.id.txtProfile_Email);
//        address = (EditText)findViewById(R.id.txtProfile_Address);
//
//        save = (Button) findViewById(R.id.BtnProfile_SaveButton);
//        female = (RadioButton) save.findViewById(R.id.rdProfile_Female);
//        male = (RadioButton) save.findViewById(R.id.rdProfile_Male);
//
//        // Connect to  Firebase's Authentication
//        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
//        // Connect to  Firebase's FireStore
//        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
//        // Check id account information
//        String userId = firebaseAuth.getCurrentUser().getUid();
//        // Current user account
//        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

        // Load data from Firebase back to system
//            DocumentReference documentReference = firestore.collection("users").document(userId);
//            documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//                @Override
//                public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//                    if(documentSnapshot.exists()){
//                        fullname.setText(documentSnapshot.getString("fName"));
//                        dateofbirth.setText(documentSnapshot.getString("birthdate"));
//                        phonenumber.setText(documentSnapshot.getString("phone"));
//                        email.setText(documentSnapshot.getString("email"));
//                        address.setText(documentSnapshot.getString("address"));
//                        nationalid.setText(documentSnapshot.getString("nationalId"));
//                        description.setText(documentSnapshot.getString("description"));
//                    }else {
//                        Log.d("tag", "onEvent: Document do not exists");
//                    }
//                }
//            });

        // Edit account's information
//        save.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                final String Email = email.getText().toString();
//                firebaseUser.updateEmail(Email).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void aVoid) {
//                        DocumentReference docRef = firestore.collection("users").document(firebaseUser.getUid());
//                        Map<String,Object> edited = new HashMap<>();
//                        edited.put("fName",fullname.getText().toString());
//                        edited.put("birthdate",dateofbirth.getText().toString());
//                        edited.put("phone",phonenumber.getText().toString());
//                        edited.put("email",Email);
//                        edited.put("address",address.getText().toString());
//                        edited.put("nationalId", nationalid.getText().toString());
//                        edited.put("description", description.getText().toString());
//                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
//                                Toast.makeText(ProfileActivity.this, "Your profile account has been updated", Toast.LENGTH_SHORT).show();
//                                //startActivity(new Intent(getApplicationContext(), HomeActivity.class));
//                                finish();
//                            }
//                        });
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(ProfileActivity.this, "Update profile failed", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
        fullname = (EditText)findViewById(R.id.txtProfile_FullName);
        dateofbirth = (EditText)findViewById(R.id.txtProfile_DateOfBirth);
        dateofbirth.setOnClickListener(v -> {
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
        phonenumber = (EditText)findViewById(R.id.txtProfile_PhoneNumber);
        email = (EditText)findViewById(R.id.txtProfile_Email);
        address = (EditText)findViewById(R.id.txtProfile_Address);
        gender = (RadioGroup)findViewById(R.id.rg_Profile_GenderGroup);
        save = (Button) findViewById(R.id.BtnProfile_SaveButton);

        // Connect to  Firebase's Authentication
        firebaseAuth = FirebaseAuth.getInstance();
        // Connect to  Firebase's FireStore
        firestore = FirebaseFirestore.getInstance();
        // Check id account information
        userId = firebaseAuth.getCurrentUser().getUid();
        // Current user account
        firebaseUser = firebaseAuth.getCurrentUser();
//
        // Load data from Firebase back to system
        DocumentReference documentReference = firestore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()) {
                    fullname.setText(documentSnapshot.getString("fullName"));
                    dateofbirth.setText(documentSnapshot.getString("birthDate"));
//                    gender.getCheckedRadioButtonId(documentSnapshot.getString("gender"));
                    phonenumber.setText(documentSnapshot.getString("phoneNumber"));
                    email.setText(documentSnapshot.getString("email"));
                    address.setText(documentSnapshot.getString("address"));
                }else {
                    Log.d("tag", "onEvent: Document do not exists");
                }
            }
        });
//
        // Edit account's information
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String Email = email.getText().toString();
                firebaseUser.updateEmail(Email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        DocumentReference docRef = firestore.collection("users").document(firebaseUser.getUid());
                        Map<String,Object> edited = new HashMap<>();

                        edited.put("fullName", fullname.getText().toString());
                        edited.put("birthDate", dateofbirth.getText().toString());
                        edited.put("phoneNumber", phonenumber.getText().toString());
                        edited.put("address", address.getText().toString());

                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(ProfileActivity.this, "Your profile account has been updated", Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ProfileActivity.this, "Update profile failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
