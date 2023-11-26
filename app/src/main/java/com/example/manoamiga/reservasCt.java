package com.example.manoamiga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.os.Bundle;
import androidx.appcompat.widget.SearchView;

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

public class reservasCt extends AppCompatActivity {

    DatabaseReference reservasClienteRef;
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
        setContentView(R.layout.activity_reservas_ct);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        reservasClienteRef = FirebaseDatabase.getInstance().getReference()
                .child("reservas").child("clientes").child(userId);

        searchView = findViewById(R.id.buscarSV);
        recyclerView = findViewById(R.id.serviciosRV);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapterServicio = new AdapterServicio(list);
        recyclerView.setAdapter(adapterServicio);



        reservasClienteRef.addValueEventListener(new ValueEventListener() {
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

                        // Aquí obtenemos el ID del proveedor directamente desde el servicio
                        // Ajusta esto según tu modelo de datos

                        list.clear();
                        list.add(servicio);
                        adapterServicio.filterList(serviciosList); // Asegurarse de que se muestren desde el principio
                    }
                }
                 // Notificar cambios
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Manejar el error
            }
        });

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

    private List<servicio> buscar(String texto) {
        List<servicio> serviciosList = new ArrayList<>();
        for (servicio servicio : list) {
            if (servicio.getServicio().toLowerCase().contains(texto.toLowerCase())) {
                serviciosList.add(servicio);
            }
        }
        return serviciosList;
    }
}

