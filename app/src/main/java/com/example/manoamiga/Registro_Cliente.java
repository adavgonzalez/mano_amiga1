package com.example.manoamiga;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Registro_Cliente extends AppCompatActivity {

    FirebaseAuth mAuth;

    private EditText correocliente_txt,
            nombrecliente_txt,
            apellidocliente_txt,
            contraseñacliente_txt,
            telefonocliente_txt;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_cliente);

        mAuth = FirebaseAuth.getInstance();

        correocliente_txt = findViewById(R.id.correo_usr);
        contraseñacliente_txt = findViewById(R.id.contra_usr);
        nombrecliente_txt = findViewById(R.id.nombrecliente_txt);
        apellidocliente_txt = findViewById(R.id.apellidocliente_txt);
        telefonocliente_txt = findViewById(R.id.telefonocliente_txt);

    }


    public void volver(View v){
        Intent intento = new Intent(this,Inicio.class);
        startActivity(intento);
    }

    public boolean isEmailValid(String email) {
        return email.contains("@");
    }

    public boolean isPasswordValid(String password) {
        return password.length() > 4;
    }

    public boolean isPhoneValid(String phone) {
        String regex = "3\\d{9}";
        return phone.matches(regex);
    }

    public void register_client(View v){

        String correo = String.valueOf(correocliente_txt.getText());
        String contraseña = String.valueOf(contraseñacliente_txt.getText());
        String nombre = String.valueOf(nombrecliente_txt.getText());
        String apellido = String.valueOf(apellidocliente_txt.getText());
        String telefono = String.valueOf(telefonocliente_txt.getText());


        if(correo.isEmpty() || contraseña.isEmpty() || nombre.isEmpty() || apellido.isEmpty() || telefono.isEmpty()){
            Toast.makeText(this, "Por favor, llene todos los campos", Toast.LENGTH_SHORT).show();
            return;
    }else if(!isEmailValid(correo)){
            Toast.makeText(this, "El correo no es válido", Toast.LENGTH_SHORT).show();
            return;}
        else if(!isPasswordValid(contraseña)){
            Toast.makeText(this, "La contraseña debe tener al menos 5 caracteres", Toast.LENGTH_SHORT).show();
            return;}
        else if(!isPhoneValid(telefono)){
            Toast.makeText(this, "El número de teléfono no es válido", Toast.LENGTH_SHORT).show();
            return;
        }else {
            mAuth.createUserWithEmailAndPassword(correo, contraseña)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // El usuario se ha creado exitosamente, ahora guardamos los datos adicionales en Realtime Database
                            FirebaseUser firebaseUser = mAuth.getCurrentUser();
                            if (firebaseUser != null) {
                                String userId = firebaseUser.getUid();

                                // Guardar los datos adicionales en Realtime Database
                                DatabaseReference usuarioRef = FirebaseDatabase.getInstance().getReference().child("usuarios").child("clientes").child(userId);

                                Map<String, Object> datosUsuario = new HashMap<>();
                                datosUsuario.put("nombre", nombre);
                                datosUsuario.put("apellido", apellido);
                                datosUsuario.put("telefono", telefono);

                                usuarioRef.setValue(datosUsuario).addOnSuccessListener(aVoid -> {
                                            Toast.makeText(this, "Usuario creado exitosamente", Toast.LENGTH_SHORT).show();

                                            // Puedes iniciar la siguiente actividad después de guardar los datos
                                            Intent intento = new Intent(this, Inicio.class);
                                            startActivity(intento);
                                        })
                                        .addOnFailureListener(e -> {
                                            Toast.makeText(this, "Error al guardar datos adicionales", Toast.LENGTH_SHORT).show();
                                        });
                            }
                        } else {
                            Toast.makeText(this, "Error al crear el usuario", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

}
}