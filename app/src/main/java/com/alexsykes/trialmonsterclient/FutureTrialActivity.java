package com.alexsykes.trialmonsterclient;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class FutureTrialActivity extends AppCompatActivity implements OnMapReadyCallback {

    private String trialid, venue_name, courses, classes;
    private double lat, lon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        trialid = getIntent().getExtras().getString("trialid");
        lat = getIntent().getDoubleExtra("lat", 0);
        lon = getIntent().getDoubleExtra("lon", 0);
        courses = getIntent().getStringExtra("courselist");
        classes = getIntent().getStringExtra("classlist");
        venue_name = getIntent().getExtras().getString("venue_name");
        setContentView(R.layout.activity_web_view);
        WebView webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // webView.loadUrl("https://android.trialmonster.uk/getTrialDetail.php?id=" + trialid);
        webView.loadUrl("https://android.trialmonster.uk/getTrialDetailNew.php?id=" + trialid);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.mapView);
        mapFragment.getMapAsync(this);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.entry_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_make_entry:
                goEntry();
                return true;
            default:

        }
        return false;
    }

    private void goEntry() {
        Intent intent = new Intent(FutureTrialActivity.this, EntryActivity.class);
        intent.putExtra("courses", courses);
        intent.putExtra("classes", classes);
        startActivity(intent);
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
