package com.android.csebrur.blooddonation;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
public class InformationActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information);
        getSupportActionBar().setTitle("Information");
    }
}