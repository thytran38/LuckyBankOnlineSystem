package com.example.myfirstapp.luckybankonlinesystem.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.luckybankonlinesystem.Class.RateViewHolder;
import com.example.myfirstapp.luckybankonlinesystem.Model.RateListModel;
import com.example.myfirstapp.luckybankonlinesystem.R;

import org.json.JSONException;

import java.util.ArrayList;

public class RateAdapter extends RecyclerView.Adapter<RateViewHolder> {

    private final String[] currencies;
    private final Context context;
    private final RateListModel model;
    private Double value;
    private String key;
    private int oldColorCode;
    private int index;

    public RateAdapter(Context context, String jsonObj) throws JSONException {
        currencies = context.getResources().getStringArray(R.array.currencies);
        model = new RateListModel(jsonObj, context);
        this.context = context;
        index = -1;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public RateViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(context).inflate(R.layout.rate_row_item, parent, false);
        final RateViewHolder viewHolder = new RateViewHolder(view);
        oldColorCode = viewHolder.getKeyField().getCurrentTextColor();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RateViewHolder holder, int position) {
        holder.getKeyField().setText(currencies[position]);
        holder.itemView.setOnClickListener(v -> {
            index = position;
            value = model.getValue(currencies[position]);
            key = currencies[position];
            notifyDataSetChanged();
        });
        if (index == position) {
            markAsSelected(holder);
        } else {
            restoreDefault(holder, oldColorCode);
        }
        holder.getValueField().setText(String.valueOf(model.getValue(currencies[position])));
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

    private void markAsSelected(RateViewHolder holder) {
        holder.getKeyField().setTypeface(null, Typeface.BOLD);
        holder.getKeyField().setTextColor(Color.rgb(0,0,0));
        holder.getValueField().setTypeface(null, Typeface.BOLD);
        holder.getValueField().setTextColor(Color.rgb(0,0,0));
    }

    private void restoreDefault(RateViewHolder holder, int colorCode) {
        holder.getKeyField().setTypeface(null, Typeface.NORMAL);
        holder.getKeyField().setTextColor(colorCode);
        holder.getValueField().setTypeface(null, Typeface.NORMAL);
        holder.getValueField().setTextColor(colorCode);
    }
}
