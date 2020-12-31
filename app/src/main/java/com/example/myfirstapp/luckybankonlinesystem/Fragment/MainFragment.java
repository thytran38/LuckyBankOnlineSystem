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

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myfirstapp.luckybankonlinesystem.Adapter.TransactionOverviewAdapter;
import com.example.myfirstapp.luckybankonlinesystem.Model.CustomerModel;
import com.example.myfirstapp.luckybankonlinesystem.Model.TransactionModel;
import com.example.myfirstapp.luckybankonlinesystem.R;
import com.example.myfirstapp.luckybankonlinesystem.Service.FetchingDataService;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;

public class MainFragment extends Fragment {

    private TextView hiTv, totalTransactionTv;
    private RecyclerView rvTransactionOverview;

    private BroadcastReceiver receiver;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.main_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        CustomerModel userInfo = Objects.requireNonNull(getActivity()).getIntent().getExtras().getParcelable(FetchingDataService.USER_INFO_KEY);
        ArrayList<TransactionModel> transactions = getActivity().getIntent().getParcelableArrayListExtra(FetchingDataService.TRANSACTION_HISTORY_KEY);

        hiTv = Objects.requireNonNull(getView()).findViewById(R.id.tvHi);
        rvTransactionOverview = getView().findViewById(R.id.rvTransactionOverview);
        totalTransactionTv = getView().findViewById(R.id.tvTotalTransaction);

        setCurrentBalance(userInfo.getAccounts().get(0).getCurrentBalance());

        rvTransactionOverview.setLayoutManager(new LinearLayoutManager(getContext()));
        TransactionOverviewAdapter adapter = new TransactionOverviewAdapter(transactions);
        rvTransactionOverview.setAdapter(adapter);

        setHelloString(userInfo.getFullName());

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(FetchingDataService.INTENT_KEY + "." + FetchingDataService.USER_INFO_KEY)) {
                    CustomerModel userInfo = intent.getExtras().getParcelable(FetchingDataService.USER_INFO_KEY);
                    setCurrentBalance(userInfo.getAccounts().get(0).getCurrentBalance());
                } else if (intent.getAction().equals(FetchingDataService.INTENT_KEY + "." + FetchingDataService.TRANSACTION_HISTORY_KEY)) {
                    ArrayList<TransactionModel> transactions = intent.getParcelableArrayListExtra(FetchingDataService.TRANSACTION_HISTORY_KEY);
                    updateTransactionHistoryList(transactions.get(transactions.size() - 1));
                }
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(FetchingDataService.INTENT_KEY + "." + FetchingDataService.USER_INFO_KEY);
        filter.addAction(FetchingDataService.INTENT_KEY + "." + FetchingDataService.TRANSACTION_HISTORY_KEY);
        LocalBroadcastManager.getInstance(Objects.requireNonNull(getActivity())).registerReceiver(receiver, filter);
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(Objects.requireNonNull(getActivity())).unregisterReceiver(receiver);
    }

    private void setHelloString(String name) {
        String yourTotal = getString(R.string.total_balance);
        String hello = "Hi " + name + "\n" + yourTotal;
        hiTv.setText(hello);
    }

    private void setCurrentBalance(double currentBalance) {
        totalTransactionTv.setText(String.format(Locale.US, "%,d", (int) currentBalance));
    }

    private void updateTransactionHistoryList(TransactionModel lastTransaction) {
        TransactionOverviewAdapter adapter = (TransactionOverviewAdapter) rvTransactionOverview.getAdapter();
        assert adapter != null;
        adapter.getDataSource().add(lastTransaction);
        adapter.notifyItemInserted(adapter.getItemCount() - 1);
    }
}