package com.example.myfirstapp.luckybankonlinesystem;

import android.content.res.Configuration;
import android.os.Bundle;
import android.widget.Adapter;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.myfirstapp.luckybankonlinesystem.Fragment.CurrencyFragment;
import com.example.myfirstapp.luckybankonlinesystem.Fragment.MainFragment;
import com.example.myfirstapp.luckybankonlinesystem.Fragment.ScreenSlidePageFragment;
import com.example.myfirstapp.luckybankonlinesystem.Fragment.TransactionFragment;
import com.example.myfirstapp.luckybankonlinesystem.Fragment.UserInfoFragment;
import com.example.myfirstapp.luckybankonlinesystem.Fragment.WalletFragment;
import com.example.myfirstapp.luckybankonlinesystem.Model.CustomerModel;
import com.example.myfirstapp.luckybankonlinesystem.Model.TransactionModel;
import com.google.firebase.FirebaseApp;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ChipNavigationBar.OnItemSelectedListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseApp.initializeApp(this);

        ArrayList<ScreenSlidePageFragment> fragmentArrayList = new ArrayList<ScreenSlidePageFragment>();

        ChipNavigationBar chipNavigationBar = findViewById(R.id.menu);
        chipNavigationBar.setMenuOrientation(ChipNavigationBar.MenuOrientation.HORIZONTAL);
        //chipNavigationBar.setMenuResource(R.menu.bottom_menu);
        //chipNavigationBar.setOnItemSelectedListener(navListener);
        chipNavigationBar.setOnItemSelectedListener(navListener);
        chipNavigationBar.setItemSelected(R.id.nav_main, true);

        //onItemSelected(nav_transaction);
        //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new MainFragment()).commit();
    }

    private ChipNavigationBar.OnItemSelectedListener navListener = new ChipNavigationBar.OnItemSelectedListener() {

        @Override
        public void onItemSelected(int i) {
            Fragment selectedFragment = null;

            switch (i) {
                case R.id.nav_main:
                    selectedFragment = new MainFragment();
                    loadFragments(selectedFragment);
                    break;
                case R.id.nav_transaction:
                    selectedFragment = new TransactionFragment();
                    loadFragments(selectedFragment);

                    break;
                case R.id.nav_wallet:
                    selectedFragment = new WalletFragment();
                    loadFragments(selectedFragment);

                    //mainTv.setText("wallet");
                    break;
                case R.id.nav_userinfo:
                    selectedFragment = new UserInfoFragment();
                    loadFragments(selectedFragment);

                    //mainTv.setText("info");
                    break;
                case R.id.nav_currency:
                    selectedFragment = new CurrencyFragment();
                    loadFragments(selectedFragment);
                    //mainTv.setText("currency");
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment);
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        int darkFlag = getResources().getConfiguration().uiMode
                & Configuration.UI_MODE_NIGHT_MASK;

        if (darkFlag == Configuration.UI_MODE_NIGHT_YES) {

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();


    }

    @Override
    public void onItemSelected(int i) {
        Fragment selectedFragment = null;

        switch (i) {
            case R.id.nav_main:
                selectedFragment = new MainFragment();
                //mainTv.setText("main");
                break;
            case R.id.nav_transaction:
                selectedFragment = new TransactionFragment();
                //mainTv.setText("trans");
                break;
            case R.id.nav_wallet:
                selectedFragment = new WalletFragment();
                //mainTv.setText("wallet");
                break;
            case R.id.nav_userinfo:
                selectedFragment = new UserInfoFragment();
                //mainTv.setText("info");
                break;
            case R.id.nav_currency:
                selectedFragment = new CurrencyFragment();
                //mainTv.setText("currency");
                break;
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment);
    }

    public boolean loadFragments(Fragment fr) {
        if (fr != null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, fr)
                    .commit();
        }
        return true;
    }
}
