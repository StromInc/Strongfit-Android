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
                alimento.setCalories(String.valueOf(cursor.getFloat(DBHelper.C_CALORIAS_INDEX)));

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

        String[] busquedaPalabra  = new String[1]; //Esto es necesario porque me pide un array
        busquedaPalabra[0] = palabra + "%";
        String query = "SELECT * FROM " + DBHelper.TABLE + " WHERE " + DBHelper.C_NAME + " LIKE ?";
        Log.v(TAG, query);
        Cursor cursor = dataBase.rawQuery(query, busquedaPalabra);
        if (cursor.moveToFirst()) {
            Log.i(TAG, "Entro al cursor");
            while(!cursor.isAfterLast()){
                Log.i(TAG, "Esta recorriendo el cursor");
                Alimento alimento = new Alimento();
                //Los traemos deacuerdo a su numero de columna
                alimento.setAlimentoID(cursor.getInt(DBHelper.C_ID_INDEX));
                alimento.setName(cursor.getString(DBHelper.C_NAME_INDEX));
                alimento.setCalories(String.valueOf(cursor.getFloat(DBHelper.C_CALORIAS_INDEX)));

                alimentosEncontrados.add(alimento);

                cursor.moveToNext();
            }
        }
        cursor.close();
        return alimentosEncontrados;
    }
    public Alimento getDatosAlimento(int id){
        Alimento alimentoEncontrado = new Alimento();
        String selection = DBHelper.C_ID + " =? ";
        String[] parametros = new String[]{String.valueOf(id)};
        SQLiteDatabase dataBase = dbHelper.getReadableDatabase();
        Cursor cursor = dataBase.query(DBHelper.TABLE, null, selection, parametros, null, null, null);
        if(cursor.moveToFirst()){
            Log.v(TAG, "Entro al cursor");
            while(!cursor.isAfterLast()){
                Log.v(TAG, "Entro al while");
                alimentoEncontrado.setAlimentoID(cursor.getInt(DBHelper.C_ID_INDEX));
                alimentoEncontrado.setName(cursor.getString(DBHelper.C_NAME_INDEX));
                alimentoEncontrado.setCalories(String.valueOf(cursor.getFloat(DBHelper.C_CALORIAS_INDEX)));
                alimentoEncontrado.setLipidos(String.valueOf(cursor.getFloat(DBHelper.C_LIPIDOS_INDEX)));
                alimentoEncontrado.setCarbohidratos(String.valueOf(cursor.getFloat(DBHelper.C_CARBOHIDRATOS_INDEX)));
                alimentoEncontrado.setProteinas(String.valueOf(cursor.getFloat(DBHelper.C_PROTEINAS_INDEX)));
                alimentoEncontrado.setAlimentoTipo(cursor.getInt(DBHelper.C_TIPO_ALIMENTO_INDEX));
                cursor.moveToNext();
            }
        }
        cursor.close();
        return alimentoEncontrado;
    }
}