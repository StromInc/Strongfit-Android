package com.strom.strongfit.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.strom.strongfit.LoginActivity;

public class SessionManager {
    SharedPreferences preferencias;
    SharedPreferences.Editor editor;
    Context contexto;
    private static final int PRIVATE_MODE = 0;
    private static final String PREFE_NAME = "StrongFitPreferencias";
    private static final String IS_LOGIN = "isLoggedIn";
    static final String KEY_NAME = "name";
    static final String KEY_EMAIL = "email";
    static final String KEY_ID_PACIENTE = "idPaciente";
    static final String KEY_AVATAR = "avatar";

    public SessionManager(Context context) {
        this.contexto = context;
        preferencias = contexto.getSharedPreferences(PREFE_NAME, PRIVATE_MODE);
        editor = preferencias.edit();
    }

    public void createLogInSession(String email, String name, int idPaciente, String avatar){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_NAME, name);
        editor.putInt(KEY_ID_PACIENTE, idPaciente);
        editor.putString(KEY_AVATAR, avatar);
        editor.commit();
    }

    public boolean checkLogin(){
        if(!this.isLoggedIn()){
            Intent intent = new Intent(contexto, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            contexto.startActivity(intent);
            return true;
        }
        return false;
    }
    public void logOutUser(){
        editor.clear();
        editor.commit();

        Intent intent = new Intent(contexto, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        contexto.startActivity(intent);
    }

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
