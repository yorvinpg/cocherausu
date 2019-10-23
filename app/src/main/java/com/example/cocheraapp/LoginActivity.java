package com.example.cocheraapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    EditText usuario, pass;
    TextView olvi;
    Button iniciar, registrarse;
    FirebaseAuth mAuth;
    FirebaseAuth.AuthStateListener mAuthListner;
    FirebaseUser mUser;
    String   Usuario, Pass;
    ProgressDialog dialog;

    public  static  final String userEmail="";

    public static  final String TAG="LOGIN";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        usuario = findViewById(R.id.usuario);
        pass    = findViewById(R.id.pass);
        olvi = findViewById(R.id.txtolvi);
        iniciar = findViewById(R.id.btnIniciar);
        registrarse = findViewById(R.id.btnRegistrarse);
        dialog = new ProgressDialog(this);
        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        olvi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent e = new Intent(LoginActivity.this, RecuActivity.class);
                startActivity(e);
            }
        });
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (mUser != null){
                    Intent intent= new Intent(LoginActivity.this, MenUsuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
                else{
                    Log.d(TAG, "AuthStateChanged:Logout");
                }

            }
        };
        iniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSing();
            }
        });

        registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent  i = new Intent(LoginActivity.this, RegistroActivity.class);
                startActivity(i);
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        mAuth.removeAuthStateListener(mAuthListner);
    }

    @Override
    protected void onStop(){
        super.onStop();
        if (mAuthListner != null){
            mAuth.removeAuthStateListener(mAuthListner);
        }
    }
    @Override
    public void onBackPressed(){
        LoginActivity.super.finish();
    }

    private void userSing(){
        Usuario = usuario.getText().toString().trim();
        Pass   = pass.getText().toString().trim();
        if (TextUtils.isEmpty(Usuario)){
            Toast.makeText(LoginActivity.this, "Ingrese el correo electronico correcto", Toast.LENGTH_SHORT).show();
            return;
        }else if (TextUtils.isEmpty(Pass)){
            Toast.makeText(LoginActivity.this, "Ingrese la contraseña correcta!", Toast.LENGTH_SHORT).show();
            return;
        }

        dialog.setMessage("Iniciando sesion porfavor espere....");
        dialog.setIndeterminate(true);
        dialog.show();
        mAuth.signInWithEmailAndPassword(Usuario, Pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()){
                    dialog.dismiss();

                    Toast.makeText(LoginActivity.this, "Login no iniciado!", Toast.LENGTH_SHORT).show();

                }else {
                    dialog.dismiss();

                    checkIfEmailVerified();
                }
            }
        });
    }

    private  void checkIfEmailVerified(){
        FirebaseUser users=FirebaseAuth.getInstance().getCurrentUser();
        boolean emailVerified=users.isEmailVerified();
        if (!emailVerified){
            Toast.makeText(this, "verify the Email id", Toast.LENGTH_SHORT).show();
            mAuth.signOut();
            finish();
        }
        else {
            usuario.getText().clear();
            pass.getText().clear();
            Intent intent= new Intent(LoginActivity.this, MenUsuActivity.class);
            intent.putExtra(userEmail,Usuario);
            startActivity(intent);

        }
    }
}
