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
    public void agregarAlimento(int idPaciente, int idAlimento, float gramos, int dia, int mes, int myYear, int comida){
        Log.i(TAG, "insertarAlimento");
        long numAlta = -1;
        ContentValues values = new ContentValues();
        SQLiteDatabase dataBase = dbHelper.getWritableDatabase();
        try{
            String busquedaQuery = "select " + DBHelper.C_ID_CONSUMO + " from "
                    + DBHelper.TABLE_CONSUMO + " where " + DBHelper.C_TIPO_COMIDA + " = " + comida
                    + " and " + DBHelper.C_PACIENTE_ID + " = " + idPaciente + " and "
                    + DBHelper.C_DIA + " = " + dia + " and " + DBHelper.C_MES + " = " + mes
                    + " and " + DBHelper.C_YEAR + " = " + myYear + " and "
                    + DBHelper.C_GRAMOS + " = " + gramos;

            Log.i(TAG, "busquedaQuery: " + busquedaQuery);
            Cursor cursor = dataBase.rawQuery(busquedaQuery, null);
            if (cursor.moveToFirst()) {
                while(!cursor.isAfterLast()){
                    numAlta = cursor.getInt(DBHelper.C_ID_CONSUMO_INDEX);
                    cursor.moveToNext();
                }
            }
            cursor.close();
            Log.i(TAG, "El valor del numAlta_1 es: " + numAlta);
            values.clear();
            values.put(DBHelper.C_TIPO_COMIDA, comida);
            values.put(DBHelper.C_PACIENTE_ID, idPaciente);
            values.put(DBHelper.C_DIA, dia);
            values.put(DBHelper.C_MES, mes);
            values.put(DBHelper.C_YEAR, myYear);
            values.put(DBHelper.C_GRAMOS, gramos);
            if(numAlta == -1){
                Log.i(TAG, "Entro al if");
                numAlta = dataBase.insertWithOnConflict(DBHelper.TABLE_CONSUMO, null, values,
                        SQLiteDatabase.CONFLICT_IGNORE);
            } else{
                Log.i(TAG, "Entro al else");
                values.put(DBHelper.C_ID_CONSUMO, numAlta);
                dataBase.insertWithOnConflict(DBHelper.TABLE_CONSUMO, null, values,
                        SQLiteDatabase.CONFLICT_IGNORE);
            }
            values.clear();
            Log.i(TAG, "El valor del numAlta_2 es: " + numAlta);
            values.put(DBHelper.C_TABLE_ALIMENTO_ID, idAlimento);
            values.put(DBHelper.C_TABLE_CONSUMO_ID, numAlta);
            values.put(DBHelper.C_PENDIENTE, "si");

            long otro =dataBase.insertWithOnConflict(DBHelper.TABLE_ALIMENTO_CONSUMO, null, values,
                    SQLiteDatabase.CONFLICT_IGNORE);
            Log.i(TAG, "El valor del otro es: " + otro);
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