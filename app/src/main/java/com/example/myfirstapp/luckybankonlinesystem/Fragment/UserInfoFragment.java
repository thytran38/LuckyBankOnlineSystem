package com.example.myfirstapp.luckybankonlinesystem.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.fragment.app.Fragment;

import com.example.myfirstapp.luckybankonlinesystem.Model.CustomerModel;
import com.example.myfirstapp.luckybankonlinesystem.R;
import com.example.myfirstapp.luckybankonlinesystem.Service.FetchingDataService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * A simple {@link UserInfoFragment} subclass.
 * Use the {@link UserInfoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class UserInfoFragment extends Fragment implements View.OnClickListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View v;
    private View rootView;

    private EditText fullnameEt, dateofbirthEt, phonenumberEt, emailEt, addressEt;
    private String fullName, dateOfBirth, phoneNumber, eMail, addRess;
    private String genDer;
    private Button save;
    private RadioGroup Gender;
    private RadioButton rdMale, rdFemale;
    private String userId;

    private FirebaseAuth firebaseAuth;
    private FirebaseFirestore firestore;
    FirebaseUser firebaseUser;

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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.v = view;
        init();

    }




    public void init() {
        CustomerModel csm = getActivity().getIntent().getExtras().getParcelable(FetchingDataService.USER_INFO_KEY);
        fullName = csm.getFullName().toUpperCase().toString();
        Logger.getLogger("debug").warning(String.valueOf(csm.getGender()));
        dateOfBirth = csm.getBirthDate().toString();
        phoneNumber = csm.getPhoneNumber().toString();
        eMail = csm.getEmail().toString();
        addRess = csm.getAddress().toString();
        genDer = csm.getGender().toString();

        fullnameEt = (EditText) rootView.findViewById(R.id.txtProfile_FullName);
        dateofbirthEt = (EditText) rootView.findViewById(R.id.txtProfile_DateOfBirth);
        phonenumberEt = (EditText) rootView.findViewById(R.id.txtProfile_PhoneNumber);
        emailEt = (EditText) rootView.findViewById(R.id.txtProfile_Email);
        addressEt = (EditText) rootView.findViewById(R.id.txtProfile_Address);
        rdMale = (RadioButton) rootView.findViewById(R.id.rdProfile_Male);
        rdFemale = (RadioButton) rootView.findViewById(R.id.rdProfile_Female);
        save = (Button) rootView.findViewById(R.id.btnTransactionNow);

        fullnameEt.setText(fullName);
        dateofbirthEt.setText(dateOfBirth);
        phonenumberEt.setText(phoneNumber);
        emailEt.setText(eMail);
        addressEt.setText(addRess);
        if(genDer.contains("Male"))
        {
            rdMale.setChecked(true);
            Log.d("this gender stuff", "couldnt work");
        }else if(genDer.contains("Female")){
            Log.d("this gender stuff", "couldnt work");

            rdFemale.setChecked(true);
        }
        save.setOnClickListener(this);
        dateofbirthEt.setOnClickListener(this);




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
//
//                        edited.put("fullName", fullname.getText().toString());
//                        edited.put("birthDate", dateofbirth.getText().toString());
//                        edited.put("phoneNumber", phonenumber.getText().toString());
//                        edited.put("address", address.getText().toString());
//
//                        docRef.update(edited).addOnSuccessListener(new OnSuccessListener<Void>() {
//                            @Override
//                            public void onSuccess(Void aVoid) {
////                                Toast.makeText(UserInfoFragment.class, "Your profile account has been updated", Toast.LENGTH_SHORT).show();
//                                //startActivity(new Intent(getApplicationContext(), HomeActivity.class));
//                            }
//                        });
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
////                        Toast.makeText(ProfileActivity.this, "Update profile failed", Toast.LENGTH_SHORT).show();
//                    }
//                });
//            }
//        });
    }


    @Override
    public void onClick(View v) {
        CustomerModel newCustomerModel = new CustomerModel();
        //Get new updates
        String newFullName = fullnameEt.getText().toString();
        String newDOB = dateofbirthEt.getText().toString();
        String newGender;
        if(rdMale.isChecked()){
            newGender = "Male";
        }else{
            newGender = "Female";
        }
        String newPhoneNo = phonenumberEt.getText().toString();
        String newEmail = emailEt.getText().toString();
        String newAddress = addressEt.getText().toString();

        //Parse new updates to instance
        newCustomerModel.setFullName(newFullName);
        try {
            newCustomerModel.setBirthDate(newDOB);
        }catch(ParseException e)
        {
            Log.d("Error parsing dob",e.getMessage().toString());
        }
        newCustomerModel.setGender(CustomerModel.CustomerGender.valueOf(newGender));
        newCustomerModel.setPhoneNumber(newPhoneNo);
        newCustomerModel.setEmail(newEmail);
        newCustomerModel.setAddress(newAddress);



        DocumentReference docRef = firestore.collection("users").document(firebaseUser.getUid());
        Map<String,Object> newUpdates = new HashMap<>();
        newUpdates.put("fullname",newFullName);

        //                        edited.put("birthdate",dateofbirth.getText().toString());
//                        edited.put("phone",phonenumber.getText().toString());
//                        edited.put("email",Email);
//                        edited.put("address",address.getText().toString());
//                        edited.put("nationalId", nationalid.getText().toString());
//                        edited.put("description", description.getText().toString());


    }
}