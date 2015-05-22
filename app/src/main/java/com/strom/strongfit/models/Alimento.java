package com.strom.strongfit.models;

/**
 * Created by USER on 21/05/2015.
 */
public class Alimento {
    private int alimentoID;
    private String name;
    private String calories;

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
