package com.example.aditijoshi.personalcapital.Core;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.aditijoshi.personalcapital.NetworkUtils.HttpRequest;
import com.example.aditijoshi.personalcapital.R;

import de.greenrobot.event.EventBus;

public class BaseActivity extends AppCompatActivity {

    public Boolean isTablet = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        isTablet();
    }

    public void subscribe() {
        if (!EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().register(this);
        }
    }

    public void unsubscribe() {
        if (EventBus.getDefault().isRegistered(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    //Unsubscribe from event listener every time app is foregrounded
    @Override
    public void onDestroy() {
      super.onDestroy();
      unsubscribe();
    }

    //Subscribing to event listener every time app is foregrounded
    @Override
    protected void onResume() {
        super.onResume();
        subscribe();
        isTablet();
    }


    // detecting if the device is a tablet using the screensize as a parameter, anything greater than 6.5 inches
    // is considered a tablet
    public void isTablet() {
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        float yInches = metrics.heightPixels / metrics.ydpi;
        float xInches = metrics.widthPixels / metrics.xdpi;
        double diagonalInches = Math.sqrt(xInches * xInches + yInches * yInches);
        if (diagonalInches >= 6.5) {
            isTablet = true;
        } else {
            isTablet = false;
        }
    }


}
