package com.example.myfirstapp.luckybankonlinesystem.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.myfirstapp.luckybankonlinesystem.Adapter.CardAdapter;
import com.example.myfirstapp.luckybankonlinesystem.Class.DepthZoomOutPageTransformer;
import com.example.myfirstapp.luckybankonlinesystem.R;

/**
 * A simple {@link WalletFragment} subclass.
 * Use the {@link WalletFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WalletFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String uID;
    private View v;
    private TextView nameTv, accnumTv;
    private String USER_NAME, ACCOUNT_NUMBER;
    private TextView detailTv, numAcc;
    private EditText numAcc2;
    private ViewPager2 viewPager2;


    public WalletFragment() {
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
    public static WalletFragment newInstance(String param1, String param2) {
        WalletFragment fragment = new WalletFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.wallet_fragment, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        this.v = view;
        init();
        Fragment fm = new PrimaryCardFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_wallet, fm).commit();

        viewPager2 = v.findViewById(R.id.viewPager_2);
        viewPager2.setCurrentItem(R.layout.primary_card_view);
        viewPager2.setAdapter(new CardAdapter(getActivity()));
        viewPager2.setPageTransformer(new DepthZoomOutPageTransformer());
    }


    public void init() {

    }
}