package com.example.myfirstapp.luckybankonlinesystem.Class;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.luckybankonlinesystem.R;

import java.util.Locale;

public class TransactionOverviewViewHolder extends RecyclerView.ViewHolder {

    private final TextView tvShortName, tvTransactionId, tvTransactionPerformTime, tvTransactionValue;

    public TransactionOverviewViewHolder(@NonNull View itemView) {
        super(itemView);
        tvShortName = itemView.findViewById(R.id.tvShortName);
        tvTransactionId = itemView.findViewById(R.id.tvTransactionId);
        tvTransactionPerformTime = itemView.findViewById(R.id.tvTransactionPerformTime);
        tvTransactionValue = itemView.findViewById(R.id.tvTransactionValue);
    }

    public void setShortName(String email) {
        tvShortName.setText(email.substring(0, 3));
    }

    public void setTransactionId(String transactionId) {
        tvTransactionId.setText(transactionId);
    }

    public void setTransactionPerformTime(long epochTime) {
        tvTransactionPerformTime.setText(Date.getInstance(epochTime).toString(true));
    }

    public void setTransactionValue(int value) {
        tvTransactionValue.setText(String.format(Locale.US, "- %,d", value));
    }
}
