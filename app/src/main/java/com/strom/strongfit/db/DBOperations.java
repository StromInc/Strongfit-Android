package com.strom.strongfit.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.strom.strongfit.models.Alimento;

import java.util.ArrayList;

/**
 * Created by USER on 14/06/2015.
 */
public class DBOperations {

    private static final String TAG = DBOperations.class.getSimpleName();
    private DBHelper dbHelper;

    public DBOperations(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void insertOrIgnore(ContentValues values){
        Log.i(TAG, "insertOrIgnore: " + values);
        SQLiteDatabase dataBase = dbHelper.getWritableDatabase();
        //Esto es para que no se repitan los alimentos si la tabla se tiene que actualizar
        try{
            dataBase.insertWithOnConflict(DBHelper.TABLE, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        }finally {
            dataBase.close();
        }
    }
    //Este metodo nos va a traer todos los alimentos
    public ArrayList<Alimento> getTodosAlimentos(){
        ArrayList<Alimento> alimentosTodos = new ArrayList<Alimento>();
        //Traemos todos los alimentos
        SQLiteDatabase dataBase = dbHelper.getReadableDatabase();
        Cursor cursor = dataBase.query(DBHelper.TABLE, null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            while(!cursor.isAfterLast()){
                Alimento alimento = new Alimento();
                //Los traemos deacuerdo a su numero de columna
                alimento.setAlimentoID(cursor.getInt(DBHelper.C_ID_INDEX));
                alimento.setName(cursor.getString(DBHelper.C_NAME_INDEX));
                alimento.setCalories(cursor.getFloat(DBHelper.C_CALORIAS_INDEX));

                alimentosTodos.add(alimento);

                cursor.moveToNext();
            }
        }
        cursor.close();
        return alimentosTodos;
    }
    public ArrayList<Alimento> getBusquedaDeAlimentos(String palabra){
        ArrayList<Alimento> alimentosEncontrados = new ArrayList<Alimento>();
        SQLiteDatabase dataBase = dbHelper.getReadableDatabase();

        String[] busquedaPalabra  = new String[]{palabra}; //Esto es necesario porque me pide un array

        String query = "SELECT * FROM " + DBHelper.TABLE + " WHERE " + DBHelper.C_NAME + " LIKE ?";

        Cursor cursor = dataBase.rawQuery(query, busquedaPalabra);
        if (cursor.moveToFirst()) {
            while(!cursor.isAfterLast()){
                Alimento alimento = new Alimento();
                //Los traemos deacuerdo a su numero de columna
                alimento.setAlimentoID(cursor.getInt(DBHelper.C_ID_INDEX));
                alimento.setName(cursor.getString(DBHelper.C_NAME_INDEX));
                alimento.setCalories(cursor.getFloat(DBHelper.C_CALORIAS_INDEX));

                alimentosEncontrados.add(alimento);

                cursor.moveToNext();
            }
        }
        cursor.close();
        return alimentosEncontrados;
    }
}