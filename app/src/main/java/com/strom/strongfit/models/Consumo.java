package com.strom.strongfit.models;

/**
 * Created by USER on 02/07/2015.
 */
public class Consumo {
    int consumoId;
    int tipoComida;
    int pacienteId;
    int dia;
    int mes;
    int year;
    float gramos;

    public int getConsumoId() {
        return consumoId;
    }

    public int getTipoComida() {
        return tipoComida;
    }

    public int getPacienteId() {
        return pacienteId;
    }

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    public int getYear() {
        return year;
    }

    public float getGramos() {
        return gramos;
    }

    public void setConsumoId(int consumoId) {
        this.consumoId = consumoId;
    }

    public void setTipoComida(int tipoComida) {
        this.tipoComida = tipoComida;
    }

    public void setPacienteId(int pacienteId) {
        this.pacienteId = pacienteId;
    }

    public void setDia(int dia) {
        this.dia = dia;
    }

    public void setMes(int mes) {
        this.mes = mes;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public void setGramos(float gramos) {
        this.gramos = gramos;
    }
}
