package com.strom.strongfit;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.strom.strongfit.utils.ConectarHTTP;
import com.strom.strongfit.utils.SessionManager;


public class LoginActivity extends ActionBarActivity {
    private EditText input_email;
    private EditText input_password;
    private SessionManager sessionManager;
    private ConectarHTTP conectarHTTP;
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

        @Override
        protected String doInBackground(String... params) {
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                String respuesta = conectarHTTP.iniciarSesion(params[0], params[1]);
                return respuesta;
            } else {
                Log.e(TAG, "No hay red");
            }
            return "No hay red";
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
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
