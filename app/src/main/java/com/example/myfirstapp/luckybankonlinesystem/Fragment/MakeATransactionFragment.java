package com.example.myfirstapp.luckybankonlinesystem.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.myfirstapp.luckybankonlinesystem.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MakeATransactionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MakeATransactionFragment extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText fullName, reciId, amount, message;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View v;
//    public OnInputSelected mOnInputSelected;


    public MakeATransactionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MakeATransactionFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MakeATransactionFragment newInstance(String param1, String param2) {
        MakeATransactionFragment fragment = new MakeATransactionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
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
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            //mOnInputSelected = (OnInputSelected) getTargetFragment();
        }catch (ClassCastException e){
            //Log.e(TAG, "onAttach: ClassCastException : " + e.getMessage() );
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.make_a_transaction_fragment, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        this.v = view;

        // Get field from view
        fullName = (EditText) v.findViewById(R.id.etFullname);
        reciId = (EditText)v.findViewById(R.id.etRecipientID);
        amount = (EditText)v.findViewById(R.id.etAmount);
        message = (EditText)v.findViewById(R.id.etMessage);
        // Fetch arguments from bundle and set title
        String title = getArguments().getString("title", "Enter Name");
        getDialog().setTitle(title);
        // Show soft keyboard automatically and request focus to field
        fullName.requestFocus();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }


}