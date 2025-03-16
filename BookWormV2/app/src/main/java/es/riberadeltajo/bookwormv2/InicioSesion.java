package es.riberadeltajo.bookwormv2;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
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
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class InicioSesion extends AppCompatActivity {

    private FirebaseAuth mAuth;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

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
                db.collection("Usuarios").document(email.getText().toString()).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        db.collection("Usuarios").document(email.getText().toString()).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                DocumentSnapshot d = task.getResult();
                                //Log.d("Prueba", "datos: "+d.getString("contraseña"));
                                //Usuario: prueba1, Contraseña: 1234
                                if (pass.getText().toString().equals(d.getString("contraseña"))) {
                                    Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(InicioSesion.this, "Contraseña Incorrecta", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(InicioSesion.this, "La cuenta con el correo electronico introducido no existe", Toast.LENGTH_SHORT).show();
                    }
                });
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

//    @Override
//    public void onStart() {
//        super.onStart();
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null) {
//            Toast.makeText(this, "Buenas Tardes", Toast.LENGTH_SHORT).show();
//        }
//    }
}