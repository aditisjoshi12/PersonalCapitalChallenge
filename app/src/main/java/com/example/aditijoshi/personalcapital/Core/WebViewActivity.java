package com.example.aditijoshi.personalcapital.Core;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.example.aditijoshi.personalcapital.NetworkUtils.NoNetworkConnectionEvent;
import com.example.aditijoshi.personalcapital.R;


public class WebViewActivity extends BaseActivity {
    public static final String URL_ARG = "url";
    public static final String TOPIC_ARG = "url-topic";

    private WebView webView;
    String url;
    String topic;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUI();

        configureWebView();


    }

    private void configureWebView() {

        if(webView!=null) {

            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webSettings.setLoadWithOverviewMode(true);
            webSettings.setUseWideViewPort(true);
            webSettings.setBuiltInZoomControls(true);
            webSettings.setLoadsImagesAutomatically(true);
            webSettings.setDomStorageEnabled(true);
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
                setMixedContentType(webSettings);
            }

            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    return super.shouldOverrideUrlLoading(view, url);
                }

            });

            webView.setWebChromeClient(new WebChromeClient() {
                public void onProgressChanged(WebView view, int progress) {

                }
            });


            webView.loadUrl(url + "?displayMobileNavigation=0");

        }
    }

    private void setUI() {

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);
        layout.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        webView = new WebView(getApplicationContext());
        Intent intent = getIntent();
        url = intent.getStringExtra(URL_ARG);
        topic = intent.getStringExtra(TOPIC_ARG);

        Toolbar myToolbar = new Toolbar(this);
        layout.addView(myToolbar);
        myToolbar.setTitle(topic);
        setSupportActionBar(myToolbar);
        myToolbar.setBackgroundColor(UIHelper.getInstance().getColor(this,R.color.colorPrimary));
        myToolbar.setTitleTextColor(UIHelper.getInstance().getColor(this,R.color.colorWhite));
        layout.addView(webView);
        setContentView(layout);
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setMixedContentType(WebSettings webSettings) {
        webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_COMPATIBILITY_MODE);
    }


    @Override
    public void onResume() {
        super.onResume();


    }


    public void onDestroy() {
        webView.loadUrl("about:blank");
        webView.onPause();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
        }
        return true;
    }


    public void onEvent(NoNetworkConnectionEvent e) {

    }

}
