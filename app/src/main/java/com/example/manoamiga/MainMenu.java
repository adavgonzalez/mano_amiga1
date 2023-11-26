package com.example.manoamiga;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);
    }

    public void busqueda(View v) {
        Intent intent = new Intent(this, BuscarServicios.class);
        startActivity(intent);
    }

    public void settings(View v) {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }

    public void reservas(View v) {
        Intent intent = new Intent(this, reservasCt.class);
        startActivity(intent);
    }

    public void favoritos(View v) {
        Intent intent = new Intent(this, Favoritos.class);
        startActivity(intent);
    }

    public void nosotros(View v) {
        Intent intent = new Intent(this, Nosotros.class);
        startActivity(intent);
    }

    public void misChats(View v){
        Intent intent = new Intent(this, ChatsWindowCte.class);
        startActivity(intent);
    }
}