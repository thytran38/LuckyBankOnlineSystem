package com.example.myfirstapp.luckybankonlinesystem;

import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.myfirstapp.luckybankonlinesystem.Fragment.ScreenSlidePageFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    ViewPager viewPager;
    Adapter adapter;
    Integer[] colors = null;

    TextView totalBalanceTv, usernameTv, accNumTv;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        usernameTv = findViewById(R.id.tvUserName);
        totalBalanceTv = findViewById(R.id.tvTotalBalance);
        accNumTv = findViewById(R.id.tvAccnumber);
        ArrayList<ScreenSlidePageFragment> fragmentArrayList = new ArrayList<ScreenSlidePageFragment>();
        //adapter = new Adapter(fragmentArrayList, this);

        db.collection("accounts")
                .whereEqualTo("Fmg3Jc1BqGGBZEmAUoN5", true)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                usernameTv.setText( document.getData().toString());
                            }
                        } else {
                            usernameTv.setText("Not get user info");
                        }
                    }
                });
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
