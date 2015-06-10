package com.strom.strongfit;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.strom.strongfit.utils.SessionManager;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;


public class LoginActivity extends ActionBarActivity {
    private EditText input_email;
    private EditText input_password;
    private String password, email;
    private SessionManager sessionManager;
    HttpURLConnection httpConnection = null;
    BufferedReader bufferedReader = null;
    StringBuilder response = null;
    private static final String TAG = LoginActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        sessionManager = new SessionManager(this);
        if(sessionManager.isLoggedIn()){
           sessionManager.checkUserType();
            this.finish();
        }
        input_email = (EditText) findViewById(R.id.input_email);
        input_password = (EditText) findViewById(R.id.input_password);
    }

    public void onClickEntrar(View view) {
        email = input_email.getText().toString();
        password = input_password.getText().toString();
        Intent intent;
        new GetHoraTask().execute();

        if (email.equals("paciente@gmail.com") && password.equals("123")) {
            sessionManager.createLogInSession("paciente@gmail.com", "Moy", "paciente");
            intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            this.finish();
        }else {
            Toast.makeText(this, "paciente@gmail.com o medico@gmail.com y 123", Toast.LENGTH_SHORT).show();
        }
    }
    public void onClickRegistro(View view){
        Intent intent = new Intent(this, RegistroActivity.class);
        startActivity(intent);
    }

    public class GetHoraTask extends AsyncTask<Object, Void, String> {

        @Override
        protected String doInBackground(Object... params) {
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {

                try {
                    Log.d(TAG, "Entro al try");
                    //No olviden cambiar la ip
                    URL url = new URL("http://192.168.1.141:8080/StrongFit/sLoginAndroid");
                    httpConnection = (HttpURLConnection) url.openConnection();
                    httpConnection.setRequestMethod("POST");
                    httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    httpConnection.setDoOutput(true);
                    String postParameters = "correo=value1&contra=value2";
                    httpConnection.setFixedLengthStreamingMode(
                            postParameters.getBytes().length);
                    PrintWriter out = new PrintWriter(httpConnection.getOutputStream());
                    out.print(postParameters);
                    out.close();
                    int responseCode = httpConnection.getResponseCode();
                    Log.w(TAG, String.valueOf(responseCode));


                    bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
                    String linea;
                    response = new StringBuilder();
                    while((linea = bufferedReader.readLine()) != null){
                        response.append(linea);
                    }
                    Log.i(TAG, "Valor: " + response.toString());
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.e(TAG, e.getMessage());
                }finally {
                    if (httpConnection != null) {
                        httpConnection.disconnect();
                    }
                }
            } else {
                Log.e(TAG, "No hay red");
            }
            return "Salio bueno";
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
