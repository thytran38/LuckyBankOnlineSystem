package com.example.myfirstapp.luckybankonlinesystem.Adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.myfirstapp.luckybankonlinesystem.Fragment.PrimaryAccFragment;
import com.example.myfirstapp.luckybankonlinesystem.Fragment.SavingAccFragment;

public class AccountAdapter extends FragmentStateAdapter {
    private View v;
    private final Fragment[] fragments = new Fragment[]{
            new PrimaryAccFragment(),
            new SavingAccFragment()
    };


    public AccountAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }


    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position)
        {
            case 0:
                return new PrimaryAccFragment();
            case 1:
                return new SavingAccFragment();
        }
        return fragments[position];
    }

    @Override
    public int getItemCount() {
        return fragments.length;
    }

    @Override
    public long getItemId(int position)
    {
        return super.getItemId(position);
    }
}
