package com.example.manoamiga;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Inicio extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    private EditText correo_txt,contra_txt;
    private TextView info_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);

        correo_txt = findViewById(R.id.correo);
        contra_txt = findViewById(R.id.contra);
        info_txt   = findViewById(R.id.Info_txt);

    }


    @Override
    public void onStart() {
        super.onStart();
        login();
    }

    public void login(){
            if(user != null){

            String uid = user.getUid();
            DatabaseReference clienteref = FirebaseDatabase.getInstance().getReference().child("usuarios").child("clientes").child(uid);
            DatabaseReference proveedorref = FirebaseDatabase.getInstance().getReference().child("usuarios").child("proveedores").child(uid);

            clienteref.addListenerForSingleValueEvent(new ValueEventListener(){
                @Override
                public void onDataChange(@NonNull DataSnapshot ctsnapshot) {
                    if(ctsnapshot.exists()){
                        Intent intento = new Intent(Inicio.this, MainMenu.class);
                        startActivity(intento);
                        finish();
                    }
                    else{
                        proveedorref.addListenerForSingleValueEvent(new ValueEventListener(){
                            @Override
                            public void onDataChange(@NonNull DataSnapshot ptsnapshot) {
                                if(ptsnapshot.exists()){
                                    Intent intento = new Intent(Inicio.this, MainMenuPv.class);
                                    startActivity(intento);
                                    finish();
                                }
                                else{
                                    info_txt.setText("No se encontró el usuario");
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                                Toast.makeText(Inicio.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();

                            }
                        });
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Toast.makeText(Inicio.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();

                }
            });

    }}



    public void inicio(View v) {
        String correo = correo_txt.getText().toString().trim();
        String contraseña = contra_txt.getText().toString().trim();

        if (correo.isEmpty() || contraseña.isEmpty()) {
            Toast.makeText(this, "Por favor, llene todos los campos", Toast.LENGTH_SHORT).show();
            return;
        } else {
            mAuth.signInWithEmailAndPassword(correo, contraseña)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            String uid = user.getUid();

                            DatabaseReference proveedorRef = FirebaseDatabase.getInstance().getReference().child("usuarios").child("proveedores").child(uid);
                            DatabaseReference clienteRef = FirebaseDatabase.getInstance().getReference().child("usuarios").child("clientes").child(uid);

                            proveedorRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        // Es un proveedor
                                        Intent intento = new Intent(Inicio.this, MainMenuPv.class);
                                        startActivity(intento);
                                        finish();  // Terminar la actividad actual si es necesario
                                    } else {
                                        // No es un proveedor, verificamos si es un cliente
                                        clienteRef.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()) {
                                                    // Es un cliente
                                                    Intent intento = new Intent(Inicio.this, MainMenu.class);
                                                    startActivity(intento);
                                                    finish();  // Terminar la actividad actual si es necesario
                                                } else {
                                                    Toast.makeText(Inicio.this, "El usuario no existe", Toast.LENGTH_SHORT).show();
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                Toast.makeText(Inicio.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    Toast.makeText(Inicio.this, "Error al iniciar sesión", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            Toast.makeText(this, "Inicio de sesión fallido", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }
    public void register(View v){
        Intent intento = new Intent(this,Sele_Registro.class);
        startActivity(intento);
    }

    public void clienteLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Login successful
                        FirebaseUser user = mAuth.getCurrentUser();
                        // Now you can navigate to the client's home screen or perform other actions
                        // For example:
                        Intent intent = new Intent(this, MainMenu.class);
                        startActivity(intent);
                    } else {
                        // If sign-in fails, display a message to the user.
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void proveedorLogin(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Login successful
                        FirebaseUser user = mAuth.getCurrentUser();
                        // Now you can navigate to the provider's home screen or perform other actions
                        // For example:
                        Intent intent = new Intent(this, MainMenuPv.class);
                        startActivity(intent);
                    } else {
                        // If sign-in fails, display a message to the user.
                        Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                    }
                });
    }


}