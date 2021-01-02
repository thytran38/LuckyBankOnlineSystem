package com.example.myfirstapp.luckybankonlinesystem.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.myfirstapp.luckybankonlinesystem.Class.Date;
import com.example.myfirstapp.luckybankonlinesystem.R;

public class TransferInformationFragment extends DialogFragment {

    private final double amount;
    private final long dateCreated, targetId;
    private final String transactionId, targetEmail, message;

    public TransferInformationFragment(double amount, long dateCreated, String transactionId, String targetEmail, long targetId, String message) {
        this.amount = amount;
        this.dateCreated = dateCreated;
        this.transactionId = transactionId;
        this.targetEmail = targetEmail;
        this.targetId = targetId;
        this.message = message;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.transfer_information, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((TextView) view.findViewById(R.id.tvAmountConfirm)).setText(String.valueOf(amount));
        ((TextView) view.findViewById(R.id.tvDateCreatedConfirm)).setText(Date.getInstance(dateCreated).toString(true));
        ((TextView) view.findViewById(R.id.tvTradingCodeConfirm)).setText(String.valueOf(transactionId));
        ((TextView) view.findViewById(R.id.tvBeneficiaryNameConfirm)).setText(targetEmail);
        ((TextView) view.findViewById(R.id.tvBeneficiaryAccountConfirm)).setText(String.valueOf(targetId));
        ((TextView) view.findViewById(R.id.tvMessageConfirm)).setText(message);
    }
}
