package com.strom.strongfit.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    private static final String TAG = DBHelper.class.getSimpleName();

    public static final String DB_NAME = "strongfit.db";
    public static final int DB_VERSION = 1;
    //Los datos de toda la tabla alimentos
    public static final String TABLE_ALIMENTOS = "alimentos";
    public static final String C_ID_ALIMENTO = BaseColumns._ID;
    public static final String C_NAME_ALIMENTO = "name";
    public static final String C_CALORIAS = "calorias";
    public static final String C_LIPIDOS = "lipidos";
    public static final String C_CARBOHIDRATOS = "carbohidratos";
    public static final String C_PROTEINAS = "proteinas";
    public static final String C_TIPO_ALIMENTO = "tipo_alimento";
    //Esto nos sirve para recorrer los cursores que es donde se almacenan los datos, son las columnas
    public static final int C_ID_ALIMENTO_INDEX = 0;
    public static final int C_NAME_ALIMENTO_INDEX = 1;
    public static final int C_CALORIAS_INDEX = 2;
    public static final int C_LIPIDOS_INDEX = 3;
    public static final int C_CARBOHIDRATOS_INDEX = 4;
    public static final int C_PROTEINAS_INDEX = 5;
    public static final int C_TIPO_ALIMENTO_INDEX = 6;

    //Los datos de la tabla consumo
    public static final String TABLE_CONSUMO = "consumo";
    public static final String C_ID_CONSUMO = BaseColumns._ID;
    public static final String C_TIPO_COMIDA = "tipo_comida";
    public static final String C_PACIENTE_ID = "paciente_id";
    public static final String C_DIA = "dia";
    public static final String C_MES = "mes";
    public static final String C_YEAR = "consumo_year";
    public static final String C_GRAMOS = "gramos";

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    // Este metodo crea las tablas
    @Override
    public void onCreate(SQLiteDatabase db) {
        //nuestro query de la tabla alimentos
        String sqlAimentos = "create table " + TABLE_ALIMENTOS + " (" + C_ID_ALIMENTO + " int " +
                "primary " +
                "key, " + C_NAME_ALIMENTO + " text, "
                + C_CALORIAS + " float, " + C_LIPIDOS + " float, " + C_CARBOHIDRATOS + " float, "
                + C_PROTEINAS + " float, " + C_TIPO_ALIMENTO + " int);";

        Log.i(TAG, "onCreated sql: " + sqlAimentos);
        db.execSQL(sqlAimentos);
    }
    // Se llama cuando la version vieja es diferente de la nueva
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Typically do ALTER TABLE_ALIMENTOS statements, but... we're just in development, so:
        db.execSQL("drop table if exists " + TABLE_ALIMENTOS);
        Log.i(TAG, "onUpdated");
        onCreate(db); // run onCreate to get the new database
    }
}