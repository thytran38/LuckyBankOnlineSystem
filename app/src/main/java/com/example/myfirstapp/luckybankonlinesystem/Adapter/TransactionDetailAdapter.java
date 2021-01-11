package com.example.myfirstapp.luckybankonlinesystem.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.luckybankonlinesystem.Class.TransactionDetailViewHolder;
import com.example.myfirstapp.luckybankonlinesystem.Model.TransactionModel;
import com.example.myfirstapp.luckybankonlinesystem.R;

import java.util.ArrayList;

public class TransactionDetailAdapter extends RecyclerView.Adapter<TransactionDetailViewHolder> {

    private final ArrayList<TransactionModel> dataSet;
    private final String currentUserUID;

    public TransactionDetailAdapter(ArrayList<TransactionModel> dataSet, String currentUserUID) {
        this.dataSet = dataSet;
        this.currentUserUID = currentUserUID;
    }

    @NonNull
    @Override
    public TransactionDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new TransactionDetailViewHolder(inflater.inflate(R.layout.transaction_detail_line_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionDetailViewHolder holder, int position) {
        TransactionModel data = dataSet.get(position);
        holder.setTransactionValue(data.getAmount(), data.getSenderUID().equals(currentUserUID));
        holder.setTransactionId(data.getTransactionID());
        holder.setTransactionPerformTime(data.getTimestamp());
        holder.setMessageTransaction(data.getMessage());
        holder.setShortName(data.getSenderName().equals(currentUserUID) ? data.getReceiverName() : data.getSenderName());
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public ArrayList<TransactionModel> getDataSet() {
        return dataSet;
    }
}
