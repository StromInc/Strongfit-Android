package com.strom.strongfit;

import android.app.DatePickerDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.strom.strongfit.db.DBOperations;
import com.strom.strongfit.models.Alimento;
import com.strom.strongfit.utils.ConectarHTTP;
import com.strom.strongfit.utils.SessionManager;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class AlimentoActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    Toolbar toolbar;  //La barrita
    private TextView textCalorias, textLipidos, textProteinas, textCarbohidratos;
    private static final String TAG = AlimentoActivity.class.getSimpleName();
    private Spinner combo;
    int idAlimento;
    private EditText cantidad;
    private SimpleDateFormat fechaFormato;
    private String fecha;
    private Button botonFecha;
    private Date fechaCambia;
    private Calendar calendario;
    private int dia;
    private int mes;
    private int myYear;
    private int comida = 1;
    private float gramos = 100;
    private int idPaciente;
    private SessionManager sessionManager;
    private ConectarHTTP conectarHTTP;
    private DBOperations dbOperations;
    private Alimento miAlimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alimento);
        setToolBar();
        dbOperations = new DBOperations(this);
        sessionManager = new SessionManager(this);
        idPaciente = sessionManager.getIDPaciente();
        Bundle bundle = this.getIntent().getExtras();
        idAlimento = bundle.getInt("idAlimento", 0);
        miAlimento = dbOperations.getDatosAlimento(idAlimento);
        String calorias = miAlimento.getCalories();
        String carbohidratos = miAlimento.getCarbohidratos();
        String lipidos = miAlimento.getLipidos();
        String proteinas = miAlimento.getProteinas();
        String nombreAlimento = miAlimento.getName();

        setTitle(nombreAlimento);
        botonFecha = (Button) findViewById(R.id.btn_fecha);
        cantidad = (EditText) findViewById(R.id.input_cantidad);
        combo = (Spinner) findViewById(R.id.spinner_comida);
        textCalorias = (TextView) findViewById(R.id.text_alimentoCalorias);
        textLipidos = (TextView) findViewById(R.id.text_alimentoLipidos);
        textProteinas = (TextView) findViewById(R.id.text_alimentoProteinas);
        textCarbohidratos = (TextView) findViewById(R.id.text_alimentoCarbohidratos);

        textCalorias.setText("Calorias: " + calorias + " kcal");
        textLipidos.setText("Lipidos: " + lipidos);
        textProteinas.setText("Proteinas: " + proteinas);
        textCarbohidratos.setText("Carbohidratos: " + carbohidratos);
        Calendar fechaSeleccionada = Calendar.getInstance();
        fechaFormato = new SimpleDateFormat("dd/MM/yy");

        Date fechaHoy = fechaSeleccionada.getTime();
        dia = fechaSeleccionada.get(Calendar.DATE);
        mes = fechaSeleccionada.get(Calendar.MONTH);
        myYear = fechaSeleccionada.get(Calendar.YEAR);
        fecha = fechaFormato.format(fechaHoy);
        botonFecha.setText(fecha);
        conectarHTTP = new ConectarHTTP();
    }

    public void setToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void onClickCambiarFecha(View view) {
        Calendar calendario = parseCalendar(botonFecha.getText(), fechaFormato);
        new DatePickerDialog(this, this, calendario.get(Calendar.YEAR),
                calendario.get(Calendar.MONTH),
                calendario.get(Calendar.DAY_OF_MONTH)).show();
    }

    private Calendar parseCalendar(CharSequence text, SimpleDateFormat fechaFormat2) {
        try {
            fechaCambia = fechaFormat2.parse(text.toString());
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        Calendar calendario = Calendar.getInstance();

        calendario.setTime(fechaCambia);
        return calendario;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alimento, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            comida = combo.getSelectedItemPosition() + 1;
            gramos = Float.parseFloat(cantidad.getText().toString());
            Log.i(TAG, "Datos a enviar comida: " + comida + " idAlimento " + idAlimento + " cantidad "
                    + gramos + " year: " + myYear + " mes " + mes + " dia " + dia);
            RegistrarAlimentoTask hilo = new RegistrarAlimentoTask();
            hilo.execute();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
        calendario = Calendar.getInstance();
        calendario.set(Calendar.YEAR, year);
        calendario.set(Calendar.MONTH, monthOfYear);
        calendario.set(Calendar.DAY_OF_MONTH, dayOfMonth);
        myYear = year;
        mes = monthOfYear;
        dia = dayOfMonth;
        fecha = fechaFormato.format(calendario.getTime());
        botonFecha.setText(fecha);
    }

    class RegistrarAlimentoTask extends AsyncTask<Object, Void, String> {

        @Override
        protected String doInBackground(Object... params) {
            ConnectivityManager connMgr = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
            if (networkInfo != null && networkInfo.isConnected()) {
                String respuesta = conectarHTTP.registrarAlimento(idPaciente, idAlimento, gramos, dia, mes, myYear, comida);
                return respuesta;
            } else {
                Log.e(TAG, "No hay red");
            }
            return "Algo raro ocurrio";
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s.equals("ok")) {
                Toast.makeText(getApplicationContext(), "Alimento agregado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "No se pudo agregar, intentelo mas tarde", Toast.LENGTH_LONG).show();
            }
        }
    }
}