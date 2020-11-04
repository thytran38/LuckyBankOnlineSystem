package com.example.myfirstapp.myapplication;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myfirstapp.myapplication.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }
}