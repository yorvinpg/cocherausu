package com.example.cocheraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.util.HashMap;
import java.util.Map;

public class OperAdmActivity extends AppCompatActivity {

    EditText txtlocal,txtrepre,txtcedu,txtruc,txtpate,txtmuni,txtdire,txtsiti,txthora,txtgeo;
    Button   btnguar,btnatras;
    DatabaseReference mDatabase;

    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_oper_adm);


        txtlocal = findViewById(R.id.txtloca);
        txtrepre = findViewById(R.id.txtrepre);
        txtcedu = findViewById(R.id.txtcedu);
        txtruc = findViewById(R.id.txtruc);
        txtpate = findViewById(R.id.txtpate);
        txtmuni = findViewById(R.id.txtcod);
        txtdire = findViewById(R.id.txtdire);
        txtsiti = findViewById(R.id.txtsit);
        txthora = findViewById(R.id.txthora);
        txtgeo = findViewById(R.id.txtgeo);
        btnguar = findViewById(R.id.btnguar);
        btnatras = findViewById(R.id.btnatras);
        mDialog = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Cochera");

        btnguar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String local = txtlocal.getText().toString();
               String repre = txtrepre.getText().toString();
               int cedu = Integer.parseInt(txtcedu.getText().toString());
               int ruc = Integer.parseInt(txtruc.getText().toString());
               int pate = Integer.parseInt(txtpate.getText().toString());
               int muni = Integer.parseInt(txtmuni.getText().toString());
               String  dire = txtdire.getText().toString();
               int siti = Integer.parseInt(txtsiti.getText().toString());
               int hora = Integer.parseInt(txthora.getText().toString());
               String  geo = txtgeo.getText().toString();

                CargarCochera(local, repre, cedu, ruc, pate, muni, dire, siti, hora, geo);
            }
        });
    }

    private void CargarCochera(String local, String repre, int cedu, int ruc, int pate, int muni, String dire, int siti, int hora, String geo)
    {
        Map<String, Object> datosCochera = new HashMap<>();
        datosCochera.put("local", local);
        datosCochera.put("representante", repre);
        datosCochera.put("cedula", cedu);
        datosCochera.put("ruc", ruc);
        datosCochera.put("patente", pate);
        datosCochera.put("codigo municipal", muni);
        datosCochera.put("direccion", dire);
        datosCochera.put("sitios libres", siti);
        datosCochera.put("tarifa de hora", hora);
        datosCochera.put("geolocalizacion", geo);

        mDatabase.child("Cochera").push().setValue(datosCochera);
    }
}
