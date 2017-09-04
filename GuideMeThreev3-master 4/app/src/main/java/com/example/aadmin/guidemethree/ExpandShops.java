package com.example.aadmin.guidemethree;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

public class ExpandShops extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expand_shops);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        TextView txtname = (TextView) findViewById(R.id.txtname);
        TextView txtaddress = (TextView) findViewById(R.id.txtaddress);
        TextView txttype = (TextView) findViewById(R.id.txttype);


        Bundle bundle = getIntent().getExtras();
        String name = bundle.getString("name");
        String address = bundle.getString("address");
        String type = bundle.getString("type");

        txtname.setText(name);
        txtaddress.setText(address);
        txttype.setText(type);


    }

}
