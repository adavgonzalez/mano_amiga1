package com.example.manoamiga;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Sele_Registro extends AppCompatActivity {

    private Button inicio_btn, cliente_btn, proveedor_btn;

        @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sele_registro);

        inicio_btn = findViewById(R.id.inicio_button);
        cliente_btn = findViewById(R.id.cliente_button);
        proveedor_btn = findViewById(R.id.pv_btn);


    }

    public void inicio(View v){

        Intent intento = new Intent(this, Inicio.class);
        startActivity(intento);
    }

    public void cliente(View v){

        Intent intento = new Intent(this, Registro_Cliente.class);
        startActivity(intento);
    }

    public void proveedor(View v) {

        Intent intento;
        intento = new Intent(this,Registro_Proveedor.class);
        startActivity(intento);
    }
}
