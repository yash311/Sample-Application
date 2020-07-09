package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class WebActivity extends AppCompatActivity {

    EditText et_url;
    WebView wv_web;
    LinearLayout rl_web;
    AlertDialog.Builder builder;
    ProgressDialog progress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);
        et_url = findViewById(R.id.et_url);
        wv_web = findViewById(R.id.wv_web);
        rl_web = findViewById(R.id.rl_web);

        wv_web.setWebViewClient(new WebViewClient(){
            public void onReceivedError(WebView view, int errCode, String description, String failinngUrl){
                Toast.makeText(WebActivity.this, description, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                progress.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progress.dismiss();
            }
        });
        wv_web.getSettings().setJavaScriptEnabled(true);

        progress = new ProgressDialog(this);
        progress.setMessage("Wait till the result is ready");
        progress.setCanceledOnTouchOutside(true);

        et_url.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String url = charSequence.toString();
                if (url.startsWith("www.") && url.endsWith(".com") && url.length() > 8) {
                    progress.show();
                    wv_web.loadUrl("https://" + url);
                    wv_web.getSettings().setBuiltInZoomControls(true);
                    et_url.setTextColor(Color.GREEN);
                } else {
                    et_url.setTextColor(Color.RED);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert")
                .setMessage("Do you really want to exit from app")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        WebActivity.super.onBackPressed();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(WebActivity.this, "Continuing an app", Toast.LENGTH_SHORT).show();
                    }
                });

    }

    @Override
    public void onBackPressed() {
        builder.create().show();
    }
}
