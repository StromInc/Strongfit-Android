package com.strom.strongfit.utils;

import android.util.Log;

import com.strom.strongfit.models.Alimento;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by Alumno on 09/06/2015.
 */
public class ConectarHTTP {


    private static final String TAG = ConectarHTTP.class.getSimpleName();

    public String iniciarSesion(String correo, String contra){
        HttpURLConnection httpConnection = null;
        BufferedReader bufferedReader = null;
        StringBuilder response = null;
        try {
            String postParameters = "correo="+correo+"&contra="+contra;
            Log.i(TAG, "ParametrosPost: " + postParameters);
            //No olviden cambiar la ip
            URL url = new URL("http://192.168.1.141:8080/StrongFit/sLoginAndroid");
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("POST");
            httpConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            httpConnection.setDoOutput(true);

            httpConnection.setFixedLengthStreamingMode(
                    postParameters.getBytes().length);

            PrintWriter out = new PrintWriter(httpConnection.getOutputStream());
            out.print(postParameters);
            out.close();

            Log.w(TAG, "Codigo de respuesta: " + httpConnection.getResponseCode());

            bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            String linea;
            response = new StringBuilder();
            while((linea = bufferedReader.readLine()) != null){
                response.append(linea);
            }
            Log.i(TAG, "La respuesta del servidor: " + response.toString());
            return response.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return "Error 90";
        }finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
        }
    }

    public ArrayList<Alimento> getTodosAlimentos(){
        HttpURLConnection httpConnection = null;
        BufferedReader bufferedReader = null;
        StringBuilder response = null;
        ArrayList<Alimento> alimentos = new ArrayList<Alimento>();
        try {
            //No olviden cambiar la ip
            URL url = new URL("http://192.168.1.141:8080/StrongFit/sGetTodosAlimentos");
            httpConnection = (HttpURLConnection) url.openConnection();
            httpConnection.setRequestMethod("GET");
            httpConnection.setRequestProperty("Content-Type", "application/json");

            Log.w(TAG, "Codigo de respuesta: " + httpConnection.getResponseCode());

            bufferedReader = new BufferedReader(new InputStreamReader(httpConnection.getInputStream()));
            String linea;
            response = new StringBuilder();
            while((linea = bufferedReader.readLine()) != null){
                response.append(linea);
            }
            Log.i(TAG, "La respuesta del servidor: " + response.toString());
            JSONObject jsonResponse = new JSONObject(response.toString());
            JSONArray jsonArray = jsonResponse.getJSONArray("alimentos");
            JSONObject alimentosJsonObject;

            for (int i=0; i<jsonArray.length(); i++) {
                alimentosJsonObject = (JSONObject) jsonArray.get(i);
                Alimento alimento = new Alimento();

                alimento.setAlimentoID(alimentosJsonObject.getInt("idAlimento"));

                alimentos.add(i, alimento);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
        }
        return alimentos;
    }
}
