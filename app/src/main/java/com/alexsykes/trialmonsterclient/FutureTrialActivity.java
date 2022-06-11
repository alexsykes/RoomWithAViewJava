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

    private String trialid, venue_name, courses, classes, entrymode;
    private double lat, lon;
    private String[] entrymodes, emodes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        trialid = intent.getExtras().getString("trialid");
        lat = intent.getDoubleExtra("lat", 0);
        lon = intent.getDoubleExtra("lon", 0);
        courses = intent.getStringExtra("courselist");
        classes = intent.getStringExtra("classlist");
        venue_name = intent.getExtras().getString("venue_name");
        entrymode = intent.getExtras().getString("emode");
        entrymodes = entrymode.split(",");

        setContentView(R.layout.activity_web_view);
        WebView webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

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
        intent.putExtra("trialid", trialid);
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
