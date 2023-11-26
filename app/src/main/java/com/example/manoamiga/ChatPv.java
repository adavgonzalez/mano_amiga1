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

public class ChatPv extends AppCompatActivity {

    TextView textViewNombre;
    EditText editTextMessage;
    String uidPv, uidCt, mensajeId;
    DatabaseReference ref1, ref2, ref3, ref4;
    FirebaseAuth auth;
    FirebaseUser user;
    RecyclerView recyclerView;
    AdapterMensajes adapterMensajes;
    LinearLayoutManager linearLayoutManager;
    ArrayList<mensaje> list;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_pv);

        textViewNombre = findViewById(R.id.ViewNombre);
        editTextMessage = findViewById(R.id.TextMessage);
        recyclerView = findViewById(R.id.ViewMessages);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapterMensajes = new AdapterMensajes(list);
        recyclerView.setAdapter(adapterMensajes);

        Intent intent = getIntent();
        uidCt = intent.getStringExtra("uid");

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        uidPv = user.getUid();

        ref1 = FirebaseDatabase.getInstance().getReference().child("usuarios").child("servicios").child(uidPv);
        ref2 = FirebaseDatabase.getInstance().getReference().child("usuarios").child("clientes").child(uidCt);
        ref3 = FirebaseDatabase.getInstance().getReference().child("chat").child("clientes").child(uidCt).child(uidPv);
        ref4 = FirebaseDatabase.getInstance().getReference().child("chat").child("proveedores").child(uidPv).child(uidCt);
        name();

        ref4.orderByChild("timestamp").addValueEventListener(new ValueEventListener() {
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
    }

    public void name(){
        ref2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String nombre = dataSnapshot.child("nombre").getValue().toString();
                textViewNombre.setText(nombre);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    public void EnviarMensaje(View view){
        ref1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    String nombre = snapshot.child("servicio").getValue().toString();
                    long timestamp = System.currentTimeMillis();
                    String mensaje = editTextMessage.getText().toString();

                    Map<String, Object> map = new HashMap<>();
                    map.put("nombre", nombre);
                    map.put("mensaje", mensaje);
                    map.put("timestamp", timestamp);
                    if(!mensaje.equals("")){
                        ref3.push().setValue(map);
                        ref4.push().setValue(map);
                        editTextMessage.setText("");
                        linearLayoutManager.scrollToPosition(list.size() - 1);
                    }else{
                        Toast.makeText(ChatPv.this, "No se puede enviar un mensaje vacío", Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    }
