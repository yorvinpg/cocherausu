package com.example.cocheraapp;

import android.widget.Spinner;

public class User {

    String nombre;
    String apellido;
    String correo;
    String contraseña;
    //Spinner tipo;//
    long createdAt;

    public User(String nombre,String apellido, String correo,String contraseña, long createdAt) { //,Spinner tipo//
        this.nombre = nombre;
        this.apellido = apellido;
        this.correo = correo;
        this.contraseña = contraseña;
        //this.tipo = tipo;//
        this.createdAt = createdAt;
    }

    public String getNombre() {
        return nombre;
    }

    public String getApellido(){
        return apellido;
    }

    public String getCorreo() {
        return correo;
    }

    public String getContraseña(){
        return contraseña;
    }
    //public Spinner getTipo(){//
        //return tipo;//
    //}//

    public long getCreatedAt() {
        return createdAt;
    }
}
