package com.example.manoamiga;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Detalles_ct extends AppCompatActivity {

    String uidPv;
    String uidCt;
    DatabaseReference refSoli,refData,refSoli2,refSoli3;
    FirebaseAuth auth;
    FirebaseUser user;
    TextView textViewId;
    TextView textViewNombre;
    TextView textViewApellido;
    TextView textViewTelefono;
    Button btnAceptar;
    Button btnRechazar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_ct);

        Intent intent = getIntent();
        uidCt = intent.getStringExtra("uid");
        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        uidPv = user.getUid();

        refSoli = FirebaseDatabase.getInstance().getReference().child("reservas");
        refData = FirebaseDatabase.getInstance().getReference().child("usuarios").child("clientes").child(uidCt);



        textViewId = findViewById(R.id.textViewId);
        textViewNombre = findViewById(R.id.textViewNombre);
        textViewApellido = findViewById(R.id.textViewApellido);
        textViewTelefono = findViewById(R.id.textViewTelefono);
        btnAceptar = findViewById(R.id.btnAceptar);
        btnRechazar = findViewById(R.id.btnRechazar);
        textos();
        validar();
    }

    public void textos(){
        refData.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nombre = dataSnapshot.child("nombre").getValue().toString();
                String apellido = dataSnapshot.child("apellido").getValue().toString();
                String telefono = dataSnapshot.child("telefono").getValue().toString();

                textViewId.setText(uidCt);
                textViewNombre.setText(nombre);
                textViewApellido.setText(apellido);
                textViewTelefono.setText(telefono);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void aceptar(View v){
        refSoli2 = refSoli.child("clientes").child(uidCt).child(uidPv);
        refSoli3 = refSoli.child("proveedores").child(uidPv).child(uidCt);

        refSoli2.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    refSoli3.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Detalles_ct.this, "Solicitud aceptada", Toast.LENGTH_SHORT).show();
                            } else {
                                // Manejar el caso en que la eliminación de refSoli3 no fue exitosa
                            }
                        }
                    });
                } else {
                    // Manejar el caso en que la eliminación de refSoli2 no fue exitosa
                }
            }
        });
        validar();
    }


    public void rechazar(View v) {
        refSoli2 = refSoli.child("clientes").child(uidCt).child(uidPv);
        refSoli3 = refSoli.child("proveedores").child(uidPv).child(uidCt);

        refSoli2.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    refSoli3.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(Detalles_ct.this, "Solicitud rechazada", Toast.LENGTH_SHORT).show();
                            } else {
                                // Manejar el caso en que la eliminación de refSoli3 no fue exitosa
                            }
                        }
                    });
                } else {
                    // Manejar el caso en que la eliminación de refSoli2 no fue exitosa
                }
            }
        });
        validar();
    }


    public void validar(){
        refSoli.child("clientes").child(uidCt).child(uidPv).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    btnAceptar.setEnabled(true);
                    btnRechazar.setEnabled(true);
                } else {
                    btnAceptar.setEnabled(false);
                    btnRechazar.setEnabled(false);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}