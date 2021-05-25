package com.interstellar.elite.webview;

import android.app.DownloadManager;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Environment;
import android.print.PrintAttributes;
import android.print.PrintDocumentAdapter;
import android.print.PrintManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.MimeTypeMap;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.interstellar.elite.BaseActivity;
import com.interstellar.elite.R;
import com.interstellar.elite.home.HomeActivity;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import androidx.appcompat.widget.Toolbar;


public class CommonWebViewActivity extends BaseActivity {

    WebView webView;
    String url;
    String name;
    String title;
    CountDownTimer countDownTimer;
    public static boolean isActive = false;

    Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_view);
        webView = (WebView) findViewById(R.id.webView);
        url = getIntent().getStringExtra("URL");
        name = getIntent().getStringExtra("NAME");
        title = getIntent().getStringExtra("TITLE");
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(title);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (title.equals("RECEIPT")) {
            fab.setVisibility(View.VISIBLE);
        } else {
            fab.setVisibility(View.GONE);
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
                bmp = getBitmapFromWebView(webView);

                new shareQuote(webView).execute();
              /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT)
                    createWebPrintJob(webView);
                else
                    Toast.makeText(CommonWebViewActivity.this, "Update Android Os for this feature ", Toast.LENGTH_SHORT).show();
*/
            }
        });
        if (isNetworkConnected()) {
            settingWebview();
            startCountDownTimer();
        } else
            Toast.makeText(this, "Check your internet connection", Toast.LENGTH_SHORT).show();
    }

    private void startCountDownTimer() {
        countDownTimer = new CountDownTimer(30000, 1000) {

            public void onTick(long millisUntilFinished) {
                //mTextField.setText("seconds remaining: " + millisUntilFinished / 1000);
                //here you can have your logic to set text to edittext
            }

            public void onFinish() {
                try {
                    cancelDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        };
        countDownTimer.start();
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

    private  void downloadPdf(String url, String name) {
        Toast.makeText(this, "Download started..", Toast.LENGTH_LONG).show();
        DownloadManager.Request r = new DownloadManager.Request(Uri.parse(url));
        r.setMimeType("application/pdf");
        r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, name + ".pdf");
        r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        r.setMimeType(MimeTypeMap.getFileExtensionFromUrl(url));
        DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
        dm.enqueue(r);
    }

    private void settingWebview() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        settings.setBuiltInZoomControls(true);
        settings.setUseWideViewPort(false);
        settings.setSupportMultipleWindows(false);

        settings.setLoadsImagesAutomatically(true);
        settings.setDomStorageEnabled(true);
        settings.setLoadWithOverviewMode(true);

        webView.addJavascriptInterface(new MyJavaScriptInterface(), "Android");


      /*  MyWebViewClient webViewClient = new MyWebViewClient(this);
        webView.setWebViewClient(webViewClient);*/
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
        webView.addJavascriptInterface(new MyJavaScriptInterface(), "Android");
        Log.d("URL", url);

        if (url.endsWith(".pdf")) {

            webView.loadUrl("https://docs.google.com/viewer?url=" + url);
            //webView.loadUrl("http://drive.google.com/viewerng/viewer?embedded=true&url=" + url);
        } else {
            webView.loadUrl(url);
        }
        //webView.loadUrl(url);
    }




    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            switch (keyCode) {
                case KeyEvent.KEYCODE_BACK:
                    if (webView.canGoBack()) {
                        webView.goBack();
                    } else {
                        finish();
                    }
                    return true;
            }

        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    class MyJavaScriptInterface {

        @JavascriptInterface
        public void RedirectToHomepage() {//Android.RedirectToHomepage();
            Intent intent = new Intent(CommonWebViewActivity.this, HomeActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }


        @JavascriptInterface
        public void moveToPlayStore(String str) {

            onBrowseClick(webView,str);
        }

        @JavascriptInterface
        public void moveToPlayStore_fin(String str) {

            onBrowseClick(webView,str);
        }
        // For ios App
//        public void moveToAppStore_fin(String str) {
//
//            onBrowseClick(webView,str);
//        }

    }

    public void onBrowseClick(View v,String url ){

        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        // Verify that the intent will resolve to an activity
//        if (intent.resolveActivity(getPackageManager()) != null) {
//            // Here we use an intent without a Chooser unlike the next example
//            startActivity(intent);
//        }

        try {
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            // Display some error message
            Log.d("URL BROWSER", e.toString()) ;
        }
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
        if (countDownTimer != null)
            countDownTimer.cancel();
    }

    public Bitmap getBitmapFromWebView(WebView webView) {
        Bitmap bmp;
        webView.measure(View.MeasureSpec.makeMeasureSpec(
                View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED),
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
        webView.layout(0, 0, webView.getMeasuredWidth(),
                webView.getMeasuredHeight());
        webView.setDrawingCacheEnabled(true);
        webView.buildDrawingCache();
        bmp = Bitmap.createBitmap(webView.getMeasuredWidth(),
                webView.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas bigcanvas = new Canvas(bmp);
        Paint paint = new Paint();
        int iHeight = bmp.getHeight();
        bigcanvas.drawBitmap(bmp, 0, iHeight, paint);
        webView.draw(bigcanvas);
        return bmp;
    }

    public class shareQuote extends AsyncTask<Void, Void, Void> {
        WebView webView;

        public shareQuote(WebView web) {
            webView = web;
        }

        @Override
        protected void onPreExecute() {
            showDialog("Generating pdf...");
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids) {


            try {
                //bmp = getBitmapFromWebView(webView);
                // SimplePDFTable(bmp, bikePremiumResponse.getSummary().getRequest_Core().getFirst_name().toUpperCase() + " - " + bikePremiumResponse.getSummary().getRequest_Core().getRegistration_no());
                //SimplePDFTable(bmp);
                createPdf(bmp);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            cancelDialog();
            super.onPostExecute(aVoid);
        }
    }

    private void createPdf(Bitmap bitmap) {


        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(bitmap.getWidth(),
                bitmap.getHeight(), 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();


        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#ffffff"));
        canvas.drawPaint(paint);


        bitmap = Bitmap.createScaledBitmap(bitmap, bitmap.getWidth(), bitmap.getHeight(), true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap, 0, 0, null);
        document.finishPage(page);


        String fileName;
        Calendar c = Calendar.getInstance();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
        fileName = df.format(c.getTime());

        //File direct = new File(Environment.getExternalStorageDirectory(), "/FINMART/QUOTES");
        File direct = createShareDirIfNotExists();
        String test = direct.getAbsolutePath();


        // write the document content

        try {
            document.writeTo(new FileOutputStream(test
                    + "/" + fileName + ".pdf"));

        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }

        // close the document.
        document.close();
        //sharePdfTowhatsApp(fileName);
    }

    public static File createShareDirIfNotExists() {
        boolean ret = true;

        File file = new File(Environment.getExternalStorageDirectory(), "/elite/receipt");
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e("TravellerLog :: ", "Problem creating Quotes folder");
                ret = false;
            }
        }
        return file;
    }

    private void createWebPrintJob(WebView webView) {

        PrintManager printManager = (PrintManager) this
                .getSystemService(Context.PRINT_SERVICE);

        PrintDocumentAdapter printAdapter =
                webView.createPrintDocumentAdapter();

        String jobName = getString(R.string.app_name) + " Print Test";

        if (printManager != null) {
            printManager.print(jobName, printAdapter,
                    new PrintAttributes.Builder().build());
        }
    }
}