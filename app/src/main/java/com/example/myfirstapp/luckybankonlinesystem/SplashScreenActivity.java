package com.example.myfirstapp.luckybankonlinesystem;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.luckybankonlinesystem.Model.CustomerModel;
import com.example.myfirstapp.luckybankonlinesystem.Model.TransactionModel;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

public class SplashScreenActivity extends AppCompatActivity {

    public static final String USER_INFO_KEY = "UserInfo";
    public static final String TRANSACTION_HISTORY_KEY = "Transactions";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_screen);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            Logger.getLogger("DEBUG").warning("user is null");
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            Task<DocumentSnapshot> fetchUserInfoTask = db.collection("users")
                    .document(user.getUid())
                    .get();
            Task<QuerySnapshot> fetchTransactionHistoryTask = db.collection("transactions")
                    .whereEqualTo("senderUID", user.getUid())
                    .get();
            Logger.getLogger("OK").warning("UID = " + user.getUid());
            Tasks.whenAllComplete(fetchUserInfoTask, fetchTransactionHistoryTask).addOnCompleteListener(task -> {
                List<Task<?>> results = task.getResult();
                Intent intent = new Intent(this, MainActivity.class);
                assert results != null;
                DocumentSnapshot userInfoSnapshot = (DocumentSnapshot) results.get(0).getResult();
                QuerySnapshot transactionHistorySnapshot = (QuerySnapshot) results.get(1).getResult();
                assert userInfoSnapshot != null && transactionHistorySnapshot != null;
                CustomerModel userInfo = userInfoSnapshot.toObject(CustomerModel.class);
                ArrayList<TransactionModel> list = new ArrayList<>();
                for (DocumentSnapshot transactionSnapshot : transactionHistorySnapshot.getDocuments()) {
                    Logger.getLogger("OK").warning("Added 1 item to list");
                    list.add(transactionSnapshot.toObject(TransactionModel.class));
                }
                intent.putExtra(USER_INFO_KEY, userInfo);
                intent.putParcelableArrayListExtra(TRANSACTION_HISTORY_KEY, list);
                startActivity(intent);
            });
        }
    }

}
