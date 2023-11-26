package com.example.manoamiga;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class Detalles_sv extends AppCompatActivity {

    String uidPv;
    String uidCt;
    DatabaseReference refReserva;
    FirebaseAuth auth;
    FirebaseUser user;
    DatabaseReference ref,ref2,refFavorites;
    TextView textViewNombre;
    TextView textViewPrecio;
    TextView textViewDescripcion;
    Button btnReservar;
    Button btnQuitarReserva;
    Button btnQuitarMegusta;
    Button btnMegusta;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_sv);

        Intent intent = getIntent();
        uidPv = intent.getStringExtra("uid");

        ref = FirebaseDatabase.getInstance().getReference().child("usuarios").child("servicios").child(uidPv);


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        uidCt = user.getUid();
        ref2 = FirebaseDatabase.getInstance().getReference().child("usuarios").child("clientes").child(uidCt);
        refReserva = FirebaseDatabase.getInstance().getReference().child("reservas");
        refFavorites = FirebaseDatabase.getInstance().getReference().child("favoritos").child("clientes").child(uidCt).child(uidPv);

        textViewNombre = findViewById(R.id.textViewNombreServicio);
        textViewPrecio = findViewById(R.id.textViewPrecio);
        textViewDescripcion = findViewById(R.id.textViewDescripcion);
        btnReservar = findViewById(R.id.btnReservar);
        btnQuitarReserva = findViewById(R.id.btnQuitarReserva);
        btnQuitarMegusta = findViewById(R.id.btnQuitarMeGusta);
        btnMegusta = findViewById(R.id.btnMeGusta);
        textos();
        verificarReserva();
        verificarMegusta();
    }

    public void reserva(View view) {
        reservaDatosServicio();
        reservaDatosCliente();

        Toast.makeText(this, "Reserva realizada", Toast.LENGTH_SHORT).show();
        verificarReserva();
        verificarMegusta();
    }
    public void verificarReserva(){
        refReserva.child("clientes").child(uidCt).child(uidPv).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    btnReservar.setEnabled(false);
                    btnQuitarReserva.setEnabled(true);
                }else{
                    btnReservar.setEnabled(true);
                    btnQuitarReserva.setEnabled(false);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void verificarMegusta(){

        refFavorites.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    btnMegusta.setEnabled(false);
                    btnQuitarMegusta.setEnabled(true);
                }else{
                    btnMegusta.setEnabled(true);
                    btnQuitarMegusta.setEnabled(false);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void textos(){ref.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {
            if (dataSnapshot.exists()) {
                String nombre = dataSnapshot.child("servicio").getValue().toString();
                String precio = dataSnapshot.child("precio").getValue().toString();
                String descripcion = dataSnapshot.child("descripcion").getValue().toString();
                textViewNombre.setText(nombre);
                textViewPrecio.setText("Precio del servicio: "+precio);
                textViewDescripcion.setText("Descripcion del servicio: "+descripcion);
            }
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    });
    }
    public void reservaDatosServicio() {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String nombre = dataSnapshot.child("servicio").getValue().toString();
                    String precio = dataSnapshot.child("precio").getValue().toString();
                    String descripcion = dataSnapshot.child("descripcion").getValue().toString();
                    Map<String, Object> map = new HashMap<>();
                    map.put("servicio", nombre);
                    map.put("precio", precio);
                    map.put("descripcion", descripcion);
                    refReserva.child("clientes").child(uidCt).child(uidPv).setValue(map);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void reservaDatosCliente() {
        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    String nombre = dataSnapshot.child("nombre").getValue().toString();
                    String apellido = dataSnapshot.child("apellido").getValue().toString();
                    String telefono = dataSnapshot.child("telefono").getValue().toString();
                    Map<String, Object> map = new HashMap<>();
                    map.put("nombre", nombre);
                    map.put("apellido", apellido);
                    map.put("telefono", telefono);
                    refReserva.child("proveedores").child(uidPv).child(uidCt).setValue(map);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void megusta(View view) {
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Solo agrega a favoritos si aún no está en la lista
                    String nombre = dataSnapshot.child("servicio").getValue().toString();
                    String precio = dataSnapshot.child("precio").getValue().toString();
                    String descripcion = dataSnapshot.child("descripcion").getValue().toString();
                    Map<String, Object> map = new HashMap<>();
                    map.put("servicio", nombre);
                    map.put("precio", precio);
                    map.put("descripcion", descripcion);
                    refFavorites.setValue(map);
                    Toast.makeText(Detalles_sv.this, "Servicio agregado a favoritos", Toast.LENGTH_SHORT).show();
                    verificarMegusta();
                } else {
                    verificarMegusta();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error al acceder a los datos
            }
        });
    }
    public void quitar_megusta(View view) {

        refFavorites.removeValue();
        Toast.makeText(this, "Servicio eliminado de favoritos", Toast.LENGTH_SHORT).show();
    }
    public void quitar_reserva(View view) {
        DatabaseReference refPV = FirebaseDatabase.getInstance().getReference().child("reservas").child("proveedores").child(uidPv).child(uidCt);
        DatabaseReference refCT = FirebaseDatabase.getInstance().getReference().child("reservas").child("clientes").child(uidCt).child(uidPv);
        refPV.removeValue();
        refCT.removeValue();
        Toast.makeText(this, "Reserva eliminada", Toast.LENGTH_SHORT).show();
    }

    public void mensaje(View view) {
        Intent intent2 = new Intent(this.getApplicationContext(), Chat.class);
        intent2.putExtra("uid", uidPv);
        startActivity(intent2);
    }
}


