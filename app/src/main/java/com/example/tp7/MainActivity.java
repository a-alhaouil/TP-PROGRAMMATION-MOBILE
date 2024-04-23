package com.example.tp7;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.ui.AppBarConfiguration;
import static androidx.core.location.LocationManagerCompat.isLocationEnabled;


import android.content.pm.PackageManager;
import android.Manifest;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.content.Context;
import android.widget.Toast;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.tp7.databinding.ActivityMainBinding;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
    private AppBarConfiguration appBarConfiguration;

    private GoogleMap mMap;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1001;
    private @NonNull ActivityMainBinding binding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(MainActivity.this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] ss = new String[1];
            ss[0] = Manifest.permission.ACCESS_COARSE_LOCATION;
            requestPermissions(ss, 997);
        }

        if(isLocationEnabled()){
            Location loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            assert loc != null;
            double alt = loc.getAltitude() ;
            double lon = loc.getLongitude() ;
            double lat = loc.getLatitude() ;
            System.out.println("lon : " + lon);
            System.out.println("lat : " + lat);
            // Add a marker in Sydney and move the camera
            LatLng sydney = new LatLng(lat, lon);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }else{
            Toast.makeText(MainActivity.this, "Activé localisation.", Toast.LENGTH_LONG).show();
        }
//        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }
}


