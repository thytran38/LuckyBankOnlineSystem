package com.example.myfirstapp.luckybankonlinesystem.Class;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.myfirstapp.luckybankonlinesystem.R;


public class AccountOverviewHolder extends RecyclerView.ViewHolder {

    private TextView tvTenthe, tvStk, tvNgaytao, tvType, tvSodu;

    public AccountOverviewHolder(@NonNull View itemView) {
        super(itemView);
        tvTenthe = itemView.findViewById(R.id.tvUserName1);
        tvStk = itemView.findViewById(R.id.tvAccnumber1);
        tvNgaytao = itemView.findViewById(R.id.dateCreated1);
        tvType = itemView.findViewById(R.id.cardType);
        tvSodu = itemView.findViewById(R.id.currentBalanceOnCard);

    }

    public void setTvNgaytao(long epochTime) {
        tvNgaytao.setText(Date.getInstance(epochTime).toString(true));
    }

    public void setCardType(String type){
        tvType.setText(type);
    }

    public void setTvTenthe(String name){
        tvTenthe.setText(name);
    }

    public void setTvStk(long stk){

        tvStk.setText(String.valueOf(stk));
    }

    public void setTvSodu(String sdu){
        tvSodu.setText(String.valueOf(sdu));
    }

}
