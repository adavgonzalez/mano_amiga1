package com.example.manoamiga;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.manoamiga.modelos.Cliente;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.concurrent.atomic.AtomicInteger;

public class Registro_Cliente extends AppCompatActivity {

    private static final AtomicInteger autoIncrement = new AtomicInteger(1);

    public static int getNextId() {
        return autoIncrement.getAndIncrement();
    }

    private EditText correocliente_txt,
            nombrecliente_txt,
            apellidocliente_txt,
            contraseñacliente_txt,
            telefonocliente_txt;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_cliente);

        correocliente_txt = findViewById(R.id.correocliente_txt);
        contraseñacliente_txt = findViewById(R.id.contraseñacliente_txt);
        nombrecliente_txt = findViewById(R.id.nombrecliente_txt);
        apellidocliente_txt = findViewById(R.id.apellidocliente_txt);
        telefonocliente_txt = findViewById(R.id.telefonocliente_txt);

    }


    public void volver(View v){
        Intent intento = new Intent(this,Inicio.class);
        startActivity(intento);
    }

    public void register_client(View v){
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("Clientes");
        Cliente client = new Cliente(getNextId(),
                nombrecliente_txt.getText().toString()    ,
                apellidocliente_txt.getText().toString()  ,
                correocliente_txt.getText().toString()    ,
                contraseñacliente_txt.getText().toString(),
                telefonocliente_txt.getText().toString()) ;
        myref.push().setValue(client);
    }




}