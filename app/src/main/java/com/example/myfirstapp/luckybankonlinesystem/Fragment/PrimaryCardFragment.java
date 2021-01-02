package com.example.myfirstapp.luckybankonlinesystem.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.myfirstapp.luckybankonlinesystem.Model.AccountModel;
import com.example.myfirstapp.luckybankonlinesystem.Model.CustomerModel;
import com.example.myfirstapp.luckybankonlinesystem.R;
import com.example.myfirstapp.luckybankonlinesystem.Service.FetchingDataService;
import com.example.myfirstapp.luckybankonlinesystem.SplashScreenActivity;

import java.util.ArrayList;

import javax.annotation.Nullable;

public class PrimaryCardFragment extends Fragment {
    private View v;
    private String USER_NAME ="";
    private String ACCOUNT_NUMBER = "";
    private TextView usnTv, accTv;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.primary_card_view, container, false);
//        float scaleFactor = 0.5f;
//        v.setScaleX(scaleFactor);
//        v.setScaleY(scaleFactor);

        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceSaved)
    {
        this.v =view;
//        float scaleFactor = 0.5f;
//        v.setScaleX(scaleFactor);
//        v.setScaleY(scaleFactor);


        usnTv = (TextView)v.findViewById(R.id.tvUserName);
        accTv = (TextView)v.findViewById(R.id.tvAccnumber);
        init();
        usnTv.setText(USER_NAME);
        accTv.setText(ACCOUNT_NUMBER);

    }

    public void init(){
        CustomerModel cm = getActivity().getIntent().getExtras().getParcelable(FetchingDataService.USER_INFO_KEY);
//        AccountModel[] am = getActivity().getIntent().getExtras().getParcelableArray(SplashScreenActivity.);
        USER_NAME = cm.getFullName().toUpperCase().toString();
        ArrayList<AccountModel> userAccounts = cm.getAccounts();
        AccountModel primeAcc = userAccounts.get(0);
        String primeAccNumber = primeAcc.getAccountNumber();
        ACCOUNT_NUMBER = primeAccNumber;
    }
}
