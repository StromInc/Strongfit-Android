package com.strom.strongfit.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Alumno on 09/06/2015.
 */
public class ConectarHTTP {

    HttpURLConnection httpConnection = null;
    BufferedReader bufferedReader = null;
    StringBuilder response = null;

    public String iniciarSesion(){
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
    }
}
