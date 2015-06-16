package com.strom.strongfit;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
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
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends AppCompatActivity {
    private EditText input_email;
    private EditText input_password;
    private SessionManager sessionManager;
    private ConectarHTTP conectarHTTP;
    private String email = "paciente@gmail.com";
    private int idPaciente = 0;
    private String avatar = "avatar";
    private String nombre = "moy";
    private String password;
    private DBOperations dbOperations;
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
        dbOperations = new DBOperations(this);
        input_email = (EditText) findViewById(R.id.input_email);
        input_password = (EditText) findViewById(R.id.input_password);
        conectarHTTP = new ConectarHTTP();
    }

    public void onClickEntrar(View view) {
        email = input_email.getText().toString().trim();
        password = input_password.getText().toString().trim();
        String[] arrayDatos = new String[]{email, password};
        if(email.equals("") || password.equals("")){
            Toast.makeText(this, "Por favor completa los campos", Toast.LENGTH_SHORT).show();
        }else{
            new IniciarSesionTask().execute(arrayDatos);
        }
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
            Map<String, String> datosPaciente = new HashMap();
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                String respuesta = conectarHTTP.iniciarSesion(params[0], params[1]);
                //respuesta.equals("si")
                if(respuesta.equals("si")){
                    datosPaciente = conectarHTTP.getDatosPaciente(email, password);
                    nombre = datosPaciente.get("nombre");
                    idPaciente = Integer.parseInt(datosPaciente.get("idPaciente"));
//                  avatar = datosPaciente.get("avatar");

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

                        dbOperations.insertOrIgnore(values);
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
            Log.i(TAG, "La respuesta en onPostExecute fue: " + s);
            //s.equals("si")
            if (s.equals("si")) {
                Intent intent;
                sessionManager.createLogInSession(email, nombre, idPaciente, avatar);
                intent = new Intent(getApplicationContext(), MainActivity.class);
                finish();
                startActivity(intent);
            }else {
                Toast.makeText(getApplicationContext(), "Datos incorrectos", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
