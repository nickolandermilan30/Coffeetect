package com.example.coffeetech;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class Geo_Location_Maker extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ArrayList<LatLng> locationArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_geo_location_maker);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // in below line we are initializing our array list.
        locationArrayList = new ArrayList<>();

        // on below line we are adding our
        // locations in our array list.
        locationArrayList.add(new LatLng(-34, 151)); // Sydney
        locationArrayList.add(new LatLng(-31.083332, 150.916672)); // TamWorth
        locationArrayList.add(new LatLng(-32.916668, 151.750000)); // NewCastle
        locationArrayList.add(new LatLng(-27.470125, 153.021072)); // Brisbane

        // Set up the button click event
        Button btnShowMarkers = findViewById(R.id.btnShowMarkers);
        btnShowMarkers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMarkers();
            }
        });
    }

    private void showMarkers() {
        // Clear existing markers
        mMap.clear();

        // Display markers
        for (LatLng location : locationArrayList) {
            mMap.addMarker(new MarkerOptions().position(location).title("Marker"));
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location, 18.0f));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }
}
