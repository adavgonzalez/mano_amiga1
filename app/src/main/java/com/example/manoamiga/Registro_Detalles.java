package com.example.manoamiga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Registro_Detalles extends AppCompatActivity {
    int id_pv;
    EditText descripcion_txt;
    TextView titulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_detalles);

        // Obtener datos del Intent
        Bundle datos = getIntent().getExtras();
        if (datos != null) {
            id_pv = datos.getInt("id_pv");
        }

        descripcion_txt = findViewById(R.id.descripcion_txt);
        titulo = findViewById(R.id.titulo_tv6);

    }

    public void volver(View v){
        Intent intento = new Intent(this, Registro_Proveedor.class);
        startActivity(intento);
    }

    public void detalles_rg(View v){
        titulo.setText(String.valueOf(id_pv));
        Intent intento2 = new Intent(this,Inicio.class);
        intento2.putExtra("id_pv",id_pv);
        startActivity(intento2);
    }
}

