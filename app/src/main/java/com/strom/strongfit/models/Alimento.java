package com.strom.strongfit.models;

/**
 * Created by USER on 21/05/2015.
 */
public class Alimento {
    private int alimentoID;
    private String name;
    private String calories;
    private String lipidos;
    private String carbohidratos;
    private String proteinas;
    private int alimentoTipo;

    public String getLipidos() {
        return lipidos;
    }

    public void setLipidos(String lipidos) {
        this.lipidos = lipidos;
    }

    public String getCarbohidratos() {
        return carbohidratos;
    }

    public void setCarbohidratos(String carbohidratos) {
        this.carbohidratos = carbohidratos;
    }

    public String getProteinas() {
        return proteinas;
    }

    public void setProteinas(String proteinas) {
        this.proteinas = proteinas;
    }

    public int getAlimentoTipo() {
        return alimentoTipo;
    }

    public void setAlimentoTipo(int alimentoTipo) {
        this.alimentoTipo = alimentoTipo;
    }

    public int getAlimentoID() {
        return alimentoID;
    }

    public void setAlimentoID(int alimentoID) {
        this.alimentoID = alimentoID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }
}
