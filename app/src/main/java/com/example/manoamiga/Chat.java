package com.example.manoamiga;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.manoamiga.Adapter.AdapterMensajes;
import com.example.manoamiga.pogo.mensaje;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Chat extends AppCompatActivity {

    TextView textViewNombre;
    EditText editTextMessage;
    String uidPv, uidCt, mensajeId, nombrePve, nombreCte;
    DatabaseReference ref1, ref2, ref3, ref4, ref5, ref6;
    FirebaseAuth auth;
    FirebaseUser user;
    RecyclerView recyclerView;
    AdapterMensajes adapterMensajes;
    LinearLayoutManager linearLayoutManager;
    ArrayList<mensaje> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        textViewNombre = findViewById(R.id.textViewNombre);
        editTextMessage = findViewById(R.id.editTextMessage);
        recyclerView = findViewById(R.id.recyclerViewMessages);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapterMensajes = new AdapterMensajes(list);
        recyclerView.setAdapter(adapterMensajes);



        Intent intent = getIntent();
        uidPv = intent.getStringExtra("uid");


        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        uidCt = user.getUid();


        ref1 = FirebaseDatabase.getInstance().getReference().child("usuarios").child("servicios").child(uidPv);
        ref2 = FirebaseDatabase.getInstance().getReference().child("usuarios").child("clientes").child(uidCt);
        ref3 = FirebaseDatabase.getInstance().getReference().child("chat").child("clientes").child(uidCt).child(uidPv);
        ref4 = FirebaseDatabase.getInstance().getReference().child("chat").child("proveedores").child(uidPv).child(uidCt);
        ref5 = FirebaseDatabase.getInstance().getReference().child("vista").child("clientes").child(uidCt).child(uidPv);
        ref6 = FirebaseDatabase.getInstance().getReference().child("vista").child("proveedores").child(uidPv).child(uidCt);


        ref3.orderByChild("timestamp").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    List<mensaje> mensajesList = new ArrayList<>();
                    for(DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                        mensaje mensaje = dataSnapshot1.getValue(mensaje.class);
                        mensajeId = dataSnapshot1.getKey();
                        mensaje.setId(mensajeId);
                        mensajesList.add(mensaje);
                    }
                    list.clear();
                    list.addAll(mensajesList);
                    adapterMensajes.notifyDataSetChanged();  // Notifica al adaptador que los datos han cambiado
                    linearLayoutManager.scrollToPosition(list.size() - 1);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Manejar errores de cancelación de lectura de datos
            }
        });

        name();


    }

    public void name(){
        ref1.addListenerForSingleValueEvent(new ValueEventListener() {


            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String nombre = snapshot.child("servicio").getValue().toString();
                    textViewNombre.setText(nombre);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void EnviarMensaje(View v){



        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String nombre = snapshot.child("nombre").getValue().toString();
                    long timestamp = System.currentTimeMillis();
                    String mensaje = editTextMessage.getText().toString();

                    Map<String, Object> map = new HashMap<>();
                    map.put("nombre", nombre);
                    map.put("mensaje", mensaje);
                    map.put("timestamp", timestamp);
                    if(!mensaje.equals("")){
                        ref3.push().setValue(map);
                        ref4.push().setValue(map);
                        nombres();
                        editTextMessage.setText("");
                        linearLayoutManager.scrollToPosition(list.size() - 1);
                    }else{
                        Toast.makeText(Chat.this, "No se puede enviar un mensaje vacío", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void nombres(){
        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    nombrePve = snapshot.child("servicio").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ref2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    nombreCte = snapshot.child("nombre").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ref5.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    long timestamp = System.currentTimeMillis();
                    Map<String, Object> map = new HashMap<>();
                    map.put("nombre", nombrePve);
                    map.put("timestamp", timestamp);
                    ref5.setValue(map);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        ref6.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(!snapshot.exists()){
                    long timestamp = System.currentTimeMillis();
                    Map<String, Object> map = new HashMap<>();
                    map.put("nombre", nombreCte);
                    map.put("timestamp", timestamp);
                    ref6.setValue(map);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


}