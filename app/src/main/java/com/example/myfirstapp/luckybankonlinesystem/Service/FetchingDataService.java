package com.example.myfirstapp.luckybankonlinesystem.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.example.myfirstapp.luckybankonlinesystem.Class.Date;
import com.example.myfirstapp.luckybankonlinesystem.Model.CustomerModel;
import com.example.myfirstapp.luckybankonlinesystem.Model.TransactionModel;
import com.example.myfirstapp.luckybankonlinesystem.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.MetadataChanges;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Objects;
import java.util.logging.Logger;

public class FetchingDataService extends Service {

    public static final String INTENT_KEY = "FireStore.Data";
    public static final String USER_INFO_KEY = "UserInfo";
    public static final String TRANSACTION_HISTORY_KEY = "Transactions";
    public static final String SEND_USER_UID_KEY = "USER_UID";
    private static final String CHANNEL_ID = "NewTransactionNotificationChannel";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        String uid = intent.getStringExtra(SEND_USER_UID_KEY);
        FirebaseFirestore db = FirebaseFirestore.getInstance(Objects.requireNonNull(FirebaseApp.initializeApp(this)));
        final long currentTime = Date.getInstance().getEpochSecond();
        new Thread(() -> {
            db.collection("users")
                    .document(uid)
                    .addSnapshotListener(MetadataChanges.INCLUDE, (data, error) -> {
                        if (error == null) {
                            if (data != null && data.exists()) {
                                Intent dataIntent = new Intent(INTENT_KEY + "." + USER_INFO_KEY);
                                CustomerModel userInfo = data.toObject(CustomerModel.class);
                                assert userInfo != null;
                                userInfo.setCustomerId(data.getId());
                                dataIntent.setAction(INTENT_KEY + "." + USER_INFO_KEY).putExtra(USER_INFO_KEY, userInfo);
                                LocalBroadcastManager.getInstance(this).sendBroadcast(dataIntent);
                            }
                        } else {
                            Logger.getLogger("DEBUG").warning("user info error: " + error.getMessage());
                        }
                    });
            db.collection("transactions")
                    .addSnapshotListener(MetadataChanges.INCLUDE, (data, error) -> {
                        if (error == null) {
                            if (data != null) {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                                    int importance = NotificationManager.IMPORTANCE_DEFAULT;
                                    NotificationChannel channel = new NotificationChannel(CHANNEL_ID, "NotificationChannel", importance);
                                    channel.setDescription("Channel to notify new user transaction");
                                    NotificationManager notificationManager = getSystemService(NotificationManager.class);
                                    notificationManager.createNotificationChannel(channel);
                                }
                                Intent dataIntent = new Intent(INTENT_KEY + "." + TRANSACTION_HISTORY_KEY);
                                ArrayList<TransactionModel> transactions = new ArrayList<>();
                                for (DocumentSnapshot snapshot : data.getDocuments()) {
                                    TransactionModel model = snapshot.toObject(TransactionModel.class);
                                    assert model != null;
                                    model.setTransactionID(snapshot.getId());
                                    if (model.getReceiverUID().equals(uid) || model.getSenderUID().equals(uid))
                                        transactions.add(model);
                                    if (model.getReceiverUID().equals(uid) && model.getTimestamp() >= currentTime) {
                                        Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                                                .setSmallIcon(R.drawable.money_icon)
                                                .setContentTitle("New transaction detected (number = " + model.getTransactionID() + ")")
                                                .setContentText(String.format(Locale.US, "You received %,d from %s", (int) model.getAmount(), model.getSenderName()))
                                                .setWhen(System.currentTimeMillis())
                                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                                .build();
                                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
                                        notificationManager.notify((int) Date.getInstance().getEpochSecond(), notification);
                                    }
                                }
                                dataIntent.setAction(INTENT_KEY + "." + TRANSACTION_HISTORY_KEY);
                                dataIntent.putExtra(TRANSACTION_HISTORY_KEY, transactions);
                                LocalBroadcastManager.getInstance(this).sendBroadcast(dataIntent);
                            }
                        } else {
                            Logger.getLogger("DEBUG").warning("transaction error: " + error.getMessage());
                        }
                    });
        }).start();
        return START_NOT_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.getLogger("DEBUG").warning("service killed by system");
    }
}
