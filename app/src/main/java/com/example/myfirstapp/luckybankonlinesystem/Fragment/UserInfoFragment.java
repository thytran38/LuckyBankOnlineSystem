package com.example.myfirstapp.luckybankonlinesystem.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.myfirstapp.luckybankonlinesystem.Class.Date;
import com.example.myfirstapp.luckybankonlinesystem.Model.CustomerModel;
import com.example.myfirstapp.luckybankonlinesystem.R;
import com.example.myfirstapp.luckybankonlinesystem.Service.FetchingDataService;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;

public class UserInfoFragment extends Fragment implements View.OnClickListener {

    private EditText etFullName, etBirthDate, etPhoneNumber, etAddress;
    private RadioButton rdFemale;
    private String currentUserUID;
    private CustomerModel userInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.userinfo_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        userInfo = getActivity().getIntent().getExtras().getParcelable(FetchingDataService.USER_INFO_KEY);
        currentUserUID = userInfo.getCustomerId();
        etFullName = view.findViewById(R.id.txtProfile_FullName);
        etBirthDate = view.findViewById(R.id.txtProfile_DateOfBirth);
        etPhoneNumber = view.findViewById(R.id.txtProfile_PhoneNumber);
        EditText etEmail = view.findViewById(R.id.txtProfile_Email);
        rdFemale = view.findViewById(R.id.rdProfile_Female);
        RadioButton rdMale = view.findViewById(R.id.rdProfile_Male);
        etAddress = view.findViewById(R.id.txtProfile_Address);
        etEmail.setEnabled(false);
        etBirthDate.setOnClickListener(v -> {
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
            dialog.show(getChildFragmentManager(), null);
        });
        etFullName.setText(userInfo.getFullName());
        etBirthDate.setText(userInfo.getBirthDate());
        etPhoneNumber.setText(userInfo.getPhoneNumber());
        etEmail.setText(userInfo.getEmail());
        etAddress.setText(userInfo.getAddress());
        if (userInfo.getGender() == CustomerModel.CustomerGender.Female)
            rdFemale.setChecked(true);
        else
            rdMale.setChecked(true);
        view.findViewById(R.id.btnTransaction_Transfer).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        userInfo.setFullName(etFullName.getText().toString());
        try {
            userInfo.setBirthDate(etBirthDate.getText().toString());
        } catch (ParseException e) {
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
        userInfo.setPhoneNumber(etPhoneNumber.getText().toString());
        userInfo.setAddress(etAddress.getText().toString());
        userInfo.setGender(rdFemale.isChecked() ? CustomerModel.CustomerGender.Female
                                    : CustomerModel.CustomerGender.Male);
        Task<Void> updateUserInfoTask = FirebaseFirestore.getInstance()
                .collection("users")
                .document(currentUserUID)
                .set(userInfo);
        WaitingDialog dialog = new WaitingDialog(getContext(), R.raw.loading_animation, updateUserInfoTask);
        dialog.setOnWaitingDialogCompletedListener(
                () -> Toast.makeText(getContext(), "Update user's info completed", Toast.LENGTH_LONG).show());
        dialog.show(getFragmentManager(), null);
    }
}