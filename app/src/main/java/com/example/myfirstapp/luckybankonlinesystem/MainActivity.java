package com.example.myfirstapp.luckybankonlinesystem;

import android.content.Intent;
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
import com.google.firebase.firestore.FirebaseFirestore;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import java.util.ArrayList;
import java.util.logging.Logger;

public class MainActivity extends AppCompatActivity implements ChipNavigationBar.OnItemSelectedListener {

    ViewPager viewPager;
    Adapter adapter;
    Integer[] colors = null;
    public static final int nav_main = 1000380;
    public static final int nav_transaction = 1000187;
    public TextView mainTv;

    TextView totalBalanceTv, usernameTv, accNumTv;
    RecyclerView rvTransactionOverview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.getLogger("OK").warning("CREATED MAIN ACTIVITY");
        FirebaseApp.initializeApp(this);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        usernameTv = findViewById(R.id.tvUserName);
        totalBalanceTv = findViewById(R.id.tvTotalTransaction);
        accNumTv = findViewById(R.id.tvAccnumber);
        rvTransactionOverview = findViewById(R.id.rvTransactionOverview);
        ArrayList<ScreenSlidePageFragment> fragmentArrayList = new ArrayList<ScreenSlidePageFragment>();
        CustomerModel model = getIntent().getExtras().getParcelable(SplashScreenActivity.USER_INFO_KEY);
        Intent t = getIntent();
        ArrayList<TransactionModel> transactions = getIntent().getParcelableArrayListExtra(SplashScreenActivity.TRANSACTION_HISTORY_KEY);
        Logger.getLogger("OK").warning(model.getAccounts().get(0).getAccountType().name());
        Logger.getLogger("OK").warning("count = " + transactions.size());
        Logger.getLogger("OK").warning("amount = " + transactions.get(0).getAmount());

        //adapter = new Adapter(fragmentArrayList, this);
        loadFragments(new TransactionFragment());
        ChipNavigationBar chipNavigationBar = findViewById(R.id.menu);
        chipNavigationBar.setMenuOrientation(ChipNavigationBar.MenuOrientation.HORIZONTAL);
        //chipNavigationBar.setMenuResource(R.menu.bottom_menu);
        //chipNavigationBar.setOnItemSelectedListener(navListener);
        chipNavigationBar.setOnItemSelectedListener(navListener);


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
//    private ChipNavigationBar.OnNavigationItemReselectedListener navListener = new BottomNavigationView.OnNavigationItemReselectedListener() {
//        @Override
//        public void onNavigationItemReselected(@NonNull MenuItem item) {
//            Fragment selectedFragment = null;
//
//            switch(item.getItemId()){
//                case R.id.nav_main:
//                    selectedFragment = new MainFragment();
//                    break;
//                case R.id.nav_transaction:
//                    selectedFragment = new TransactionFragment();
//                    break;
//                case R.id.nav_wallet:
//                    selectedFragment = new WalletFragment();
//                    break;
//                case R.id.nav_userinfo:
//                    selectedFragment = new UserInfoFragment();
//                    break;
//                case R.id.nav_currency:
//                    selectedFragment = new CurrencyFragment();
//                    break;
//            }
//
//            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
//                    selectedFragment);
//            //return true;
//        }
//    };

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
