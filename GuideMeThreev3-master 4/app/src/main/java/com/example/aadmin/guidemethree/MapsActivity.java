package com.example.aadmin.guidemethree;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private String latitude;
    private String longitude;
    private double lati;
    private double longi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        Bundle getCordinates = getIntent().getExtras();
//Extract the data…
        latitude = getCordinates.getString("latitude");
        longitude = getCordinates.getString("longitude");
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        lati = Double.parseDouble(latitude);
        longi = Double.parseDouble(longitude);

        LatLng demo = new LatLng(longi,lati);
        mMap.addMarker(new MarkerOptions().position(demo).title("Marker in demo place"));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(demo,20));
    }
}
