package com.alexsykes.roomwithaviewjava;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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
        // Update the cached copy of the words in the adapter.
        trialViewModel.getAllTrials().observe(this, adapter::submitList);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent intent = new Intent(MainActivity.this, NewTrialActivity.class);
            startActivityForResult(intent, NEW_TRIAL_ACTIVITY_REQUEST_CODE);
        });

        getVolley();
    }

    private void getVolley() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplication().getBaseContext());
        String url = "https://android.trialmonster.uk/getAndroidPastTrials.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //  textView.setText("Response is: "+ response.substring(0,500));
                        addTrialsToDb(response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("Info", "That didn't work!");

            }
        });
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    private void addTrialsToDb(String response) {
        Log.i("Info", "addTrialsToDb: " + response);
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

    public void onClickCalled(String id) {
        Log.i("Info", "onClickCalled: " + id);
        Intent intent = new Intent(MainActivity.this, ResultListActivity.class);
        intent.putExtra("trialid", id);
        startActivity(intent);
    }
}