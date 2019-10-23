package com.example.cocheraapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Date;

public class RegistroActivity extends AppCompatActivity implements View.OnClickListener {

    //Spinner spinner;//
    EditText txtnom, txtape, txtcorreo, txtcla, txtrep;
    Button btnguardar, btnlg;
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;
    String nom,ape,correo,cla,rep;
    ProgressDialog mDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_registro);

        mAuth = FirebaseAuth.getInstance();
        txtnom = findViewById(R.id.txtnom);
        //spinner = findViewById(R.id.spinner);//
        txtape = findViewById(R.id.txtape);
        txtcorreo = findViewById(R.id.txtcorreo);
        txtcla = findViewById(R.id.txtclave);
        txtrep = findViewById(R.id.txtrepita);
        btnguardar = findViewById(R.id.btnguardar);
        btnlg  = findViewById(R.id.btnlg);
        mDialog = new ProgressDialog(this);
        btnguardar.setOnClickListener(this);
        btnlg.setOnClickListener(this);
        mDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        //String [] opciones={"Elija Cargo","Administrador","Usuario"};//
        //ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,R.layout.spinner_item, opciones);//
       // spinner.setAdapter(adapter);//
    }

    @Override
    public void onClick(View v){
        if (v==btnguardar){
            UserRegister();
        }else if (v==btnlg){
            startActivity(new Intent(RegistroActivity.this, LoginActivity.class));
        }
    }
    private void UserRegister(){
        nom = txtnom.getText().toString().trim();
        ape = txtape.getText().toString().trim();
        correo = txtcorreo.getText().toString().trim();
        cla  = txtcla.getText().toString().trim();
        rep = txtrep.getText().toString().trim();

        if (TextUtils.isEmpty(nom)){
            Toast.makeText(RegistroActivity.this, "Ingrese Nombre", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(ape)){
            Toast.makeText(RegistroActivity.this, "Ingrese Apellido", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(correo)){
            Toast.makeText(RegistroActivity.this, "Ingrese correo", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(cla)){
            Toast.makeText(RegistroActivity.this, "Ingrese clave", Toast.LENGTH_SHORT).show();
            return;
        }else if (cla.length()<6){
            Toast.makeText(RegistroActivity.this, "Debe ser mayor a 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }
        else if (TextUtils.isEmpty(rep)){
            Toast.makeText(RegistroActivity.this, "Repita su clave", Toast.LENGTH_SHORT).show();
            return;
        }else if (rep.length()<6){
            Toast.makeText(RegistroActivity.this, "Debe ser mayor a 6 caracteres", Toast.LENGTH_SHORT).show();
            return;
        }
        mDialog.setMessage("Creando Usuario espere porfavor...");
        mDialog.setCanceledOnTouchOutside(false);
        mDialog.show();
        mAuth.createUserWithEmailAndPassword(correo, cla).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    sendEmailVerification();
                    mDialog.dismiss();
                    OnAuth(task.getResult().getUser());
                    mAuth.signOut();
                }else {
                    Toast.makeText(RegistroActivity.this, "Usuario ya creado", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void sendEmailVerification(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user!=null){
            user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(RegistroActivity.this, "Revise su correo electrónico para verificar", Toast.LENGTH_SHORT).show();
                        FirebaseAuth.getInstance().signOut();
                        Intent p= new Intent(RegistroActivity.this, LoginActivity.class);
                        startActivity(p);
                    }
                }
            });
        }
    }

    private void OnAuth(FirebaseUser user){
        createAnewUser(user.getUid());
    }
    private void createAnewUser(String uid){
        User user = BuildNewuser();
        mDatabase.child(uid).setValue(user);
    }
    private User BuildNewuser(){ //gettipo(),//
        return new User(
                getnombre(),
                getapellido(),
                getcorreo(),
                getcontraseña(),
                new Date().getTime()
        );
    }
    public String getnombre(){
        return nom;
    }

    public  String getapellido(){
        return ape;
    }

    public String getcorreo(){
        return correo;
    }

    public String getcontraseña(){
        return cla;
    }
    //public Spinner gettipo(){
        //return spinner;//
   // }//

}
