package com.strom.strongfit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


public class AlimentoActivity extends AppCompatActivity {
    Toolbar toolbar;  //La barrita
    private TextView textCalorias, textLipidos, textProteinas, textCarbohidratos;
    private static final String TAG = AlimentoActivity.class.getSimpleName();
    private Spinner combo;
    int idAlimento;
    private EditText cantidad;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alimento);
        setToolBar();

        Bundle bundle = this.getIntent().getExtras();
        String nombreAlimento = bundle.getString("nombreAlimento");
        idAlimento = bundle.getInt("idAlimento", 0);
        float calorias = bundle.getFloat("calorias", 0);
        float carbohidratos = bundle.getFloat("carbohidratos", 0);
        float lipidos = bundle.getFloat("lipidos", 0);
        float proteinas = bundle.getFloat("proteinas", 0);

        setTitle(nombreAlimento);
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
    }

    public void setToolBar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_alimento, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add) {
            int comida = combo.getSelectedItemPosition();
            float gramos = Float.parseFloat(cantidad.getText().toString());
            Log.i(TAG, "Datos a enviar: " + comida + " idAlimento " + idAlimento + " cantidad " + gramos);
            Toast.makeText(this, "Atiendame", Toast.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
