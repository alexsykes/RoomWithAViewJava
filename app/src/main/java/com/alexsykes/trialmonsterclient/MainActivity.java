package com.alexsykes.trialmonsterclient;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
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

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private TrialViewModel trialViewModel;
    public static final int NEW_TRIAL_ACTIVITY_REQUEST_CODE = 1;
    ArrayList<HashMap<String, String>> theTrialList;
    boolean canConnect;
    // BottomNavigationView bottomNavigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        canConnect = canConnect();
        if(canConnect) {
            getTrialsList();
        } else {
            showDialog();
        }

        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        final TrialListAdapter adapter = new TrialListAdapter(new TrialListAdapter.TrialDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        trialViewModel = new ViewModelProvider(this).get(TrialViewModel.class);
        // Update the cached copy of the words in the adapter.
        trialViewModel.getAllTrials().observe(this, adapter::submitList);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_show_future:
                goFuture();
                return true;
        }
        return false;
    }

    private void goFuture() {
        Log.i("Info", "goFuture: ");
        Intent intent = new Intent(MainActivity.this, FutureTrialListActivity.class);
        startActivity(intent);
    }


    private void showDialog() {
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle("No Connection")
                .setMessage("TrialMonster needs an internet connection to work")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // Continue with delete operation
                    dialog.cancel();
                }
            })
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();
    }

    private void getTrialsList() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplication().getBaseContext());
        String url = "https://android.trialmonster.uk/getAndroidPastTrials.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
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
        try {
            theTrialList = getTrialList(response);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (int index = 0; index < theTrialList.size(); index++) {
            HashMap<String, String> theTrialHash = theTrialList.get(index);
            String name = theTrialHash.get("name");
            String date = theTrialHash.get("date");
            String club = theTrialHash.get("club");
            int id = Integer.valueOf(theTrialHash.get("id"));
            String location = theTrialHash.get("location");

            // Instantiate and insert Trial into database
            Trial trial = new Trial(id, name, club, date, location);
            trialViewModel.insert(trial);
        }
    }

    public void onClickCalled(String id) {
        // Check for connectivity
        if(canConnect()) {
            Intent intent = new Intent(MainActivity.this, ResultListActivity.class);
            intent.putExtra("trialid", id);
            startActivity(intent);
        } else {
            showDialog();
        }
    }

    private ArrayList<HashMap<String, String>> getTrialList(String json) throws JSONException {
        theTrialList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(json);

        for (int index = 0; index < jsonArray.length(); index++) {
            HashMap<String, String> theTrialHash = new HashMap<>();
            String name = jsonArray.getJSONObject(index).getString("name");
            String date = jsonArray.getJSONObject(index).getString("date");
            String club = jsonArray.getJSONObject(index).getString("club");
            String id = jsonArray.getJSONObject(index).getString("id");
            String location = jsonArray.getJSONObject(index).getString("location");
            theTrialHash.put("name", name);
            theTrialHash.put("date", date);
            theTrialHash.put("id", id);
            theTrialHash.put("club", club);
            theTrialHash.put("location", location);
            theTrialList.add(theTrialHash);
        }
        return theTrialList;
    }

    protected boolean canConnect() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}