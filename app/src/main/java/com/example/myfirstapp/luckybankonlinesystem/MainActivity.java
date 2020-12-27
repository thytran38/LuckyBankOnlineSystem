package com.example.myfirstapp.luckybankonlinesystem;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.myfirstapp.luckybankonlinesystem.Fragment.ScreenSlidePageFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    ViewPager viewPager;
    Adapter adapter;
    Integer[] colors = null;

    TextView totalBalanceTv, usernameTv, accNumTv;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    RecyclerView rvTransactionOverview;
    FirebaseAuth auth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameTv = findViewById(R.id.tvUserName);
        totalBalanceTv = findViewById(R.id.tvTotalBalance);
        accNumTv = findViewById(R.id.tvAccnumber);
        rvTransactionOverview = findViewById(R.id.rvTransactionOverview);
        auth = FirebaseAuth.getInstance();
        ArrayList<ScreenSlidePageFragment> fragmentArrayList = new ArrayList<ScreenSlidePageFragment>();
        //adapter = new Adapter(fragmentArrayList, this);

        FirebaseUser user = auth.getCurrentUser();
        if (user == null) {
            startActivity(new Intent(this, LoginActivity.class));
        } else {
            db.collection("transactions")
                    .whereEqualTo("sender_UID", user.getUid())
                    .get()
                    .addOnSuccessListener(task -> {
                        for (DocumentSnapshot snapshot : task.getDocuments()) {

                        }
                    })
                    .addOnFailureListener(failureTask -> {

                    });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        int darkFlag = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;

        if(darkFlag == Configuration.UI_MODE_NIGHT_YES){

        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        return false;
    }
}
