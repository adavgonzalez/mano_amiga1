package com.example.manoamiga;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.manoamiga.modelos.Proveedor;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.concurrent.atomic.AtomicInteger;

public class Registro_Proveedor extends AppCompatActivity {

    int id_pv = 0;
    EditText NombreProovedor,CorreoProveedor,ContraseñaProveedor,TelefonoProveedor,DireccionProveedor,NmServicio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_proveedor);

        Bundle datos = getIntent().getExtras();
        if (datos != null) {
            id_pv = datos.getInt("id_pv");
        }

            NombreProovedor = findViewById(R.id.nombreproveedor_txt);
            CorreoProveedor = findViewById(R.id.correoproveedor_txt);
            ContraseñaProveedor = findViewById(R.id.contraseñaproveedor_txt);
            TelefonoProveedor = findViewById(R.id.telefonoproveedor_txt);
            DireccionProveedor = findViewById(R.id.direccionproveedor_txt);
            NmServicio = findViewById(R.id.nombreservicio_txt);

    }

    public void volver_(View v){
        Intent intento = new Intent(this,Inicio.class);
        startActivity(intento);
    }

    public void pv_registrar(View v){
        id_pv = id_pv + 1;
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myref = database.getReference("Proveedores");
        Proveedor proveedor = new Proveedor(id_pv,
                NombreProovedor.getText().toString()     ,
                TelefonoProveedor.getText().toString()   ,
                CorreoProveedor.getText().toString()     ,
                ContraseñaProveedor.getText().toString() ,
                DireccionProveedor.getText().toString()  ,
                NmServicio.getText().toString())         ;
        myref.push().setValue(proveedor);

        Intent intento = new Intent(this,Registro_Detalles.class);
        intento.putExtra("id_pv",id_pv);
        startActivity(intento);
    }
}