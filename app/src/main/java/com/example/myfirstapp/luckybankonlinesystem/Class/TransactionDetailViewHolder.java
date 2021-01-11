package com.example.myfirstapp.luckybankonlinesystem.Class;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.luckybankonlinesystem.R;

import java.util.Locale;

public class TransactionDetailViewHolder extends RecyclerView.ViewHolder {

    private final TextView tvShortName, tvTransactionId, tvTransactionPerformTime, tvMessageTransaction, tvTransactionValue;

    public TransactionDetailViewHolder(@NonNull View itemView) {
        super(itemView);
        tvShortName = itemView.findViewById(R.id.tvShortName);
        tvTransactionId = itemView.findViewById(R.id.tvTransactionId);
        tvTransactionPerformTime = itemView.findViewById(R.id.tvTransactionPerformTime);
        tvMessageTransaction = itemView.findViewById(R.id.tvMessageTransaction);
        tvTransactionValue = itemView.findViewById(R.id.tvTransactionValue);
    }

    public void setShortName(String email) {
        tvShortName.setText(email.substring(0, 3));
    }

    public void setTransactionId(String transactionId) {
        tvTransactionId.setText(transactionId);
    }

    public void setTransactionPerformTime(long epochSecond) {
        tvTransactionPerformTime.setText(Date.getInstance(epochSecond).toString(true));
    }

    public void setMessageTransaction(String message) {
        tvMessageTransaction.setText(message);
    }

    public void setTransactionValue(double value, boolean isSender) {
        tvTransactionValue.setText(String.format(Locale.US, "%s %,d", isSender ? "+" : "-", (int) value));
    }
}
