package com.alexsykes.roomwithaviewjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {
    private TrialViewModel trialViewModel;
    public static final int NEW_TRIAL_ACTIVITY_REQUEST_CODE = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final TrialListAdapter adapter = new TrialListAdapter(new TrialListAdapter.TrialDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        trialViewModel = new ViewModelProvider(this).get(TrialViewModel.class);
        trialViewModel.getAllTrials().observe(this, trials -> {
            // Update the cached copy of the words in the adapter.
             adapter.submitList(trials);
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener( view -> {
            Intent intent = new Intent(MainActivity.this, NewTrialActivity.class);
            startActivityForResult(intent, NEW_TRIAL_ACTIVITY_REQUEST_CODE);
        });

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == NEW_TRIAL_ACTIVITY_REQUEST_CODE && resultCode == RESULT_OK) {
            String name = data.getStringExtra("name");
            String club = data.getStringExtra("club");
            String date = data.getStringExtra("date");
            String location = data.getStringExtra("location");
            Trial trial = new Trial(name, club, date, location);
            trialViewModel.insert(trial);
        } else {
            Toast.makeText(
                    getApplicationContext(),
                    R.string.empty_not_saved,
                    Toast.LENGTH_LONG).show();
        }
    }

}