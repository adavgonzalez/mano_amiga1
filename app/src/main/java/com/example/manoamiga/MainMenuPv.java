package com.example.manoamiga;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainMenuPv extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseUser user;
    String uid;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_pv);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        uid = user.getUid();
        ref = FirebaseDatabase.getInstance().getReference().child("usuarios").child("servicios").child(uid);

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    Intent intent = new Intent(MainMenuPv.this, Registro_Detalles.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void settings(View v) {
        Intent intent = new Intent(this, Ajustes_pv.class);
        startActivity(intent);
    }

    public void busqueda(View v) {
        Intent intent = new Intent(this, Solicitudes.class);
        startActivity(intent);
    }

    public void nosotros(View v) {
        Intent intent = new Intent(this, Nosotros.class);
        startActivity(intent);
    }
    public void escribir(View v) {
        Intent intent = new Intent(this, ChatsWindowPve.class);
        startActivity(intent);
    }
}