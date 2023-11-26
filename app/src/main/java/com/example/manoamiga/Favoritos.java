package com.example.manoamiga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.manoamiga.Adapter.AdapterServicio;
import com.example.manoamiga.pogo.servicio;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Favoritos extends AppCompatActivity {

    DatabaseReference favoritosRef;
    ArrayList<servicio> list;
    RecyclerView recyclerView;
    SearchView searchView;
    AdapterServicio adapterServicio;
    LinearLayoutManager linearLayoutManager;
    FirebaseAuth mAuth;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoritos);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        favoritosRef = FirebaseDatabase.getInstance().getReference()
                .child("favoritos").child("clientes").child(userId);
        searchView = findViewById(R.id.buscarSv);
        recyclerView = findViewById(R.id.serviciosRv);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapterServicio = new AdapterServicio(list);
        recyclerView.setAdapter(adapterServicio);

        favoritosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Limpiar la lista antes de agregar nuevos datos
                if (dataSnapshot.exists()) {
                    List<servicio> serviciosList = new ArrayList<>(); // Crear nueva lista
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        servicio servicio = dataSnapshot1.getValue(servicio.class);
                        String servicioId = dataSnapshot1.getKey();
                        servicio.setId(servicioId);
                        serviciosList.add(servicio);

                        list.clear(); // Limpiar la lista antes de agregar nuevos datos
                        list.addAll(serviciosList); // Agregar los nuevos datos
                        adapterServicio.filterList(serviciosList); // Notificar al adaptador de cambios
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) { // Cuando se presiona el botón de búsqueda
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) { // Cuando se cambia el texto
                adapterServicio.filterList(buscar(newText)); // Filtrar el adaptador
                return true;
            }
        });
    }

    private List<servicio> buscar(String str) {
        List<servicio> serviciosList = new ArrayList<>();
        for (servicio servicio : list) {
            if (servicio.getServicio().toLowerCase().contains(str.toLowerCase())) {
                serviciosList.add(servicio);
            }
        }
        return serviciosList;
    }
}