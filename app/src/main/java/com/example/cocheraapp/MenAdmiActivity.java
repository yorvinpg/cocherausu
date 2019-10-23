package com.example.cocheraapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;

public class MenAdmiActivity extends AppCompatActivity {

    Button rmi,al;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_men_admi);

        rmi = findViewById(R.id.btnRMI);
        al  = findViewById(R.id.btnAL);

        rmi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent r = new Intent(MenAdmiActivity.this,OperAdmActivity.class);
                startActivity(r);
            }
        });

        al.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a = new Intent(MenAdmiActivity.this,OperaSitActivity.class);
            }
        });

    }
}
