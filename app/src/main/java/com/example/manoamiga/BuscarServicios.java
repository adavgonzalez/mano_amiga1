package com.example.manoamiga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;
import android.widget.TextView;

import com.example.manoamiga.Adapter.AdapterServicio;
import com.example.manoamiga.pogo.servicio;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class BuscarServicios extends AppCompatActivity {



    // Variables
    DatabaseReference databaseReference;
    ArrayList<servicio> list;
    RecyclerView recyclerView;
    SearchView searchView;
    AdapterServicio adapterServicio;
    LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buscar_servicios);

        // Inicializar variables
        databaseReference = FirebaseDatabase.getInstance().getReference().child("usuarios").child("servicios");
        recyclerView = findViewById(R.id.serviciosRecyclerView);
        searchView = findViewById(R.id.buscarServiciosSearchView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapterServicio = new AdapterServicio(list);
        recyclerView.setAdapter(adapterServicio);

        // Buscar servicios
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<servicio> serviciosList = new ArrayList<>(); // Crear nueva lista
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        servicio servicio = dataSnapshot1.getValue(servicio.class);
                        String servicioId = dataSnapshot1.getKey();
                        servicio.setId(servicioId);
                        serviciosList.add(servicio);
                    }
                    list.clear(); // Limpiar la lista antes de agregar nuevos datos
                    list.addAll(serviciosList); // Agregar los nuevos datos
                    adapterServicio.filterList(serviciosList); // Asegurarse de que se muestren desde el principio
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error
            }
        });

        // Configurar el SearchView
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapterServicio.filterList(buscar(newText));
                return true;
            }
        });
    }

    private List<servicio> buscar(String str) {
        List<servicio> myList = new ArrayList<>();
        for (servicio object : list) {
            if (object.getServicio().toLowerCase().contains(str.toLowerCase())) {
                myList.add(object);
            }
        }
        return myList;
    }
}
