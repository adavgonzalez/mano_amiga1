package com.example.manoamiga;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Settings extends AppCompatActivity {

    TextView tv7;
    FirebaseAuth auth;
    FirebaseUser user;
    TextView id;
    TextView nombre;
    TextView apellido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        id = findViewById(R.id.id);
        nombre = findViewById(R.id.nombre_clte);
        apellido = findViewById(R.id.apellido_clte);
        tv7 = findViewById(R.id.titulo_tv7);
        setTxts();
    }

    public void logout(View v) {
        try {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(this, Inicio.class);
            startActivity(intent);
            finish();
        } catch (Exception e) {
            e.printStackTrace();
            // Manejar la excepción según sea necesario
        }
    }

    public void setTxts() {
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        if (user == null) {
            Intent intent = new Intent(this, Inicio.class);
            startActivity(intent);
            finish();
        } else if (user != null) {
            String uid = user.getUid();
            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("usuarios").child("clientes").child(uid);
            db.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        // Los datos existen, puedes acceder a ellos
                        String nombre_ct = dataSnapshot.child("nombre").getValue(String.class);
                        String apellido_ct = dataSnapshot.child("apellido").getValue(String.class);


                        id.setText("ID: "+uid);
                        nombre.setText("Nombre: " +nombre_ct);
                        apellido.setText("Apellido: "+apellido_ct);

                    } else {
                        // Los datos no existen
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    // Manejar el error en caso de fallo al obtener los datos
                }
            });
        }
    }
    

}