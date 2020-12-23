package com.example.myfirstapp.luckybankonlinesystem;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.myfirstapp.luckybankonlinesystem.Fragment.CurrencyFragment;
import com.example.myfirstapp.luckybankonlinesystem.Fragment.MainFragment;
import com.example.myfirstapp.luckybankonlinesystem.Fragment.ScreenSlidePageFragment;
import com.example.myfirstapp.luckybankonlinesystem.Fragment.TransactionFragment;
import com.example.myfirstapp.luckybankonlinesystem.Fragment.UserInfoFragment;
import com.example.myfirstapp.luckybankonlinesystem.Fragment.WalletFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
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

        BottomNavigationView bottomNavigationView = findViewById(R.id.menu);
        bottomNavigationView.setOnNavigationItemReselectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainFragment()).commit();


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

    private BottomNavigationView.OnNavigationItemReselectedListener navListener = new BottomNavigationView.OnNavigationItemReselectedListener() {
        @Override
        public void onNavigationItemReselected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch(item.getItemId()){
                case R.id.nav_main:
                    selectedFragment = new MainFragment();
                    break;
                case R.id.nav_transaction:
                    selectedFragment = new TransactionFragment();
                    break;
                case R.id.nav_wallet:
                    selectedFragment = new WalletFragment();
                    break;
                case R.id.nav_userinfo:
                    selectedFragment = new UserInfoFragment();
                    break;
                case R.id.nav_currency:
                    selectedFragment = new CurrencyFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment);
            //return true;
        }
    };

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
