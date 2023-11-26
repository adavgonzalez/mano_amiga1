package com.example.manoamiga;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Ajustes_pv extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    TextView idu;
    TextView nombrePve;
    TextView direccionPve;
     String uid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes_pv);

        FirebaseApp.initializeApp(this);

        idu = findViewById(R.id.uid);
        nombrePve = findViewById(R.id.nombre_pve);
        direccionPve = findViewById(R.id.direccionPv);
        idu.setText("Hola");

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();

        if (user == null) {
            try {
                FirebaseAuth.getInstance().signOut();
                redirectToLogin();
            } catch (Exception e) {
                e.printStackTrace();
                // Manejar la excepción según sea necesario
            }
        } else {
            uid = user.getUid();
            idu.setText("Id: " + uid);
            updateSettings();
        }

    }

    private void redirectToLogin() {
        Intent intent = new Intent(this, Inicio.class);
        startActivity(intent);
        finish();
    }

    public void logout(View v) {
        try {
            FirebaseAuth.getInstance().signOut();
            redirectToLogin();
        } catch (Exception e) {
            e.printStackTrace();
            // Manejar la excepción según sea necesario
        }
    }

    private void updateSettings() {
        Log.d("TAG", "updateSettings() called");
        if (user == null) {
            try {
                FirebaseAuth.getInstance().signOut();
                redirectToLogin();
            } catch (Exception e) {
                e.printStackTrace();
                // Manejar la excepción según sea necesario
            }
        } else {
            Log.d("TAG", "User is not null in updateSettings()");
            DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("usuarios").child("proveedores").child(uid);
            db.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        displayUserData(dataSnapshot);
                    } else {
                        Log.d("TAG", "DataSnapshot does not exist");
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e("TAG", "onCancelled: " + databaseError.getMessage());
                }
            });
        }
    }

    private void displayUserData(DataSnapshot dataSnapshot) {
        String nombre = dataSnapshot.child("nombre").getValue(String.class);
        String direccion = dataSnapshot.child("direccion").getValue(String.class);
        nombrePve.setText("Nombre: " + nombre);
        direccionPve.setText("Direccion: " + direccion);
        Log.d("TAG", "Nombre: " + nombre);
        Log.d("TAG", "Direccion: " + direccion);
    }
}