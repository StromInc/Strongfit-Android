package com.strom.strongfit.utils;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.strom.strongfit.LoginActivity;
import com.strom.strongfit.MainActivity;

public class SessionManager {
    SharedPreferences preferencias;
    SharedPreferences.Editor editor;
    Context contexto;
    private static final int PRIVATE_MODE = 0;
    private static final String PREFE_NAME = "StrongFitPreferencias";
    private static final String IS_LOGIN = "isLoggedIn";
    static final String KEY_NAME = "name";
    static final String KEY_EMAIL = "email";
    static final String KEY_USER_TYPE = "userType";

    public SessionManager(Context context) {
        this.contexto = context;
        preferencias = contexto.getSharedPreferences(PREFE_NAME, PRIVATE_MODE);
        editor = preferencias.edit();
    }

    public void createLogInSession(String email, String name, String userType){
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_EMAIL, email);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_USER_TYPE, userType);
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
    public void checkUserType(){
        String tipoUsuario = preferencias.getString(KEY_USER_TYPE, "paciente");
        Intent intent;
        if(tipoUsuario.equals("paciente")){
            intent = new Intent(contexto, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            contexto.startActivity(intent);
        }
    }
}
