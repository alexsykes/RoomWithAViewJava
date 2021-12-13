package com.alexsykes.roomwithaviewjava;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class ResultListActivity extends AppCompatActivity {
    String trialid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list);
        trialid = getIntent().getExtras().getString("trialid");
        getResults();
    }

    private void getResults() {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplication().getBaseContext());
        String url = "https://android.trialmonster.uk/getTrialResultJSONdata.php?id=" + trialid;

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //  textView.setText("Response is: "+ response.substring(0,500));
                        addResultsToDb(response);
                    }

                    private void addResultsToDb(String response) {

                        Log.i("Info", "addResultsToDb: " + response);
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
}