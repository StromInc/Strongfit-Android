package com.strom.strongfit.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.strom.strongfit.LoginActivity;

public class SessionManager {
    SharedPreferences preferencias; //Aqui vamos a guardar nuestras preferencias
    SharedPreferences.Editor editor; //Nos deja editar nuestras preferencias
    Context contexto;
    private static final int PRIVATE_MODE = 0; //No recuerdo para que era
    //El nombre de nuestro archivo
    private static final String PREFE_NAME = "StrongFitPreferencias";
    //con todas estas keys evitamos equivocarnos ya que las preferencias se manejan por clave valor
    private static final String IS_LOGIN = "isLoggedIn";
    static final String KEY_NAME = "name";
    static final String KEY_EMAIL = "email";
    static final String KEY_ID_PACIENTE = "idPaciente";
    static final String KEY_AVATAR = "avatar";
    //Nuestro constructor
    public SessionManager(Context context) {
        this.contexto = context;
        preferencias = contexto.getSharedPreferences(PREFE_NAME, PRIVATE_MODE);
        editor = preferencias.edit();
    }
    //Se le pasan los datos necesarios para crear una preferencia
    public void createLogInSession(String email, String name, int idPaciente, String avatar){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_NAME, name);
        editor.putInt(KEY_ID_PACIENTE, idPaciente);
        editor.putString(KEY_AVATAR, avatar);
        editor.commit(); //Guarda los cambios
    }
    //Verifica si ya existen preferencias con lo que no se necesita volver a loguear
    public boolean checkLogin(){
        if(!this.isLoggedIn()){
            Intent intent = new Intent(contexto, LoginActivity.class);
            //No me acuerdo para que sirve, creo que cierra todas las actividades si no estas logueado
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            contexto.startActivity(intent);
            return true;
        }
        return false;
    }
    //Cerramos la sesion duh!
    public void logOutUser(){
        editor.clear(); /*Borra todo a la verga*/
        editor.commit(); //Guarda los cambios
        //Lo mismo que arriba
        Intent intent = new Intent(contexto, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        contexto.startActivity(intent);
    }
    //Con esto obtenemos los datos de las preferencias
    //Le pasamos la preferencia que queremos mediante el uso de la key y un valor por si no se encuentra
    //la preferencia
    public boolean isLoggedIn(){
        return preferencias.getBoolean(IS_LOGIN, false);
    }
    public String getAvatar(){
        return preferencias.getString(KEY_AVATAR, "Nana");
    }
    public String getName(){
        return preferencias.getString(KEY_NAME, "Moises de Patzi");
    }
    public String getCorreo(){
        return preferencias.getString(KEY_EMAIL, "moy@gmail.com");
    }
    public int getIDPaciente(){
        return preferencias.getInt(KEY_ID_PACIENTE, 777);
    }
}
