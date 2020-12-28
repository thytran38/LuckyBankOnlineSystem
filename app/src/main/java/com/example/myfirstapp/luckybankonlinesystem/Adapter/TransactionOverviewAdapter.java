package com.example.myfirstapp.luckybankonlinesystem.Adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.luckybankonlinesystem.Class.TransactionOverviewViewHolder;
import com.example.myfirstapp.luckybankonlinesystem.Model.TransactionModel;
import com.example.myfirstapp.luckybankonlinesystem.R;

import java.util.List;

public class TransactionOverviewAdapter extends RecyclerView.Adapter<TransactionOverviewViewHolder> {

    private final List<TransactionModel> dataSource;

    public TransactionOverviewAdapter(List<TransactionModel> dataSource) {
        this.dataSource = dataSource;
    }

    @NonNull
    @Override
    public TransactionOverviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new TransactionOverviewViewHolder(inflater.inflate(R.layout.transaction_overview_line_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionOverviewViewHolder holder, int position) {
        TransactionModel model = dataSource.get(position);
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }
}
