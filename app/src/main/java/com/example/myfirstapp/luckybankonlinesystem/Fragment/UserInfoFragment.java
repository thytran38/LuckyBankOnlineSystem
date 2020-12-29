package com.example.myfirstapp.luckybankonlinesystem.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.myfirstapp.luckybankonlinesystem.Class.Date;
import com.example.myfirstapp.luckybankonlinesystem.R;
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

/**
 * A simple {@link UserInfoFragment} subclass.
 * Use the {@link UserInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserInfoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View v;
    private View rootView;

    private EditText fullname, dateofbirth, phonenumber, email, address;
    private Button save;
    private RadioGroup gender;
    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    FirebaseUser firebaseUser;
    private String userId;

    public UserInfoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static UserInfoFragment newInstance(String param1, String param2) {
        UserInfoFragment fragment = new UserInfoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.userinfo_fragment, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view,savedInstanceState);
        this.v = view;
        init();
    }

    public void init()
    {
        fullname = (EditText)rootView.findViewById(R.id.txtProfile_FullName);
        dateofbirth = (EditText)rootView.findViewById(R.id.txtProfile_DateOfBirth);
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
            dialog.show(getFragmentManager(), null);
        });


        phonenumber = (EditText)rootView.findViewById(R.id.txtProfile_PhoneNumber);
        email = (EditText)rootView.findViewById(R.id.txtProfile_Email);
        address = (EditText)rootView.findViewById(R.id.txtProfile_Address);
        gender = (RadioGroup)rootView.findViewById(R.id.rg_Profile_GenderGroup);
        save = (Button) rootView.findViewById(R.id.BtnProfile_SaveButton);

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

        documentReference.addSnapshotListener(getActivity(), new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if(documentSnapshot.exists()) {
                    fullname.setText(documentSnapshot.getString("fullName"));
                    dateofbirth.setText(documentSnapshot.getString("birthDate"));
                    //gender.getCheckedRadioButtonId(documentSnapshot.getString("gender"));
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
//                                Toast.makeText(UserInfoFragment.class, "Your profile account has been updated", Toast.LENGTH_SHORT).show();
                                //startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(ProfileActivity.this, "Update profile failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}