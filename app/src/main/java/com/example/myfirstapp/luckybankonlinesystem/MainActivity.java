package com.example.myfirstapp.luckybankonlinesystem;

import android.annotation.SuppressLint;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.myfirstapp.luckybankonlinesystem.Fragment.CurrencyFragment;
import com.example.myfirstapp.luckybankonlinesystem.Fragment.MainFragment;
import com.example.myfirstapp.luckybankonlinesystem.Fragment.TransactionFragment;
import com.example.myfirstapp.luckybankonlinesystem.Fragment.UserInfoFragment;
import com.example.myfirstapp.luckybankonlinesystem.Fragment.WalletFragment;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class MainActivity extends AppCompatActivity implements ChipNavigationBar.OnItemSelectedListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ChipNavigationBar chipNavigationBar = findViewById(R.id.menu);
        chipNavigationBar.setMenuOrientation(ChipNavigationBar.MenuOrientation.HORIZONTAL);
        chipNavigationBar.setOnItemSelectedListener(this);
        chipNavigationBar.setItemSelected(R.id.nav_main, true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onItemSelected(int i) {
        Fragment selectedFragment = null;
        switch (i) {
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
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
    }

//    public boolean loadFragments(Fragment fr) {
//        if (fr != null) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.fragment_container, fr)
//                    .commit();
//        }
//        return true;
//    }
}
