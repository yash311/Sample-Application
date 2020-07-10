package com.example.myapplication;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProgressDialogDemo extends AppCompatActivity {

    Button btn_progress;
    Handler handler;
    ProgressDialog progressDialog;
    int status = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_progress_dilog);
        btn_progress = findViewById(R.id.btn_progressbar);
        handler = new Handler();
    }

    public void createProgressDilog() {
        progressDialog = new ProgressDialog(ProgressDialogDemo.this);
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setCancelable(true);
        progressDialog.setMax(100);
        progressDialog.show();
    }

    public void startProgressDialog() {
        status = 0;
        new Thread(() -> {
            while (status < 100) {
                status++;
                try {
                    Thread.sleep(150);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                handler.post(() -> {
                    progressDialog.setProgress(status);
                    if (status == 100) {
                        progressDialog.dismiss();
                        Toast.makeText(this, "Download successful", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).start();
    }

    public void startDownload(View v) {
        createProgressDilog();
        startProgressDialog();
    }
}