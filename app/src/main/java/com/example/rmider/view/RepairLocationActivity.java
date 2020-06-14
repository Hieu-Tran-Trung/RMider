package com.example.rmider.view;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rmider.R;
import com.example.rmider.callback.OnPositionListener;
import com.example.rmider.utils.LocationUtils;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class RepairLocationActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap googleMap;
    private TextView txtNameStreet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repair_location);

        initView();
    }

    private void initView() {
        txtNameStreet = findViewById(R.id.txtNameStreet);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;

        addMyPosition();
    }

    private void addMyPosition() {
        LocationUtils.getInstance().getCurrentPostion(new OnPositionListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onPosition(LatLng latLng) {
                txtNameStreet.setVisibility(View.VISIBLE);
                txtNameStreet.setText("" + LocationUtils.getInstance().getAddress(latLng));
                addMarker(latLng);
                moveCamera(latLng);
            }
        });
    }

    private void moveCamera(LatLng latLng) {
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,18));
    }

    private void addMarker(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions()
                .position(latLng);

        googleMap.addMarker(markerOptions);
    }
}
