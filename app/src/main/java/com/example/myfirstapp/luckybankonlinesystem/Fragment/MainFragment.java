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
import androidx.viewpager2.widget.ViewPager2;

import com.example.myfirstapp.luckybankonlinesystem.Adapter.CardAdapter;
import com.example.myfirstapp.luckybankonlinesystem.Adapter.TransactionOverviewAdapter;
import com.example.myfirstapp.luckybankonlinesystem.Class.DepthZoomOutPageTransformer;
import com.example.myfirstapp.luckybankonlinesystem.Model.CustomerModel;
import com.example.myfirstapp.luckybankonlinesystem.Model.TransactionModel;
import com.example.myfirstapp.luckybankonlinesystem.R;
import com.example.myfirstapp.luckybankonlinesystem.Service.FetchingDataService;
import com.google.firebase.firestore.FirebaseFirestore;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;


/**
 * A simple {@link MainFragment} subclass.
 * Use the {@link MainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainFragment extends Fragment {

    private ViewPager2 viewPager2;
    private View v;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public MainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MainFragment newInstance(String param1, String param2) {
        MainFragment fragment = new MainFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


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
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.main_fragment, container, false);

        return v;

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

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        CustomerModel userInfo = Objects.requireNonNull(getActivity()).getIntent().getExtras().getParcelable(FetchingDataService.USER_INFO_KEY);
        ArrayList<TransactionModel> transactions = getActivity().getIntent().getParcelableArrayListExtra(FetchingDataService.TRANSACTION_HISTORY_KEY);

        hiTv = Objects.requireNonNull(getView()).findViewById(R.id.tvHi);
        rvTransactionOverview = getView().findViewById(R.id.rvTransactionOverview);
        totalTransactionTv = getView().findViewById(R.id.tvTotalAcc);

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

        viewPager2 = v.findViewById(R.id.viewPager);
        viewPager2.setCurrentItem(R.layout.primary_card_view);
        viewPager2.setAdapter(new CardAdapter(getActivity()));
        viewPager2.setPageTransformer(new DepthZoomOutPageTransformer());
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


    }
}