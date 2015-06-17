package com.strom.strongfit.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by USER on 16/06/2015.
 */
public class Consumido {
    int idAlta;
    String nombre;
    String calorias;
    public int getIdAlta() {
        return idAlta;
    }

    public void setIdAlta(int idAlta) {
        this.idAlta = idAlta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCalorias() {
        return calorias;
    }

    public void setCalorias(String calorias) {
        this.calorias = calorias;
    }
}
