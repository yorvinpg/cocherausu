package com.example.cocheraapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class MenUsuActivity extends AppCompatActivity implements View.OnClickListener{

    private int My_PERMISSIONS_REQUEST_READ_CONTACTS;
    private FusedLocationProviderClient mFuse;
    DatabaseReference mDatabase;
    private Button busqueda;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_men_usu);

        mFuse = LocationServices.getFusedLocationProviderClient(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();
        busqueda = findViewById(R.id.btnbusqueda);
        subirLangLong();
        busqueda.setOnClickListener(this);

    }

    private void subirLangLong() {
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED){

            ActivityCompat.requestPermissions(MenUsuActivity.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    My_PERMISSIONS_REQUEST_READ_CONTACTS);

            return;
        }
        mFuse.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location !=null){
                    Log.e("Latitud: ",+ location.getLatitude()+"Longitud: "+location.getLongitude());
                    Map<String, Object> latlang = new HashMap<>();
                    latlang.put("latitud",location.getLatitude());
                    latlang.put("longitud",location.getLongitude());
                    mDatabase.child("Maps").push().setValue(latlang);
                }

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnbusqueda : Intent t = new Intent(MenUsuActivity.this, BusquedaActivity.class);
            startActivity(t);
            break;
        }

    }
}
