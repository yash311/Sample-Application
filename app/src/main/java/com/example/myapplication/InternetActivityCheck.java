package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;

public class InternetActivityCheck extends AppCompatActivity {

    TextView tv;
    SwipeRefreshLayout swipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_internet_check);
        tv = findViewById(R.id.tv);
        swipe = findViewById(R.id.swipe);
        swipe.setRefreshing(false);

        swipe.setOnRefreshListener(() -> {
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo net = cm.getActiveNetworkInfo();
            if(net!=null && net.isConnectedOrConnecting())
                tv.setTextColor(Color.GREEN);
            else
                tv.setTextColor(Color.RED);
            return;
        });
    }
}
