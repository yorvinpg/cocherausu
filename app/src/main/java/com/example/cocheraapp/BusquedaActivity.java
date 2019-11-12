package com.example.cocheraapp;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.drawable.Icon;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BusquedaActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private DatabaseReference mDatabase;
    private ArrayList<Marker> tmpRealTimeMarkers = new ArrayList<>();
    private ArrayList<Marker> realTimeMarkers = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        mDatabase = FirebaseDatabase.getInstance().getReference();


    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){

            return;
        }
        mMap.setMyLocationEnabled(true);

        mDatabase.child("Cochera").addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                for (Marker maker : realTimeMarkers)
                {
                    maker.remove();

                }
                for (DataSnapshot snapshot:dataSnapshot.getChildren())
                {
                    PuntoMaps pm = snapshot.getValue(PuntoMaps.class);
                    Double latitud = pm.getLatitud();
                    Double longitud = pm.getLongitud();
                    LatLng coordenadas = new LatLng(latitud,longitud);
                    MarkerOptions markerOptions = new MarkerOptions()
                            .position(coordenadas)
                            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_maps));
                    CameraPosition cameraPosition = new CameraPosition.Builder().zoom(16).target(coordenadas).build();
                    mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));

                    tmpRealTimeMarkers.add(mMap.addMarker(markerOptions));


                }
                realTimeMarkers.clear();
                realTimeMarkers.addAll(tmpRealTimeMarkers);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                Toast.makeText(BusquedaActivity.this, "Error en linea ", Toast.LENGTH_SHORT).show();

            }
        });

        /*Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        Map.addMarker(new MarkerOptions()
             .position(sydney)
             .title("Marker in Sydney"))
             .setIcon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_maps));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));*/
    }

}
