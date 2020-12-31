package com.example.myfirstapp.luckybankonlinesystem.Class;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.luckybankonlinesystem.R;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class TransactionOverviewViewHolder extends RecyclerView.ViewHolder {

    private final TextView tvShortName, tvTransactionId, tvTransactionPerformTime, tvTransactionValue;

    public TransactionOverviewViewHolder(@NonNull View itemView) {
        super(itemView);
        tvShortName = itemView.findViewById(R.id.tvShortName);
        tvTransactionId = itemView.findViewById(R.id.tvTransactionId);
        tvTransactionPerformTime = itemView.findViewById(R.id.tvTransactionPerformTime);
        tvTransactionValue = itemView.findViewById(R.id.tvTransactionValue);
    }

    public void setShortName(String fullName) {
        String[] words = fullName.split("\\s+");
        String shortName = (String.valueOf(words[words.length - 1].charAt(0)) + String.valueOf(words[0].charAt(0))).toUpperCase();
        tvShortName.setText(shortName);
    }

    public void setTransactionId(String transactionId) {
        tvTransactionId.setText(transactionId);
    }

    public void setTransactionPerformTime(long epochTime) {
        tvTransactionPerformTime.setText(Date.getInstance(epochTime).toString(true));
    }

    public void setTransactionValue(int value) {
        NumberFormat formatter = new DecimalFormat("#,###");
        tvTransactionValue.setText(String.format("- %s", formatter.format(value)));
    }
}
