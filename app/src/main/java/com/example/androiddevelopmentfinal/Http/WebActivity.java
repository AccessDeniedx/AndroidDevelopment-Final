package com.example.androiddevelopmentfinal.Http;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.example.androiddevelopmentfinal.R;

public class WebActivity extends AppCompatActivity {


    String mView;
    private WebView webView;

    public WebActivity(String mView)
    {this.mView=mView;}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);
        webView=(WebView)findViewById(R.id.web_view);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(mView);
    }
}