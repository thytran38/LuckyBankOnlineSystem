package com.example.myfirstapp.luckybankonlinesystem.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.myfirstapp.luckybankonlinesystem.Model.TransactionModel;
import com.example.myfirstapp.luckybankonlinesystem.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewTransactionDialog#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewTransactionDialog extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText fullName, reciId, amount, message;
    private String senderID,partnerID;
    private Button transferBtn;
    private String partnerAccountNum;
    private CollectionReference collectionReference;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    private static final String KEY_PARTNER_ID = "receiverid";
    private static final String KEY_PARTNER_NAME = "receivername";
    private static final String KEY_SENDER_ID = "senderid";
    private static final String KEY_SENDER_NAME = "sendername";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_DATE_CREATED = "datecreated";

    private final CollectionReference transactionRecord = db.collection("transactions");
    private CollectionReference userTransaction;




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private View v;
    private Context context;
//    public OnInputSelected mOnInputSelected;


    public NewTransactionDialog() {
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
    public static NewTransactionDialog newInstance(String param1, String param2) {
        NewTransactionDialog fragment = new NewTransactionDialog();
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
//        IntentFilter filter = new IntentFilter(FetchingDataService.INTENT_KEY + "." + FetchingDataService.USER_INFO_KEY);
//        filter.addAction(FetchingDataService.INTENT_KEY + "." + FetchingDataService.TRANSACTION_HISTORY_KEY);
//        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);

        LocalBroadcastManager lbm = LocalBroadcastManager.getInstance(context);
        lbm.registerReceiver(receiver, new IntentFilter("filter_string"));
    }

    public BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent != null) {
                String str = intent.getStringExtra("key");
                // get all your data from intent and do what you want
            }
        }
    };

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
        v = inflater.inflate(R.layout.fragment_make_a_transaction, container, false);
        return v;
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        context = activity;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        this.v = view;
//        CustomerModel customerModel = getActivity().getIntent().getExtras().getParcelable(SplashScreenActivity.USER_INFO_KEY);
//
//        String senderIDTest = "5252582852852";
//        fullName = (EditText) v.findViewById(R.id.etFullname);
//        reciId = (EditText)v.findViewById(R.id.etAccnum);
//        amount = (EditText)v.findViewById(R.id.etAmount);
//        message = (EditText)v.findViewById(R.id.etMessage);
//
//        putObjectToHashMap();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, IntentFilter);

        transferBtn = (Button)v.findViewById(R.id.btnTransfer);
        transferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "This btn", Toast.LENGTH_SHORT).show();
                Logger.getLogger("debug000").warning("clicked");
                //requestNewTransaction();
            }
        });
    }

    public void requestNewTransaction(){
//        checkAccountValidation();
//        validateFields();
//        if(checkAccountValidation()  && validateFields())
//            {
//            }
        transactionForm();

    }


    public boolean validateFields(){
        if(reciId.getText().toString().isEmpty() || fullName.getText().toString().isEmpty() || amount.getText().toString().isEmpty() || message.getText().toString().isEmpty()){
            Toast.makeText(getActivity(), "No empty field.", Toast.LENGTH_SHORT).show();
            return false;}
        else return true;
    }

    public boolean checkAccountValidation() {
        boolean valid = false;
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        partnerAccountNum = reciId.getText().toString();
        collectionReference = db.collection("user").document("pIKOVzXcWQhu0cNZvlyjxm7x0K12").collection("accounts").document("0").getParent();
        Logger.getLogger("debug000").warning(String.valueOf(collectionReference.getParent().toString()));






        return true;
    }

    public void transactionForm(){
//        this.customerModel = getActivity().getIntent().getExtras().getParcelable(SplashScreenActivity.USER_INFO_KEY);
        TransactionModel newTransaction = new TransactionModel();
        newTransaction.setReceiverUID(reciId.getText().toString());
        newTransaction.setSenderUID("1414144114");
        double newAmount = Double.parseDouble(amount.getText().toString());
        newTransaction.setAmount(newAmount);
        String newMes = message.getText().toString();
        newTransaction.setMessage(newMes);
        Logger.getLogger("debug000").warning("reci" +reciId.getText().toString());
        //Logger.getLogger("debug000").warning("senderId"+customerModel.getCustomerId().toString());
        Logger.getLogger("debug000").warning( "amount"+String.valueOf(newAmount) );

        putObjectToHashMap();

    }

    public void putObjectToHashMap(){
        //Query to Receiver's accounts
        //this.userTransaction = db.collection("users").document(customerModel.getCustomerId()).collection("accounts").document("0").getParent();

        String getUserFromFullnameQuery = db.collection("users").whereEqualTo("fullName","Thy Tran Yen").get().toString();
        Logger.getLogger("debug000").warning(getUserFromFullnameQuery);
        Logger.getLogger("debug000").warning(userTransaction.whereEqualTo("email","ax4409h@gmail.com").toString());
        //DocumentReference = db.collection("users").document("IKOVzXcWQhu0cNZvlyjxm7x0K12").collection("transactions").document("0");
        Map<String, Object> note = new HashMap<>();






    }


}