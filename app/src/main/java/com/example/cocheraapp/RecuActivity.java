package com.example.cocheraapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class RecuActivity extends AppCompatActivity {

    private EditText email;
    private Button btnreset, btnback;
    private FirebaseAuth mauth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_recu);

        email = findViewById(R.id.email);
        btnreset = findViewById(R.id.btnrestablecer);
        btnback = findViewById(R.id.btnback);

        mauth = FirebaseAuth.getInstance();

        btnback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(RecuActivity.this, LoginActivity.class);
                startActivity(i);

            }
        });
        btnreset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Email = email.getText().toString().trim();

                if (TextUtils.isEmpty(Email)){
                    Toast.makeText(getApplication(), "Ingrese correo electrónico registrada", Toast.LENGTH_SHORT).show();
                    return;
                }

                mauth.sendPasswordResetEmail(Email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(RecuActivity.this, "¡Le hemos enviado instrucciones para restablecer su contraseña!", Toast.LENGTH_SHORT).show();
                            Intent p= new Intent(RecuActivity.this, LoginActivity.class);
                            startActivity(p);
                        }else {
                            Toast.makeText(RecuActivity.this, "¡Error al enviar el correo electrónico de restablecimiento!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
