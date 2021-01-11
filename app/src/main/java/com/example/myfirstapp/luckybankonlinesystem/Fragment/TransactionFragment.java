package com.example.myfirstapp.luckybankonlinesystem.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.luckybankonlinesystem.Adapter.TransactionDetailAdapter;
import com.example.myfirstapp.luckybankonlinesystem.Model.CustomerModel;
import com.example.myfirstapp.luckybankonlinesystem.Model.TransactionModel;
import com.example.myfirstapp.luckybankonlinesystem.R;
import com.example.myfirstapp.luckybankonlinesystem.Service.FetchingDataService;

import java.util.ArrayList;
import java.util.logging.Logger;

public class TransactionFragment extends Fragment {

    private RecyclerView rvTransactionDetail;
    private String currentUserUID;
    private TextView tvTotalAcc;

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Logger.getLogger("DEBUG").warning("Get new data in transaction fragment");
            ArrayList<TransactionModel> transactions = intent.getParcelableArrayListExtra(FetchingDataService.TRANSACTION_HISTORY_KEY);
            updateTransactionHistoryList(transactions);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.transaction_fragment, container, false);
        view.findViewById(R.id.btnTrans).setOnClickListener(v -> {
            NewTransactionDialog mkaFragment = new NewTransactionDialog();
            mkaFragment.show(getFragmentManager(), "This new Fragment");
        });
        tvTotalAcc = view.findViewById(R.id.tvTotalAcc);
        rvTransactionDetail = view.findViewById(R.id.rvTransactionDetail);
        rvTransactionDetail.setLayoutManager(new LinearLayoutManager(getContext()));

        CustomerModel userInfo = getActivity().getIntent().getParcelableExtra(FetchingDataService.USER_INFO_KEY);
        ArrayList<TransactionModel> transactions = getActivity().getIntent().getParcelableArrayListExtra(FetchingDataService.TRANSACTION_HISTORY_KEY);
        tvTotalAcc.setText(String.valueOf(transactions.size()));
        currentUserUID = userInfo.getCustomerId();
        updateTransactionHistoryList(transactions);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(FetchingDataService.INTENT_KEY + "." + FetchingDataService.TRANSACTION_HISTORY_KEY);
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(receiver, filter);
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(receiver);
    }

    private void updateTransactionHistoryList(ArrayList<TransactionModel> transactions) {
        tvTotalAcc.setText(String.valueOf(transactions.size()));
        rvTransactionDetail.setAdapter(new TransactionDetailAdapter(transactions, currentUserUID));
    }
}
