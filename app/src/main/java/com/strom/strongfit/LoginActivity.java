package com.strom.strongfit;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.strom.strongfit.db.DBHelper;
import com.strom.strongfit.db.DBOperations;
import com.strom.strongfit.models.Alimento;
import com.strom.strongfit.utils.ConectarHTTP;
import com.strom.strongfit.utils.SessionManager;

import java.util.ArrayList;


public class LoginActivity extends AppCompatActivity {
    private EditText input_email;
    private EditText input_password;
    private SessionManager sessionManager;
    private ConectarHTTP conectarHTTP;
    //private DBOperations dbOperations;
    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);
        if(sessionManager.isLoggedIn()){
            Intent i = new Intent(this, MainActivity.class);
            this.finish();
            startActivity(i);
        }
        //dbOperations = new DBOperations(this);
        input_email = (EditText) findViewById(R.id.input_email);
        input_password = (EditText) findViewById(R.id.input_password);
        conectarHTTP = new ConectarHTTP();
    }

    public void onClickEntrar(View view) {
        String email = input_email.getText().toString();
        String password = input_password.getText().toString();
        String[] arrayDatos = new String[]{email, password};
        new IniciarSesionTask().execute(arrayDatos);
    }

    public class IniciarSesionTask extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setTitle("Espera un momento");
            progressDialog.setMessage("Se estan configurando algunas cosas");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            ArrayList<Alimento> listaAlimentos = new ArrayList<Alimento>();
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                String respuesta = conectarHTTP.iniciarSesion(params[0], params[1]);
                if(respuesta.equals("entra")){
                    listaAlimentos = conectarHTTP.getTodosAlimentos();
                    ContentValues values = new ContentValues();
                    for(Alimento alimento : listaAlimentos){
                        values.clear();
                        values.put(DBHelper.C_ID, alimento.getAlimentoID());
                        values.put(DBHelper.C_NAME, alimento.getName());
                        values.put(DBHelper.C_CALORIAS, alimento.getCalories());
                        values.put(DBHelper.C_LIPIDOS, alimento.getLipidos());
                        values.put(DBHelper.C_CARBOHIDRATOS, alimento.getCarbohidratos());
                        values.put(DBHelper.C_PROTEINAS, alimento.getProteinas());
                        values.put(DBHelper.C_TIPO_ALIMENTO, alimento.getAlimentoTipo());

                        Log.i(TAG, "Agregando alimento numero: " + alimento.getAlimentoID());

                        //dbOperations.insertOrIgnore(values);
                    }
                    return respuesta;
                }
            } else {
                Log.e(TAG, "No hay red");
            }
            return "Algo raro ocurrio";
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            Log.i(TAG, s);
            //s.equals("entra")
            if (true) {
                Intent intent;
                sessionManager.createLogInSession("paciente@gmail.com", "Moy");
                intent = new Intent(getApplicationContext(), MainActivity.class);
                finish();
                startActivity(intent);
            }else {
                Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
