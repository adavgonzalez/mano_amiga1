package com.example.manoamiga;

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.FirebaseApp;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import android.widget.Toast;
import com.google.firebase.firestore.FirebaseFirestore;

public class Inicio extends AppCompatActivity {

    private EditText correo_txt,contra_txt;
    private TextView info_txt;
    Button btn_inicio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicio);
        FirebaseApp.initializeApp(this);


        correo_txt = findViewById(R.id.correo_txt);
        contra_txt = findViewById(R.id.contra_txt);
        info_txt   = findViewById(R.id.Info_txt);

    }



    public void register(View v){
        Intent intento = new Intent(this,Sele_Registro.class);
        startActivity(intento);
    }
}