package com.example.manoamiga;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;

public class Registro_Proveedor extends AppCompatActivity {
    FirebaseAuth mAuth;

    EditText NombreProovedor,CorreoProveedor,ContraseñaProveedor,TelefonoProveedor,DireccionProveedor,NmServicio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_proveedor);

        mAuth = FirebaseAuth.getInstance();
        NombreProovedor = findViewById(R.id.nombreproveedor_txt);
        CorreoProveedor = findViewById(R.id.correoproveedor_txt);
        ContraseñaProveedor = findViewById(R.id.contraseñaproveedor_txt);
        TelefonoProveedor = findViewById(R.id.telefonoproveedor_txt);
        DireccionProveedor = findViewById(R.id.direccionproveedor_txt);


    }

    public void volver_(View v){
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

    public void pv_registrar(View v){

        String nombre = String.valueOf(NombreProovedor.getText());
        String correo = String.valueOf(CorreoProveedor.getText());
        String contraseña = String.valueOf(ContraseñaProveedor.getText());
        String telefono = String.valueOf(TelefonoProveedor.getText());
        String direccion = String.valueOf(DireccionProveedor.getText());


        if (nombre.isEmpty() || correo.isEmpty() || contraseña.isEmpty() || telefono.isEmpty() || direccion.isEmpty()) {
            Toast.makeText(this, "Debe llenar todos los campos", Toast.LENGTH_SHORT).show();
        } else if (!isEmailValid(correo)) {
            Toast.makeText(this, "El correo no es válido", Toast.LENGTH_SHORT).show();
        } else if (!isPasswordValid(contraseña)) {
            Toast.makeText(this, "La contraseña debe tener al menos 5 caracteres", Toast.LENGTH_SHORT).show();
        } else if (!isPhoneValid(telefono)) {
            Toast.makeText(this, "El teléfono no es válido", Toast.LENGTH_SHORT).show();
        }
        else{
            mAuth.createUserWithEmailAndPassword(correo, contraseña).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    FirebaseUser user = mAuth.getCurrentUser();
                    String userId = user.getUid();
                    DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("usuarios").child("proveedores");
                    Map<String, Object> map = new HashMap<>();
                    map.put("id", userId);
                    map.put("nombre", nombre);
                    map.put("telefono", telefono);
                    map.put("direccion", direccion);
                    mDatabase.child(userId).setValue(map).addOnCompleteListener(task1 -> {
                        if (task1.isSuccessful()) {
                            Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                            Intent intento = new Intent(this,Registro_Detalles.class);
                            intento.putExtra("id", mAuth.getCurrentUser().getUid());
                            startActivity(intento);
                            // You can add any additional logic or navigation here
                        } else {
                            Toast.makeText(this, "No se pudo registrar los datos correctamente", Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    Toast.makeText(this, "No se pudo registrar este usuario", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }
}