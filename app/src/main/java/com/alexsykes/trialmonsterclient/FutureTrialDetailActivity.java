package com.alexsykes.trialmonsterclient;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FutureTrialDetailActivity extends AppCompatActivity
        implements OnMapReadyCallback {
    private String trialid, venue_name;
    private double lat, lon;
    TextView introTextView;
    TextView followingTextView;
    MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_trial_detail);
        trialid = getIntent().getExtras().getString("trialid");
        lat = getIntent().getDoubleExtra("lat", 0);
        lon = getIntent().getDoubleExtra("lon", 0);
        venue_name = getIntent().getExtras().getString("venue_name");


        introTextView = findViewById(R.id.introTextView);
        followingTextView = findViewById(R.id.followingTextView);

        getTrialDetails(trialid);

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void getTrialDetails(String trialid) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplication().getBaseContext());
        String url = "https://android.trialmonster.uk/getFutureTrialDetailAndroid.php?id=" + trialid;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Info", "Response: " + response);
//                        try {
//                            theTrialList = getTrialList(response);
//                            rv = findViewById(R.id.rv);
//                            llm = new LinearLayoutManager(rv.getContext());
//                            rv.setLayoutManager(llm);
//                            rv.setHasFixedSize(true);
//                            initialiseAdapter();
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
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

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng location = new LatLng(lat, lon);
        googleMap.addMarker(new MarkerOptions()
                .title(venue_name)
                .position(location));

        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 12));

    }
}