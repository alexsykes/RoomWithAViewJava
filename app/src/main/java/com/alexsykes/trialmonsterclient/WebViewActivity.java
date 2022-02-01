package com.alexsykes.trialmonsterclient;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

public class WebViewActivity extends AppCompatActivity {

    private String trialid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        trialid = getIntent().getExtras().getString("trialid");
        setContentView(R.layout.activity_web_view);
        WebView webView = findViewById(R.id.webView);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        webView.loadUrl("https://trialmonster.uk/index.php?option=com_entryman&view=comingup&id=" + trialid);
        //  webView.loadUrl("https://android.trialmonster.uk/getWebPage.php?id="+trialid);
        //  webView.loadUrl( "https://android.trialmonster.uk/getIntroText.php?id=" + trialid );

    }
}