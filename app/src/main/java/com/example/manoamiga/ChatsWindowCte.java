package com.example.manoamiga;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.manoamiga.Adapter.AdapterChats;
import com.example.manoamiga.Adapter.AdapterChats2;
import com.example.manoamiga.pogo.chats;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ChatsWindowCte extends AppCompatActivity {

    DatabaseReference databaseReference;
    ArrayList<chats> list;
    RecyclerView recyclerView;
    AdapterChats adapterChats;
    LinearLayoutManager linearLayoutManager;
    FirebaseAuth auth;
    FirebaseUser user;
    String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chats_window_cte);

        // Inicializar variables
        recyclerView = findViewById(R.id.chatsRecyclerView);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        adapterChats = new AdapterChats(list);
        recyclerView.setAdapter(adapterChats);

        auth = FirebaseAuth.getInstance();
        user = auth.getCurrentUser();
        uid = user.getUid();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("vista").child("clientes").child(uid);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    List<chats> chatsList = new ArrayList<>(); // Crear nueva lista
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                        chats chat = dataSnapshot1.getValue(chats.class);
                        String chatId = dataSnapshot1.getKey();
                        chat.setId(chatId);
                        chatsList.add(chat);
                    }
                    list.clear();
                    list.addAll(chatsList);
                    adapterChats.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Error
            }
        });
    }
}