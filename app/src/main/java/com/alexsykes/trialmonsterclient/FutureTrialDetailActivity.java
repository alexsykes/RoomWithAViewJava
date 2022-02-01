package com.alexsykes.trialmonsterclient;

import android.os.Bundle;
import android.text.Html;
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
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FutureTrialDetailActivity extends AppCompatActivity
        implements OnMapReadyCallback {
    private String trialid, venue_name;
    private double lat, lon;
    TextView followingTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_future_trial_detail);
        trialid = getIntent().getExtras().getString("trialid");
        lat = getIntent().getDoubleExtra("lat", 0);
        lon = getIntent().getDoubleExtra("lon", 0);
        venue_name = getIntent().getExtras().getString("venue_name");

        followingTextView = findViewById(R.id.followingTextView);

        // Get trial details, then display
        getTrialDetails(trialid);

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void getTrialDetails(String trialid) {
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(getApplication().getBaseContext());
        String url = "https://android.trialmonster.uk/getIntroText.php?id=" + trialid;

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Info", "Response: " + response);
                        followingTextView.setText(Html.fromHtml(response));
                        //  followingTextView.loadD
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