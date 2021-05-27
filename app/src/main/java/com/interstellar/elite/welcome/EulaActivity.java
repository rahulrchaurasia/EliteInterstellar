package com.interstellar.elite.welcome;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;


import com.interstellar.elite.BaseActivity;
import com.interstellar.elite.R;
import com.interstellar.elite.core.APIResponse;
import com.interstellar.elite.core.IResponseSubcriber;
import com.interstellar.elite.facade.PrefManager;
import com.interstellar.elite.login.LoginActivity;
import com.interstellar.elite.webview.MyWebViewClient;

import androidx.appcompat.widget.Toolbar;

public class EulaActivity extends BaseActivity implements View.OnClickListener {
    Button btnAgree, btnDisAgree;
    PrefManager prefManager;
    WebView webView;
    public static boolean isActive = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eula);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        prefManager = new PrefManager(this);
        initWidgets();
        setListener();
        settingWebview();
    }

    private void initWidgets() {
        webView = (WebView) findViewById(R.id.webView);
        btnAgree = (Button) findViewById(R.id.btnAgree);
        btnDisAgree = (Button) findViewById(R.id.btnDisAgree);
    }

    private void setListener() {
        btnAgree.setOnClickListener(this);
        btnDisAgree.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAgree:

                prefManager.setFirstTimeLaunch(false);
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                break;
            case R.id.btnDisAgree:
                finish();
                break;
        }
    }




    private void settingWebview() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(false);
        settings.setJavaScriptEnabled(true);
        settings.setSupportMultipleWindows(false);

        settings.setLoadsImagesAutomatically(true);
        settings.setLightTouchEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);
        settings.setJavaScriptEnabled(true);


//        MyWebViewClient webViewClient = new MyWebViewClient(this);
//        webView.setWebViewClient(webViewClient);

        webView.setWebViewClient(new WebViewClient() {


            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO show you progress image
                if (isActive)
                    showDialog();
                // new ProgressAsync().execute();
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO hide your progress image
                cancelDialog();
                super.onPageFinished(view, url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                /*if (url.endsWith(".pdf")) {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(Uri.parse(url), "application/pdf");
                    try {
                        startActivity(intent);
                    } catch (ActivityNotFoundException e) {
                        //user does not have a pdf viewer installed
                        String googleDocs = "https://docs.google.com/viewer?url=";
                        webView.loadUrl(googleDocs + url);
                    }
                }*/
                return false;
            }
        });

        webView.getSettings().setBuiltInZoomControls(true);
       /* Log.d("URL", url);
        //webView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + url);
        webView.loadUrl(url);*/
        webView.loadUrl("http://elite.interstellar.co.in/elite_gold/slider/elitetnc.html");
    }

    @Override
    protected void onResume() {
        super.onResume();
        isActive = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isActive = false;

    }
}
