package com.example.rmider.utils;

import android.location.Address;
import android.location.Geocoder;
import android.os.Looper;

import com.example.rmider.callback.OnPositionListener;
import com.example.rmider.config.AppConfig;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class LocationUtils {

    private static LocationUtils locationUtils;
    private static LatLng latLng;

    public static LocationUtils getInstance(){
        if (locationUtils == null){
            locationUtils = new LocationUtils();
        }
        return locationUtils;
    }

    public void getCurrentPostion(final OnPositionListener onPositionListener){
        final FusedLocationProviderClient locationProviderClient = new FusedLocationProviderClient(AppConfig.getContext());

        LocationRequest locationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setFastestInterval(5000)
                .setInterval(10000);

        locationProviderClient.requestLocationUpdates(locationRequest, new LocationCallback(){
            @Override
            public void onLocationResult(LocationResult locationResult) {
                super.onLocationResult(locationResult);

                latLng = new LatLng(locationResult.getLastLocation().getLatitude(),locationResult.getLastLocation().getLongitude());

                onPositionListener.onPosition(latLng);
                locationProviderClient.removeLocationUpdates(this);
            }
        }, Looper.myLooper());
    }

    public String getAddress(LatLng latLng){
        Geocoder geocoder;
        List<Address> addresses = null;
        geocoder = new Geocoder(AppConfig.getContext(), Locale.getDefault());

        try {
            addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1);
        } catch (IOException e) {
            return "";
        }
        return addresses.get(0).getAddressLine(0);
    }

}
