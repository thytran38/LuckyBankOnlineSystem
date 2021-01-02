package com.example.myfirstapp.luckybankonlinesystem.Fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.myfirstapp.luckybankonlinesystem.Class.Date;
import com.example.myfirstapp.luckybankonlinesystem.Model.CustomerModel;
import com.example.myfirstapp.luckybankonlinesystem.Model.TransactionModel;
import com.example.myfirstapp.luckybankonlinesystem.R;
import com.example.myfirstapp.luckybankonlinesystem.Service.FetchingDataService;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.SignInMethodQueryResult;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Locale;
import java.util.Objects;

public class NewTransactionDialog extends DialogFragment {
    private EditText etRecipientName, etRecipientID, etAmount, etMessage;

    private View v;

    private double realtimeBalance;

    private TextView tvCurrentBalance;

    private final BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            CustomerModel userInfo = intent.getParcelableExtra(FetchingDataService.USER_INFO_KEY);
            realtimeBalance = userInfo.getAccounts().get(0).getCurrentBalance();
            updateCurrentBalance(realtimeBalance);
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_make_a_transaction, container, false);
        return v;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        this.v = view;

        etRecipientName = v.findViewById(R.id.etBeneficiary);
        etRecipientID = v.findViewById(R.id.etBeneAccount);
        etAmount = v.findViewById(R.id.etTransAmount);
        etMessage = v.findViewById(R.id.etTransMes);
        tvCurrentBalance = v.findViewById(R.id.tvCurrentBalance);
//
//        putObjectToHashMap();
        getDialog().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        CustomerModel userInfo = getActivity().getIntent().getParcelableExtra(FetchingDataService.USER_INFO_KEY);
        realtimeBalance = userInfo.getAccounts().get(0).getCurrentBalance();
        updateCurrentBalance(realtimeBalance);

        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = fAuth.getCurrentUser();

        v.findViewById(R.id.btnTransaction_Transfer)
                .setOnClickListener(i -> {
                    if (validateFields()) {
                        long targetAccount = Long.parseLong(etRecipientID.getText().toString());
                        String targetName = etRecipientName.getText().toString();
                        double amount = Double.parseDouble(etAmount.getText().toString());
                        String message = etMessage.getText().toString();
                        long dateCreated = Date.getInstance().getEpochSecond();
                        if (realtimeBalance - amount <= 50000) {
                            Toast.makeText(getContext(), "Your current balance is not enough", Toast.LENGTH_LONG).show();
                            return;
                        }
                        FirebaseFirestore db = FirebaseFirestore.getInstance();
                        Task<QuerySnapshot> transferTask = db.collection("users")
                                .get()
                                .addOnCompleteListener(task -> {
                                    if (task.isSuccessful()) {
                                        QuerySnapshot snapshot = task.getResult();
                                        assert snapshot != null;
                                        for (DocumentSnapshot data : snapshot.getDocuments()) {
                                            CustomerModel targetUser = data.toObject(CustomerModel.class);
                                            assert targetUser != null;
                                            targetUser.setCustomerId(data.getId());
                                            if (targetUser.getAccounts().get(0).getDateCreated() == targetAccount) {
                                                fAuth.fetchSignInMethodsForEmail(targetName)
                                                        .addOnCompleteListener(task1 -> {
                                                            SignInMethodQueryResult result = task1.getResult();
                                                            if (result.getSignInMethods().isEmpty()) {
                                                                Toast.makeText(getContext(), "Recipient's email is not existed", Toast.LENGTH_LONG).show();
                                                            } else {
                                                                TransactionModel model = new TransactionModel();
                                                                model.setTimestamp(dateCreated);
                                                                model.setAmount(amount);
                                                                model.setReceiverUID(data.getId());
                                                                model.setReceiverName(targetName);
                                                                assert firebaseUser != null;
                                                                model.setSenderUID(firebaseUser.getUid());
                                                                model.setSenderName(firebaseUser.getEmail());
                                                                model.setMessage(message);
                                                                db.collection("transactions")
                                                                        .document(String.valueOf(model.getTimestamp()))
                                                                        .set(model);
                                                                targetUser.getAccounts().get(0).setCurrentBalance(targetUser.getAccounts().get(0).getCurrentBalance() + amount);
                                                                data.getReference().set(targetUser);
                                                                db.collection("users")
                                                                        .document(firebaseUser.getUid())
                                                                        .get()
                                                                        .addOnSuccessListener(resultOfSubTask -> {
                                                                            CustomerModel sourceUser = resultOfSubTask.toObject(CustomerModel.class);
                                                                            sourceUser.setCustomerId(resultOfSubTask.getId());
                                                                            sourceUser.getAccounts().get(0)
                                                                                    .setCurrentBalance(sourceUser.getAccounts().get(0).getCurrentBalance() - amount);
                                                                            resultOfSubTask.getReference()
                                                                                    .set(sourceUser);
                                                                        });
                                                            }
                                                        });
                                                return;
                                            }
                                        }
                                        Toast.makeText(getContext(), "Recipient's account number is not existed", Toast.LENGTH_LONG).show();
                                    }
                                });
                        WaitingDialog dialog = new WaitingDialog(getContext(), R.raw.loading_animation, transferTask);
                        dialog.show(getActivity().getSupportFragmentManager(), null);
                        dialog.setOnWaitingDialogCompletedListener(() -> {
                            TransferInformationFragment fragment = new TransferInformationFragment(amount,
                                    dateCreated, String.valueOf(dateCreated), targetName, targetAccount, message);
                            fragment.show(getActivity().getSupportFragmentManager(), null);
                        });
                    }
                });
    }

    @Override
    public void onStart() {
        super.onStart();
        IntentFilter filter = new IntentFilter(FetchingDataService.INTENT_KEY + "." + FetchingDataService.USER_INFO_KEY);
        LocalBroadcastManager.getInstance(Objects.requireNonNull(getContext())).registerReceiver(receiver, filter);
    }

    @Override
    public void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(Objects.requireNonNull(getContext())).unregisterReceiver(receiver);
    }

    public boolean validateFields() {
        if (etRecipientID.getText().toString().isEmpty() || etRecipientName.getText().toString().isEmpty() || etAmount.getText().toString().isEmpty() || etMessage.getText().toString().isEmpty()) {
            Toast.makeText(getActivity(), "No empty field.", Toast.LENGTH_SHORT).show();
            return false;
        } else return true;
        // 1609159285
    }

    private void updateCurrentBalance(double currentBalance) {
        tvCurrentBalance.setText(String.format(Locale.US, "%,3d", (int) currentBalance));
    }
}