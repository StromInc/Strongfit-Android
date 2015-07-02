package com.strom.strongfit.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.strom.strongfit.models.Alimento;

import java.util.ArrayList;

public class DBOperations {

    private static final String TAG = DBOperations.class.getSimpleName();
    private DBHelper dbHelper;
    //Inicializamos nuestras operaciones y nuestro ayudador tio!
    public DBOperations(Context context) {
        dbHelper = new DBHelper(context);
    }

    public void insertOrIgnore(ContentValues values){
        Log.i(TAG, "insertOrIgnore: " + values);

        SQLiteDatabase dataBase = dbHelper.getWritableDatabase();
        //Esto es para que no se repitan los alimentos si la tabla se tiene que actualizar
        try{
            dataBase.insertWithOnConflict(DBHelper.TABLE_ALIMENTOS, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        }finally {
            dataBase.close();
        }
    }
    //Este metodo nos va a traer todos los alimentos
    public ArrayList<Alimento> getTodosAlimentos(){
        ArrayList<Alimento> alimentosTodos = new ArrayList<>();
        //Traemos todos los alimentos
        SQLiteDatabase dataBase = dbHelper.getReadableDatabase();
        //Solo traemos 20 alimentos
        Cursor cursor = dataBase.query(DBHelper.TABLE_ALIMENTOS, null, null, null, null, null, null, "20");

        if (cursor.moveToFirst()) {
            while(!cursor.isAfterLast()){
                Alimento alimento = new Alimento();
                //Los traemos deacuerdo a su numero de columna
                alimento.setAlimentoID(cursor.getInt(DBHelper.C_ID_ALIMENTO_INDEX));
                alimento.setName(cursor.getString(DBHelper.C_NAME_ALIMENTO_INDEX));
                alimento.setCalories(String.valueOf(cursor.getFloat(DBHelper.C_CALORIAS_INDEX)));

                alimentosTodos.add(alimento);

                cursor.moveToNext();
            }
        }
        cursor.close();
        return alimentosTodos;
    }
    //Para el buscador de alimentos
    public ArrayList<Alimento> getBusquedaDeAlimentos(String palabra){
        ArrayList<Alimento> alimentosEncontrados = new ArrayList<>();
        SQLiteDatabase dataBase = dbHelper.getReadableDatabase();

        String[] busquedaPalabra  = new String[1]; //Esto es necesario porque me pide un array
        busquedaPalabra[0] = palabra + "%";
        //un tipico query
        String query = "SELECT * FROM " + DBHelper.TABLE_ALIMENTOS + " WHERE " + DBHelper.C_NAME_ALIMENTO + " LIKE ?";
        Log.v(TAG, query);

        Cursor cursor = dataBase.rawQuery(query, busquedaPalabra);
        if (cursor.moveToFirst()) {
            Log.i(TAG, "Entro al cursor");
            while(!cursor.isAfterLast()){
                Log.i(TAG, "Esta recorriendo el cursor");
                Alimento alimento = new Alimento();
                //Los traemos deacuerdo a su numero de columna
                alimento.setAlimentoID(cursor.getInt(DBHelper.C_ID_ALIMENTO_INDEX));
                alimento.setName(cursor.getString(DBHelper.C_NAME_ALIMENTO_INDEX));
                alimento.setCalories(String.valueOf(cursor.getFloat(DBHelper.C_CALORIAS_INDEX)));

                alimentosEncontrados.add(alimento);

                cursor.moveToNext();
            }
        }
        cursor.close();
        return alimentosEncontrados;
    }
    //Traemos los datos especificos de un alimento, por su ID
    public Alimento getDatosAlimento(int id){
        Alimento alimentoEncontrado = new Alimento();

        String selection = DBHelper.C_ID_ALIMENTO + " =? ";
        String[] parametros = new String[]{String.valueOf(id)}; //Tiene que ser string
        SQLiteDatabase dataBase = dbHelper.getReadableDatabase();

        Cursor cursor = dataBase.query(DBHelper.TABLE_ALIMENTOS, null, selection, parametros, null, null, null);
        if(cursor.moveToFirst()){
            Log.v(TAG, "Entro al cursor");
            while(!cursor.isAfterLast()){
                Log.v(TAG, "Entro al while");
                //obtenemos nuestros datos por cursores
                alimentoEncontrado.setAlimentoID(cursor.getInt(DBHelper.C_ID_ALIMENTO_INDEX));
                alimentoEncontrado.setName(cursor.getString(DBHelper.C_NAME_ALIMENTO_INDEX));
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