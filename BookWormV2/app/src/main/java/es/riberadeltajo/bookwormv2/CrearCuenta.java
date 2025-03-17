package es.riberadeltajo.bookwormv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Source;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

public class CrearCuenta extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        HashMap usuario = new HashMap();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crearcuenta);
        Button biniciosesion = findViewById(R.id.isBtn);
        Button crearCuenta = findViewById(R.id.registrarseBtn);
        EditText nom = findViewById(R.id.nombreUs);
        EditText ape = findViewById(R.id.apellidosUs);
        EditText nomus = findViewById(R.id.usernameUs);
        EditText email = findViewById(R.id.emailUs);
        EditText pass = findViewById(R.id.passwordUs);
        ArrayList ped = new ArrayList();
        ArrayList seg = new ArrayList();
        ArrayList sig = new ArrayList();

        crearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario.put("nombre", nom.getText().toString());
                usuario.put("apellidos", ape.getText().toString());
                usuario.put("email", email.getText().toString());
                usuario.put("contraseña", pass.getText().toString());
                usuario.put("username", nomus.getText().toString());
                usuario.put("pedidos", ped);
                usuario.put("seguidores", seg);
                usuario.put("siguiendo", sig);

                db.collection("Usuarios").document(email.getText().toString()).get(Source.CACHE)
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        Toast.makeText(CrearCuenta.this, "Este email ya ha sido registrado", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        db.collection("Usuarios").document(email.getText().toString()).set(usuario)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(CrearCuenta.this, "Cuenta creada con exito", Toast.LENGTH_SHORT).show();
                                        Intent i = new Intent(getApplicationContext(), InicioSesion.class);
                                        startActivity(i);


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(CrearCuenta.this, "Los datos introducidos son incorrectos", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });









            }
        });

        biniciosesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), InicioSesion.class);
                startActivity(i);

            }
        });

    }
}