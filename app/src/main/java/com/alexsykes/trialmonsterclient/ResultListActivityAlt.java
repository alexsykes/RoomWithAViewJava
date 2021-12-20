package com.alexsykes.trialmonsterclient;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ResultListActivityAlt extends AppCompatActivity {
    String trialid;
    RecyclerView rv;
    private ArrayList<HashMap<String, String>> theResults;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_list_alt);
        trialid = getIntent().getExtras().getString("trialid");
        rv = findViewById(R.id.rv);
        getResultsFromServer();
    }

    private void getResultsFromServer() {
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
                        try {
                            addResultsToDb(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    private void addResultsToDb(String response) throws JSONException {
                        Log.i("Info", "addResultsToDb: " + response);
                        // Parse string data into JSON
                        JSONArray jsonArray = new JSONArray(response);

                        JSONArray theTrial = jsonArray.getJSONObject(0).getJSONArray("trial details");
                        JSONObject trialDetails = theTrial.getJSONObject(0);
                        // displayTrialDetails(trialDetails);

                        // JSONArray courseCount = jsonArray.getJSONObject(1).getJSONArray("entry count");
                        String results = jsonArray.getJSONObject(2).getJSONArray("results").toString();
                        theResults = getResultList(results);
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

    private ArrayList<HashMap<String, String>> getResultList(String json) throws JSONException {

        theResults = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(json);
        String gone = Integer.toString(View.GONE);
        String name;
        for (int index = 0; index < jsonArray.length(); index++) {
            // Create new HashMap
            HashMap<String, String> theResultHash = new HashMap<>();
            name = jsonArray.getJSONObject(index).getString("name");
            Log.i("Name", "getResultList: " + name);
            // ut data from JSON
            theResultHash.put("rider", jsonArray.getJSONObject(index).getString("rider"));
            theResultHash.put("name", jsonArray.getJSONObject(index).getString("name"));
            theResultHash.put("machine", jsonArray.getJSONObject(index).getString("machine"));
            theResultHash.put("total", jsonArray.getJSONObject(index).getString("total"));
            theResultHash.put("class", jsonArray.getJSONObject(index).getString("class"));
            theResultHash.put("course", jsonArray.getJSONObject(index).getString("course"));
            theResultHash.put("cleans", jsonArray.getJSONObject(index).getString("cleans"));
            theResultHash.put("ones", jsonArray.getJSONObject(index).getString("ones"));
            theResultHash.put("twos", jsonArray.getJSONObject(index).getString("twos"));
            theResultHash.put("threes", jsonArray.getJSONObject(index).getString("threes"));
            theResultHash.put("fives", jsonArray.getJSONObject(index).getString("fives"));
            theResultHash.put("missed", jsonArray.getJSONObject(index).getString("missed"));
            theResultHash.put("sectionscores", jsonArray.getJSONObject(index).getString("sectionscores"));
            theResultHash.put("scores", jsonArray.getJSONObject(index).getString("scores"));
            theResultHash.put("dnf", jsonArray.getJSONObject(index).getString("dnf"));
            theResultHash.put("position", "");
            theResultHash.put("index", String.valueOf(index));
            theResultHash.put("visibility", gone);

            // Add to array
            theResults.add(theResultHash);
        }
        // theResults = addPosition(theResults);

        return theResults;
    }
}