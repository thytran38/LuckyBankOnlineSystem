
package com.example.myfirstapp.luckybankonlinesystem.Fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
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
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.myfirstapp.luckybankonlinesystem.Model.AccountModel;
import com.example.myfirstapp.luckybankonlinesystem.Model.CustomerModel;
import com.example.myfirstapp.luckybankonlinesystem.Model.TransactionModel;
import com.example.myfirstapp.luckybankonlinesystem.R;
import com.example.myfirstapp.luckybankonlinesystem.Service.FetchingDataService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.logging.Logger;



public class NewTransactionDialog extends DialogFragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private EditText eMail, reciId, amount, message;
    private String senderID, partnerID, inputEmail;
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

    CustomerModel customerModel;
    ArrayList<AccountModel> accountModelArrayList;
    AccountModel primAcc;
    String senderUID, receiver_uid;
    private double inputAmount;
    private double currBalance;

    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;

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
        try {
                Logger.getLogger("debug000").warning("onAttach here");
         } catch (ClassCastException e) {
            Log.e("debug000", "onAttach: ClassCastException : " + e.getMessage());
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.v = view;
//
        customerModel = getActivity().getIntent().getExtras().getParcelable(FetchingDataService.USER_INFO_KEY);
        //get some data
        senderUID = firebaseUser.getUid();

        //open dialog
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
//        LocalBroadcastManager.getInstance(context).registerReceiver(receiver, IntentFilter);

        transferBtn = getView().findViewById(R.id.btnTransactionNow);
        transferBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                transactionFormValidate();
                if (reciId.getText().toString().isEmpty() || eMail.getText().toString().isEmpty() || amount.getText().toString().isEmpty() || message.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(), "No empty field.", Toast.LENGTH_SHORT).show();
                    return;
                }
                Logger.getLogger("debug000").warning("clicked");
                Toast.makeText(getActivity(), "Request created.", Toast.LENGTH_SHORT).show();
//                requestNewTransaction();
                if (!transactionFormValidate()) {
                    Toast.makeText(getActivity(), "Check input.", Toast.LENGTH_SHORT).show();
                    try {
                        //doTransact();
                    } catch (NullPointerException npe) {
                        Logger.getLogger("debug000").warning(npe.getMessage().toString());
                    }
//                } else if (doTransact()) {
//
//                    doTransact();
//                }
                }
            }

        });
    }

    public void requestNewTransaction() {
        if (accountExist() && sufficientBalance()) {
            //doTransact();
        }
    }

    public boolean accountExist() {
        boolean f = false;
        db.collection("users").document(partnerAccountNum).collection("accounts").get();

        return f;

    }

    public boolean sufficientBalance() {
        boolean f = false;
        accountModelArrayList = customerModel.getAccounts();
        primAcc = accountModelArrayList.get(0);
        currBalance = primAcc.getCurrentBalance();
        Logger.getLogger("debug000").warning(String.valueOf(currBalance));
        if (currBalance > inputAmount) {
            f = true;
        }
        return f;
    }

    public boolean correctAmount(Double anyAmount) {
        boolean f = false;
        double mod = inputAmount % 500;
        if ((int) mod == 0) {
            f = true;
        }
        return f;
    }

    public boolean transactionFormValidate() {
//        this.customerModel = getActivity().getIntent().getExtras().getParcelable(SplashScreenActivity.USER_INFO_KEY);
        boolean res = false;
        TransactionModel newTransaction = new TransactionModel();

        eMail = (EditText) v.findViewById(R.id.etBeneficiary);
        reciId = (EditText) v.findViewById(R.id.etBeneAccount);
        amount = (EditText) v.findViewById(R.id.etTransAmount);
        message = (EditText) v.findViewById(R.id.etTransMes);

        String reciEmail = eMail.getText().toString();
        partnerAccountNum = reciId.getText().toString();
        String famount = amount.getText().toString();
        String fmessage = message.getText().toString();

        inputAmount = Double.parseDouble(famount);
        if (correctAmount(inputAmount)) {
            Toast.makeText(getActivity(), "Correct amount format", Toast.LENGTH_SHORT).show();
            Logger.getLogger("debug000").warning("valid");
            while (!correctAmount(inputAmount)) {
                Toast.makeText(getActivity(), "input again", Toast.LENGTH_SHORT).show();
                Logger.getLogger("debug000").warning("invalid amount input");
            }
        }

        if (sufficientBalance()) {
            Logger.getLogger("debug000").warning("enough");
            while (!sufficientBalance()) {
                Toast.makeText(getActivity(), "input again", Toast.LENGTH_SHORT).show();
                Logger.getLogger("debug000").warning("insufficient");
            }
        }

        if (accountExist()) {
            Logger.getLogger("debug000").warning("existed");

            while (!accountExist()) {
                Toast.makeText(getActivity(), "input again", Toast.LENGTH_SHORT).show();
                Logger.getLogger("debug000").warning("not existed acc");
            }
        }
        Toast.makeText(getActivity(), reciEmail + partnerAccountNum + famount + fmessage, Toast.LENGTH_SHORT).show();
        Logger.getLogger("debug000").warning(reciEmail + "  " + partnerAccountNum + "  " + famount + "  " + fmessage);
        return res;
    }

    public void updateBalance(){
        if(sufficientBalance()){
            db.collection("users").document(firebaseUser.getUid()).collection("accounts").document("0")
                    .update("currentBalance",currBalance-inputAmount);
        }

        Query getuserByEmail = db.collection("users").whereEqualTo("email",inputEmail);


    }

//    public boolean doTransact() {
//        boolean result = false;
//        TransactionModel newTrans = new TransactionModel();
//        long epochSec = newTrans.getTimestamp();
//        //getDate
//        Logger.getLogger("debug000").warning(String.valueOf(epochSec));
//        //get Timestamp-------------Use epochSec
//        //get senderUID
//        String transSenderUID = senderUID;
//
//        //get receiver UID
//        //put fields to Model
//        newTrans.setTransactionID(String.valueOf(epochSec));
//        newTrans.setTimestamp(epochSec);
//        newTrans.setSenderUID(transSenderUID);
//        newTrans.setReceiverUID(partnerAccountNum);
//        newTrans.setAmount(inputAmount);
//        newTrans.setSenderName(firebaseUser.getEmail());
//        newTrans.setReceiverName(eMail.getText().toString());
//        newTrans.setMessage(message.getText().toString());
//        Logger.getLogger("debug000").warning(String.valueOf(transSenderUID +" " +transSenderUID+"  "+partnerAccountNum+"  "+inputAmount+"  "+message.getText().toString()));
//
//        //
//
//        db.collection("transactions").add(newTrans);
//
//
//
//        return result;
//    }


}

