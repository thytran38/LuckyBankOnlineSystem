package com.example.myfirstapp.luckybankonlinesystem.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.luckybankonlinesystem.Class.Date;
import com.example.myfirstapp.luckybankonlinesystem.Model.AccountModel;
import com.example.myfirstapp.luckybankonlinesystem.R;

import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Logger;

public class AccountsAdapter extends RecyclerView.Adapter<AccountsAdapter.ViewHolder> {
    private ArrayList<AccountModel> dataSource;
    private int viewTypeId;
    private String accOwnerName;

    public AccountsAdapter(ArrayList<AccountModel> accountModels, String fullName) {
        this.dataSource = accountModels;
        this.accOwnerName = fullName;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View cardView = null;
        switch (viewTypeId) {
            case 0:
                cardView = (inflater.inflate(R.layout.primary_acc_fragment,parent,false));
                break;
            case 1:
                cardView = (inflater.inflate(R.layout.saving_acc_fragment,parent,false));
                break;
        }

        return new ViewHolder(cardView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        viewTypeId = position;
        AccountModel model = dataSource.get(position);
        AccountModel.AccountType thisType = model.getAccountType();
        if (position == 0) {
            viewTypeId = 1;
        } else {
            viewTypeId = 2;
        }
        try {
            holder.setTvTenthe(this.accOwnerName);
            holder.setTvStk(model.getDateCreated());
            holder.setTvNgaytao(model.getDateCreated());
            holder.setCardType(String.valueOf(thisType));
            holder.setTvSodu(String.format(Locale.US, "%,d", (int) model.getCurrentBalance()));
        } catch (NullPointerException npe) {
            Logger.getLogger("debug000").warning(npe.getMessage());
        }
    }

    public ArrayList<AccountModel> getDataSource() {
        return dataSource;
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTenthe, tvStk, tvNgaytao, tvType, tvSodu;


        public ViewHolder(View itemView) {
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

        public void setCardType(String type) {
            tvType.setText(type);
        }

        public void setTvTenthe(String name) {
            tvTenthe.setText(name);
        }

        public void setTvStk(long stk) {

            tvStk.setText(String.valueOf(stk));
        }

        public void setTvSodu(String sdu) {
            tvSodu.setText(String.valueOf(sdu));
        }

    }

}
