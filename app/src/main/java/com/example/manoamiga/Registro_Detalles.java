package com.example.manoamiga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Registro_Detalles extends AppCompatActivity {

    EditText descripcion_txt, precio_txt, nmServicio;
    TextView titulo;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_detalles);
        mAuth = FirebaseAuth.getInstance();
        descripcion_txt = findViewById(R.id.descripcion_txt);
        titulo = findViewById(R.id.titulo_tv6);
        precio_txt = findViewById(R.id.precio_txt);
        nmServicio = findViewById(R.id.Servicio_txt);

        FirebaseApp.initializeApp(this);
    }

    public void detalles_rg(View v) {
        String descripcion = String.valueOf(descripcion_txt.getText());
        String precio = String.valueOf(precio_txt.getText());
        String servicio = String.valueOf(nmServicio.getText());
        String id = getIntent().getStringExtra("id");
        if (descripcion.isEmpty() || precio.isEmpty() || servicio.isEmpty()) {
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
        } else {


        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("usuarios").child("servicios");
        Map<String, Object> map = new HashMap<>();
        map.put("id", id);
        map.put("descripcion", descripcion);
        map.put("precio", precio);
        map.put("servicio", servicio);

        mDatabase.child(id).setValue(map).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                Intent intento = new Intent(this, Inicio.class);
                startActivity(intento);
            } else {
                Toast.makeText(this, "Error al registrar: " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                task.getException().printStackTrace(); // Log the stack trace for detailed error analysis
            }
        });
    }}
}
