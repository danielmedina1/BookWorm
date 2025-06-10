package es.riberadeltajo.bookwormv2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import es.riberadeltajo.bookwormv2.recyclerviews.carrito.ListaCarrito;

public class InicioSesion extends AppCompatActivity {
    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    public static String nombreusuario = new String();
    public static String nombreempresa = new String();
    public static String emailusuario = new String();
    public static String emailempresa = new String();
    public static String codusuario = new String();
    public static String codempresa = new String();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.iniciarsesion);
        mAuth = FirebaseAuth.getInstance();
        Button bcrearcuenta = findViewById(R.id.crearcuentaBtn);
        Button biniciarsesion = findViewById(R.id.iniciarsesBtn);

        EditText email = findViewById(R.id.emailIs);
        EditText pass = findViewById(R.id.passwordIs);

        String pass2;

        biniciarsesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!email.getText().toString().equals("") && !pass.getText().toString().equals("")) {
                    db.collection("Usuarios").whereEqualTo("email", email.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                            for (QueryDocumentSnapshot d: queryDocumentSnapshots) {
                                if (pass.getText().toString().equals(d.getString("contraseña"))) {
                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                    nombreusuario = d.getString("username");
                                    emailusuario = d.getString("email");
                                    codusuario = d.getId();
                                    startActivity(i);
                                } else {
                                    Toast.makeText(InicioSesion.this, "Contraseña Usuario Incorrecta", Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {


                        }
                    });
                    db.collection("Empresas").whereEqualTo("email", email.getText().toString()).get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    for (QueryDocumentSnapshot d: queryDocumentSnapshots) {
                                        if (pass.getText().toString().equals(d.getString("password"))) {
                                            Intent i = new Intent(getApplicationContext(), MainActivity2.class);
                                            emailempresa = d.getString("email");
                                            nombreempresa = d.getString("nombre");
                                            codempresa = d.getId();
                                            startActivity(i);
                                        } else {
                                            Toast.makeText(InicioSesion.this, "Contraseña Empresa Incorrecta", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {

                                }
                            });
                    db.collection("Administradores").whereEqualTo("email", email.getText().toString()).get()
                            .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                                @Override
                                public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                                    for (QueryDocumentSnapshot d: queryDocumentSnapshots) {
                                        if (pass.getText().toString().equals(d.getString("password"))) {
                                            Intent i = new Intent(getApplicationContext(), MainActivity3.class);
                                            startActivity(i);
                                        } else {
                                            Toast.makeText(InicioSesion.this, "Contraseña Administrador Incorrecta", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(InicioSesion.this, "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(InicioSesion.this, "Introduce los datos en sus respectivos campos", Toast.LENGTH_SHORT).show();
                }
            }
        });


        bcrearcuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), CrearCuenta.class);
                startActivity(i);

            }
        });

    }
}