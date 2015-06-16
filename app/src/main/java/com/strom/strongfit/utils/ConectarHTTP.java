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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Alumno on 09/06/2015.
 */
public class ConectarHTTP {
    //No olviden cambiar la ip, la ruta principal de neustro proyecto
    private static final String STRONGFITURL = "http://192.168.1.75:8080/StrongFit/";
    //Esto recupera el nombre de la clase
    private static final String TAG = ConectarHTTP.class.getSimpleName();

    public String iniciarSesion(String correo, String contra){
        HttpURLConnection httpConnection = null;
        BufferedReader bufferedReader = null;
        StringBuilder response = null;
        try {
            //La solicitud post
            String postParameters = "correo=" + correo + "&contra=" + contra;
            Log.i(TAG, "ParametrosPost: " + postParameters);

            URL url = new URL(STRONGFITURL + "sLoginAndroid");
            Log.i(TAG, "URL: " + url.toString());
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
            URL url = new URL(STRONGFITURL + "sGetTodosAlimentos");
            Log.i(TAG, url.toString());
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
            JSONArray jsonArray = new JSONArray(response.toString());
            JSONObject alimentosJsonObject;

            for (int i=0; i<jsonArray.length(); i++) {
                alimentosJsonObject = (JSONObject) jsonArray.get(i);
                Alimento alimento = new Alimento();

                alimento.setAlimentoID(alimentosJsonObject.getInt("alimentoID"));
                alimento.setName(alimentosJsonObject.getString("name"));
                alimento.setCalories(alimentosJsonObject.getString("calories"));
                alimento.setLipidos(alimentosJsonObject.getString("lipidos"));
                alimento.setCarbohidratos(alimentosJsonObject.getString("carbohidratos"));
                alimento.setProteinas(alimentosJsonObject.getString("proteinas"));
                alimento.setAlimentoTipo(alimentosJsonObject.getInt("alimentoTipo"));

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
    public String registrarAlimento(int idPaciente, int idAlimento, float gramos, int day, int month, int year, int tipoComida){
        HttpURLConnection httpConnection = null;
        BufferedReader bufferedReader = null;
        StringBuilder response = null;
        try {
            String postParameters = "idPaciente=" + idPaciente + "&idAlimento=" + idAlimento
                    + "&gramos=" + gramos + "&day=" + day + "&month=" + month + "&year=" + year
                    + "&tipoComida=" + tipoComida;
            Log.i(TAG, "ParametrosPost: " + postParameters);

            URL url = new URL(STRONGFITURL + "sRegistrarAlimentoAndroid");
            Log.i(TAG, "URL: " + url.toString());
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

    public Map<String, String> getDatosPaciente(String correo, String contra){
        HttpURLConnection httpConnection = null;
        BufferedReader bufferedReader = null;
        StringBuilder response = null;
        Map<String, String> datosPaciente = new HashMap<>();
        try {
            String postParameters = "correo=" + correo + "&contra=" + contra;
            Log.i(TAG, "ParametrosPost: " + postParameters);

            URL url = new URL(STRONGFITURL + "sGetDatospaciente");
            Log.i(TAG, "URL: " + url.toString());
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
            JSONObject pacienteJsonObject = new JSONObject(response.toString());
            datosPaciente.put("nombre", pacienteJsonObject.getString("nombre"));
            datosPaciente.put("idPaciente", pacienteJsonObject.getString("idPaciente"));

        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (httpConnection != null) {
                httpConnection.disconnect();
            }
        }
        return datosPaciente;
    }
}
