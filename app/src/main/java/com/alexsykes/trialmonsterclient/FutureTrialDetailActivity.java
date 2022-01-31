package com.alexsykes.trialmonsterclient;

import android.os.Bundle;
import android.text.Html;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

        introTextView.setText("intro");
        introTextView.setText(Html.fromHtml("        <h1>Main Title</h1>\n" +
                "        <h2>A sub-title</h2>\n" +
                "        <p>This is some html. Look, here\\'s an <u>underline</u>.</p>\n" +
                "        <p>Look, this is <em>emphasized.</em> And here\\'s some <b>bold</b>.</p>\n" +
                "        <p>This is a UL list:\n" +
                "        <ul>\n" +
                "        <li>One</li>\n" +
                "        <li>Two</li>\n" +
                "        <li>Three</li>\n" +
                "        </ul>\n" +
                "        <p>This is an OL list:\n" +
                "        <ol>\n" +
                "        <li>One</li>\n" +
                "        <li>Two</li>\n" +
                "        <li>Three</li>\n" +
                "        </ol>"));
        followingTextView.setText(Html.fromHtml("        <h1>Main Title</h1>\n" +
                "        <h2>A sub-title</h2>\n" +
                "        <p>This is some html. Look, here\\'s an <u>underline</u>.</p>\n" +
                "        <p>Look, this is <em>emphasized.</em> And here\\'s some <b>bold</b>.</p>\n" +
                "        <p>This is a UL list:\n" +
                "        <ul>\n" +
                "        <li>One</li>\n" +
                "        <li>Two</li>\n" +
                "        <li>Three</li>\n" +
                "        </ul>\n" +
                "        <p>This is an OL list:\n" +
                "        <ol>\n" +
                "        <li>One</li>\n" +
                "        <li>Two</li>\n" +
                "        <li>Three</li>\n" +
                "        </ol>"));

        // Get the SupportMapFragment and request notification when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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