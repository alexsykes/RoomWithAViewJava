package com.alexsykes.trialmonsterclient;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

public class FutureTrialDetailActivity extends AppCompatActivity {
    private String trialid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_trial_detail);
        trialid = getIntent().getExtras().getString("trialid");
        Log.i("Info", "trialid: " + trialid);
    }
}