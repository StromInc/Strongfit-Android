package com.strom.strongfit.models;

/**
 * Created by USER on 21/05/2015.
 */
public class Alimento {
    private int alimentoID;
    private String name;
    private float calories;
    private float lipidos;
    private float carbohidratos;
    private float proteinas;
    private int alimentoTipo;

    public int getAlimentoID() {
        return alimentoID;
    }

    public String getName() {
        return name;
    }

    public float getCalories() {
        return calories;
    }

    public float getLipidos() {
        return lipidos;
    }

    public float getCarbohidratos() {
        return carbohidratos;
    }

    public float getProteinas() {
        return proteinas;
    }

    public int getAlimentoTipo() {
        return alimentoTipo;
    }

    //Luego van los set

    public void setAlimentoID(int alimentoID) {
        this.alimentoID = alimentoID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCalories(float calories) {
        this.calories = calories;
    }

    public void setLipidos(float lipidos) {
        this.lipidos = lipidos;
    }

    public void setCarbohidratos(float carbohidratos) {
        this.carbohidratos = carbohidratos;
    }

    public void setProteinas(float proteinas) {
        this.proteinas = proteinas;
    }

    public void setAlimentoTipo(int alimentoTipo) {
        this.alimentoTipo = alimentoTipo;
    }
}
