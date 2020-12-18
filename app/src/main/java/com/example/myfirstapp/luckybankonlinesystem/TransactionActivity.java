package com.example.myfirstapp.luckybankonlinesystem;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class TransactionActivity extends AppCompatActivity {
    private EditText reciEt, idEt, messageEt, amountEt;
    private Button transBtn, cancBtn;
    private static final String KEY_TRANSACTION_ID = "transactionID";
    private static final String KEY_ACCOUNT_RECEIVER = "receiver";
    private static final String KEY_ACC_SENDER = "sender";
    private static final String KEY_RECEIVER_UID = "receiver UID";
    private static final String KEY_SENDER_UID = "sender UID";
    private static final String KEY_AMOUNT = "amount";
    private static final String KEY_TRANSACTION_TIME = "timestamp of Transaction";
    private static final String KEY_MESSAGE = "message";

    private static String receiver = "";
    private static String sender = "";
    private static String receiverID = "";
    private static String senderID = "";
    private static String amount = "";
    private static String timestamp = "";
    private static String message = "";

    private final FirebaseFirestore db = FirebaseFirestore.getInstance();
    private Boolean existedAccount;
    private String idOfReceiver;
    private String transactionId;

    public TransactionActivity() {
 // DusQGX0wnazH2TMFnkCD
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_a_transaction);

        reciEt = (EditText)findViewById(R.id.etRecipientID);
        messageEt = (EditText)findViewById(R.id.etMessage);
        amountEt = (EditText)findViewById(R.id.etAmount);

//        idOfReceiver = reciEt.getText().toString();
//        receiver = db.collection("users").document(idOfReceiver).get().toString();
//        sender = db.collection("users").document().get().toString();
//        receiverID = idOfReceiver;
//        senderID = "get id from current login session";
//        amount = amountEt.getText().toString();
//        message = messageEt.getText().toString();
//        timestamp = "";
//
//        transactionId = IdGenerator(receiverID,senderID,timestamp);

        idOfReceiver = "" ;
        receiver = "Tran Thi B";
        sender = "Nguyen Van A";
        receiverID = "525jj2nf";
        senderID = "259924hf2h2";
        amount = "2500";
        message = "Thanks";
        timestamp = "03/12/2020 14:16:24";

        transactionId = "2498Jjrw25HH";

        Map<String,Object> transaction = new HashMap<>();
        transaction.put(KEY_TRANSACTION_ID,transactionId);
        transaction.put(KEY_ACC_SENDER,sender);
        transaction.put(KEY_ACCOUNT_RECEIVER,receiver);
        transaction.put(KEY_RECEIVER_UID,receiverID);
        transaction.put(KEY_SENDER_UID,senderID);
        transaction.put(KEY_TRANSACTION_TIME,timestamp);
        transaction.put(KEY_AMOUNT,amount);
        transaction.put(KEY_MESSAGE,message);


        db.collection("transactions").document().set(transaction);



    }

    public String getTimestamp(){
        return "";
    }

    public void writeCheck(String senderId){

        //db.collection("users").document("Xp01Mz36fS2mFnP0Vyqk").document("accounts");
    }

    public void getCheck(String recipientId){

    }

}
