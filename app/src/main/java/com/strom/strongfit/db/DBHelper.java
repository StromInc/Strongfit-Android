package com.strom.strongfit.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

/**
 * Created by USER on 14/06/2015.
 */
public class DBHelper extends SQLiteOpenHelper {

    private static final String TAG = DBHelper.class.getSimpleName();

    public static final String DB_NAME = "strongfit.db";
    public static final int DB_VERSION = 1;
    //Los datos de toda la tabla
    public static final String TABLE = "alimentos";
    public static final String C_ID = BaseColumns._ID;
    public static final String C_NAME = "name";
    public static final String C_CALORIAS = "calorias";
    public static final String C_LIPIDOS = "lipidos";
    public static final String C_CARBOHIDRATOS = "carbohidratos";
    public static final String C_PROTEINAS = "proteinas";
    public static final String C_TIPO_ALIMENTO = "tipo_alimento";
    //Esto nos sirve para recorrer los cursores que es donde se almacenan los datos
    public static final int C_ID_INDEX = 0;
    public static final int C_NAME_INDEX = 1;
    public static final int C_CALORIAS_INDEX = 2;
    public static final int C_LIPIDOS_INDEX = 3;
    public static final int C_CARBOHIDRATOS_INDEX = 4;
    public static final int C_PROTEINAS_INDEX = 5;
    public static final int C_TIPO_ALIMENTO_INDEX = 6;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }
    // Este metodo crea la tabla de alimentos
    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "create table " + TABLE + " (" + C_ID + " int primary key, " + C_NAME + " text, "
                + C_CALORIAS + " float, " + C_LIPIDOS + " float, " + C_CARBOHIDRATOS + " float, "
                + C_PROTEINAS + " float, " + C_TIPO_ALIMENTO + " int);";

        Log.i(TAG, "onCreated sql: " + sql);
        db.execSQL(sql);
    }
    // Se llama cuando la version vieja es diferente de la nueva
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Typically do ALTER TABLE statements, but... we're just in development, so:
        db.execSQL("drop table if exists " + TABLE);
        Log.i(TAG, "onUpdated");
        onCreate(db); // run onCreate to get the new database
    }
}