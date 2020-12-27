package com.example.myfirstapp.luckybankonlinesystem.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.luckybankonlinesystem.Class.RateViewHolder;
import com.example.myfirstapp.luckybankonlinesystem.CurrencyConverterActivity;
import com.example.myfirstapp.luckybankonlinesystem.Model.RateListModel;
import com.example.myfirstapp.luckybankonlinesystem.R;
import com.google.common.util.concurrent.AtomicDouble;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.logging.Logger;

public class RateAdapter extends RecyclerView.Adapter<RateViewHolder> {

    String[] currencies;
    Context context;
    RateListModel model;
    Double value;
    String key;

    public RateAdapter(Context context, String jsonObj) throws JSONException {

        currencies = context.getResources().getStringArray(R.array.currencies);
        model = new RateListModel(jsonObj, context);
        this.context = context;
    }

    @NonNull
    @Override
    public RateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.rate_row_item, parent, false);
        return new RateViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RateViewHolder holder, int position) {
        holder.setKey(currencies[position]);
        holder.itemView.setOnClickListener(v -> {
            value = model.getValue(currencies[position]);
            key = currencies[position];
        });
        holder.setValue(model.getValue(currencies[position]));
    }

    @Override
    public int getItemCount() {
        return currencies.length;
    }

    public double getSelectedValue() throws IllegalAccessException {
        if (value == null)
            throw new IllegalAccessException("Please choose a rate");
        return value;
    }

    public String getKey() throws IllegalAccessException {
        if (key == null)
            throw new IllegalAccessException("Please choose a rate");
        return key;
    }
}
