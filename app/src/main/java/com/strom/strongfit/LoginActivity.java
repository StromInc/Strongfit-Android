package com.strom.strongfit;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class LoginActivity extends ActionBarActivity {
    private EditText input_email;
    private EditText input_password;
    private String password, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        input_email = (EditText) findViewById(R.id.input_email);
        input_password = (EditText) findViewById(R.id.input_password);
    }

    public void onClickEntrar(View view) {
        email = input_email.getText().toString();
        password = input_password.getText().toString();
        Intent intent;

        if (email.equals("paciente@gmail.com") && password.equals("123")) {
            Toast.makeText(this, "Entrar", Toast.LENGTH_SHORT).show(); //Un pequeño mensaje
            finish();
            intent = new Intent(this, MainPacienteActivity.class);
            startActivity(intent);

        }else {
            if (email.equals("medico@gmail.com") && password.equals("123")) {
                finish(); //Esto cierra al actividad actual
                intent = new Intent(this, MainMedicoActivity.class); //Con esto abrimos una nueva actividad
                startActivity(intent);
            }else{
                Toast.makeText(this, "paciente@gmail.com o medico@gmail.com y 123", Toast.LENGTH_SHORT).show();
            }
        }
    }
    public void onClickRegistro(View view){
        Intent intent = new Intent(this, RegistroActivity.class);
        startActivity(intent);
    }
}
