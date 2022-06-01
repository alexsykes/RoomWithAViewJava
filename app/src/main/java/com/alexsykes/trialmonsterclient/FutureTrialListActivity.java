package com.alexsykes.trialmonsterclient;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;

public class FutureTrialListActivity extends AppCompatActivity {
    ArrayList<HashMap<String, String>> theTrialList;
    boolean canConnect;
    RecyclerView rv;
    LinearLayoutManager llm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_trial_list);

        canConnect = canConnect();
        if (canConnect) {
            getTrialsList();
        } else {
            showDialog();
        }

        RecyclerView recyclerView = findViewById(R.id.rv);
    }

    private void initialiseAdapter() {
        FutureTrialListAdapter adapter = new FutureTrialListAdapter(theTrialList);
        rv.setAdapter(adapter);
    }

    private void getTrialsList() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplication().getBaseContext());
        String url = "https://android.trialmonster.uk/getFutureTrialListAndroid.php";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    //  Log.i("Info", "Response: " + response);
                    try {
                        theTrialList = getTrialList(response);
                        rv = findViewById(R.id.rv);
                        llm = new LinearLayoutManager(rv.getContext());
                        rv.setLayoutManager(llm);
                        rv.setHasFixedSize(true);
                        initialiseAdapter();

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> Log.i("Info", "That didn't work!"));
// Add the request to the RequestQueue.
        queue.add(stringRequest);
    }


    private ArrayList<HashMap<String, String>> getTrialList(String json) throws JSONException {
        theTrialList = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(json);

        for (int index = 0; index < jsonArray.length(); index++) {
            HashMap<String, String> theTrialHash = new HashMap<>();
            String id = jsonArray.getJSONObject(index).getString("id");
            String name = jsonArray.getJSONObject(index).getString("name");
            String date = jsonArray.getJSONObject(index).getString("date");
            String club = jsonArray.getJSONObject(index).getString("club");
            String lat = jsonArray.getJSONObject(index).getString("lat");
            String lon = jsonArray.getJSONObject(index).getString("lon");
            String venue_name = jsonArray.getJSONObject(index).getString("venue_name");

            theTrialHash.put("id", id);
            theTrialHash.put("name", name);
            theTrialHash.put("date", date);
            theTrialHash.put("club", club);
            theTrialHash.put("lat", lat);
            theTrialHash.put("lon", lon);
            theTrialHash.put("venue_name", venue_name);
            theTrialList.add(theTrialHash);
        }
        return theTrialList;
    }

    protected boolean canConnect() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        @SuppressLint("MissingPermission") NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }

    private void showDialog() {
        new AlertDialog.Builder(this)
                .setCancelable(true)
                .setTitle("No Connection")
                .setMessage("TrialMonster needs an internet connection to work")
                .setPositiveButton("OK", (dialog, which) -> {
                    // Continue with delete operation
                    dialog.cancel();
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
    }

    public void onClickCalled(String id, String lat, String lon, String venue_name) {
        Intent intent = new Intent(this, FutureTrialActivity.class);
        intent.putExtra("trialid", id);
        intent.putExtra("lat", Double.valueOf(lat));
        intent.putExtra("lon", Double.valueOf(lon));
        intent.putExtra("venue_name", venue_name);
        startActivity(intent);
    }
}