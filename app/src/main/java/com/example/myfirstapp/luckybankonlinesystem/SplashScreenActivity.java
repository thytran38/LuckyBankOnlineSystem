package com.example.myfirstapp.luckybankonlinesystem;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.myfirstapp.luckybankonlinesystem.Model.CustomerModel;
import com.example.myfirstapp.luckybankonlinesystem.Model.TransactionModel;
import com.example.myfirstapp.luckybankonlinesystem.Service.FetchingDataService;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.logging.Logger;

public class SplashScreenActivity extends AppCompatActivity {

    private BroadcastReceiver receiver;
    private Intent intent;

    private enum TaskCompleteState {
        UserInfoCompleted, TransactionCompleted, BothCompleted, BothUncompleted;
    }

    private TaskCompleteState state;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);

        state = TaskCompleteState.BothUncompleted;

        intent = new Intent(this, MainActivity.class);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(FetchingDataService.INTENT_KEY + "." +
                        FetchingDataService.USER_INFO_KEY)) {
                    CustomerModel userInfo = intent.getExtras().getParcelable(FetchingDataService.USER_INFO_KEY);
                    SplashScreenActivity.this.intent.putExtra(FetchingDataService.USER_INFO_KEY, userInfo);
                    if (state == TaskCompleteState.TransactionCompleted) {
                        Logger.getLogger("DEBUG").warning("Completed both");
                        state = TaskCompleteState.BothCompleted;
                        startActivity(SplashScreenActivity.this.intent);
                    } else if (state == TaskCompleteState.BothUncompleted) {
                        state = TaskCompleteState.UserInfoCompleted;
                    }
                } else if (intent.getAction().equals(FetchingDataService.INTENT_KEY + "." +
                        FetchingDataService.TRANSACTION_HISTORY_KEY)) {
                    ArrayList<TransactionModel> transactions = intent.getParcelableArrayListExtra(FetchingDataService.TRANSACTION_HISTORY_KEY);
                    SplashScreenActivity.this.intent.putParcelableArrayListExtra(FetchingDataService.TRANSACTION_HISTORY_KEY, transactions);
                    if (state == TaskCompleteState.UserInfoCompleted) {
                        state = TaskCompleteState.BothCompleted;
                        startActivity(SplashScreenActivity.this.intent);
                    } else if (state == TaskCompleteState.BothUncompleted) {
                        state = TaskCompleteState.TransactionCompleted;
                    }
                }
            }
        };

        if (user == null) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            startService(new Intent(this, FetchingDataService.class)
                    .putExtra(FetchingDataService.SEND_USER_UID_KEY, user.getUid()));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(FetchingDataService.INTENT_KEY + "." + FetchingDataService.USER_INFO_KEY);
        filter.addAction(FetchingDataService.INTENT_KEY + "." + FetchingDataService.TRANSACTION_HISTORY_KEY);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }
}