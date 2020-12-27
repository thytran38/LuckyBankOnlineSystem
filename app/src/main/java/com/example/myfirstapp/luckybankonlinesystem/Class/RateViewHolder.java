    package com.example.myfirstapp.luckybankonlinesystem.Class;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.luckybankonlinesystem.R;

public class RateViewHolder extends RecyclerView.ViewHolder {
    TextView tvKey, tvValue;

    public RateViewHolder(@NonNull View itemView) {
        super(itemView);

        tvKey = itemView.findViewById(R.id.tvKey);
        tvValue = itemView.findViewById(R.id.tvValue);
    }

    public void setKey(String key) {
        tvKey.setText(key);
    }

    public void setValue(double value) {
        tvValue.setText(String.valueOf(value));
    }
}
