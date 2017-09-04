package com.example.aadmin.guidemethree;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class Map extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);
        TextView txtlatitude = (TextView) findViewById(R.id.txtLatitude);
        TextView txtlongitude = (TextView) findViewById(R.id.txtLongitude);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Bundle getCordinates = getIntent().getExtras();

//Extract the data…
        String latitude = getCordinates.getString("latitude");
        String longitude = getCordinates.getString("longitude");

        txtlatitude.setText(latitude);
        txtlongitude.setText(longitude);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
