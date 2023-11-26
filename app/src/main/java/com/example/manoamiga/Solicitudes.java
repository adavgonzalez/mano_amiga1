package com.example.manoamiga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.manoamiga.Adapter.AdapterCliente;
import com.example.manoamiga.pogo.cliente;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Solicitudes extends AppCompatActivity {

    DatabaseReference solicitudRef;
    ArrayList<cliente> list;
    RecyclerView recyclerView;
    SearchView searchView;
    AdapterCliente adapterCliente;
    LinearLayoutManager linearLayoutManager;
    FirebaseAuth mAuth;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solicitudes);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        solicitudRef = FirebaseDatabase.getInstance().getReference()
                .child("reservas").child("proveedores").child(userId);
        searchView = findViewById(R.id.buscarCte);
        recyclerView = findViewById(R.id.clientesRv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapterCliente = new AdapterCliente(list);
        recyclerView.setAdapter(adapterCliente);


        solicitudRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Limpiar la lista antes de agregar nuevos datos
                if (dataSnapshot.exists()) {
                    List<cliente> clientesList = new ArrayList<>(); // Crear nueva lista
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        cliente cliente = dataSnapshot1.getValue(cliente.class);
                        String clienteId = dataSnapshot1.getKey();
                        cliente.setId(clienteId);
                        clientesList.add(cliente);

                        list.clear();
                        list.addAll(clientesList);
                        adapterCliente.filterList(clientesList);
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            adapterCliente.filterList(buscar(newText));
            return true;
        }
    });
    }

    private List<cliente> buscar(String texto) {
        List<cliente> clientesList = new ArrayList<>();
        for (cliente cliente : list) {
            if (cliente.getNombre().toLowerCase().contains(texto.toLowerCase())) {
                clientesList.add(cliente);
            }
        }
        return clientesList;
    }
}