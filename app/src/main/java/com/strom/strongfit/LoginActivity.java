package com.strom.strongfit;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class LoginActivity extends ActionBarActivity {
    private EditText input_email;
    private EditText input_password;
    private TextView text_registro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        input_email = (EditText) findViewById(R.id.input_email);
        input_password = (EditText) findViewById(R.id.input_password);
        text_registro = (TextView) findViewById(R.id.text_registro);
    }

    public void onClickEntrar(View view) {
        Toast.makeText(this, "Entrar", Toast.LENGTH_SHORT).show(); //Un pequeño mensaje
    }
    public void onClickRegistro(View view){
        Toast.makeText(this, "Registro", Toast.LENGTH_SHORT).show();
    }
}
