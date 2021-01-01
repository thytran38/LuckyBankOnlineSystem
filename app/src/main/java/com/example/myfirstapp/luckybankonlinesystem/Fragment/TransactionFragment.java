package com.example.myfirstapp.luckybankonlinesystem.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.myfirstapp.luckybankonlinesystem.R;

/**
 * A simple {@link TransactionFragment} subclass.
 * Use the {@link TransactionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionFragment extends Fragment {

    private Button transBtn;
    private View v;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public TransactionFragment() {
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
    public static TransactionFragment newInstance(String param1, String param2) {
        TransactionFragment fragment = new TransactionFragment();
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
        v = inflater.inflate(R.layout.transaction_fragment, container, false);
        Button transBtn = (Button) v.findViewById(R.id.btnTrans);
        //transBtn = (Button) getView().findViewById(R.id.btnTrans);

        transBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewTransactionDialog mkaFragment = new NewTransactionDialog();
                mkaFragment.show(getFragmentManager(), "This new Fragment");
                //FragmentManager fm = getSupportManager();
                FragmentManager fragmentManager = getFragmentManager();
                //mkaFragment.show(getSupportFragmentManager(),"this");
                inflater.inflate(R.layout.make_a_transaction_fragment, container, false);

            }
        });
        // Inflate the layout for this fragment
        return v;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        transBtn = (Button) v.findViewById(R.id.btnTrans);

        transBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment thisFragment = new NewTransactionDialog();
                try{
                    thisFragment.show(getChildFragmentManager(),"New Transaction");
                }
                catch(Exception e){
                    Log.d("Exception", e.getCause().toString());
                }
                Log.d("This button","Clicked");
            }
        });
    }
}