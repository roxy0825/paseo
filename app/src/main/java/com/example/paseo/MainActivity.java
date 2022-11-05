package com.example.paseo;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    EditText etCodigo, etNombre, etCiudad, etCantidad;
    CheckBox cbActivo;
    String codigo, nombre, ciudad, cantidad, codigoId;
    byte sw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etCodigo = findViewById(R.id.etcodigo);
        etNombre = findViewById(R.id.etnombre);
        etCiudad = findViewById(R.id.etciudad);
        etCantidad = findViewById(R.id.etcantidad);
        cbActivo = findViewById(R.id.cbactivo);
        sw = 0;
    }

    public void adicionar(View view) {
        codigo = etCodigo.getText().toString();
        nombre = etNombre.getText().toString();
        ciudad = etCiudad.getText().toString();
        cantidad = etCantidad.getText().toString();
        if (codigo.isEmpty() || nombre.isEmpty() || ciudad.isEmpty()
                || cantidad.isEmpty()) {
            Toast.makeText(this, "Todos los datos requeridos", Toast.LENGTH_SHORT).show();
            etCodigo.requestFocus();
        } else {
            Map<String, Object> factura = new HashMap<>();
            factura.put("Codigo", codigo);
            factura.put("Nombre", nombre);
            factura.put("Ciudad", ciudad);
            factura.put("Cantidad", cantidad);
            factura.put("Activo", "si");

            // Add a new document with a generated ID
            db.collection("facturas")
                    .add(factura)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            Toast.makeText(MainActivity.this, "Datos guardados!", Toast.LENGTH_SHORT).show();
                            limpiarCampos();
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MainActivity.this, "Error, guardando campos!", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public void consultar(View view) {
        codigo = etCodigo.getText().toString();
        if (codigo.isEmpty()) {
            Toast.makeText(this, "El codigo es requerido", Toast.LENGTH_SHORT).show();
            etCodigo.requestFocus();
        } else {
            db.collection("facturas")
                    .whereEqualTo("Codigo", codigo)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                sw = 1;
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    codigoId = document.getId();
                                    etNombre.setText(document.getString("Nombre"));
                                    etCiudad.setText(document.getString("Ciudad"));
                                    etCantidad.setText(document.getString("Cantidad"));
                                    if (document.getString("Activo").equals("si")) {
                                        cbActivo.setChecked(Boolean.TRUE);
                                    } else {
                                        cbActivo.setChecked(Boolean.FALSE);
                                    }
                                }
                            } else {
                                Toast.makeText(MainActivity.this, "Error consultando datos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }

    }

    public void Anular (View view) {
        if (sw == 0) {
            Toast.makeText(this, "Para anular debe primero consultar", Toast.LENGTH_SHORT).show();
            etCodigo.requestFocus();
        } else {
            codigo = etCodigo.getText().toString();
            nombre = etNombre.getText().toString();
            ciudad = etCiudad.getText().toString();
            cantidad = etCantidad.getText().toString();
            if (codigo.isEmpty() || nombre.isEmpty() || ciudad.isEmpty() || cantidad.isEmpty()) {
                Toast.makeText(this, "Todos los datos requeridos", Toast.LENGTH_SHORT).show();
                etCodigo.requestFocus();
            } else {
                // Create a new user with a first and last name
                Map<String, Object> factura = new HashMap<>();
                factura.put("Codigo", codigo);
                factura.put("Nombre", nombre);
                factura.put("Ciudad", ciudad);
                factura.put("Cantidad", cantidad);
                factura.put("Activo", "No");
                db.collection("facturas").document(codigoId)
                        .set(factura)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "documento anulado...", Toast.LENGTH_SHORT).show();
                                limpiarCampos();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Error anulado estudiante...", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
    }
    public void cancelar(View view) {
        limpiarCampos();
    }

    public void Modificar(View view) {
        if (sw == 0) {
            Toast.makeText(this, "Para modificar debe primero consultar", Toast.LENGTH_SHORT).show();
            etCodigo.requestFocus();
        } else {
            codigo = etCodigo.getText().toString();
            nombre = etNombre.getText().toString();
            ciudad = etCiudad.getText().toString();
            cantidad = etCantidad.getText().toString();
            if (codigo.isEmpty() || nombre.isEmpty() || ciudad.isEmpty() || cantidad.isEmpty()) {
                Toast.makeText(this, "Todos los datos requeridos", Toast.LENGTH_SHORT).show();
                etCodigo.requestFocus();
            } else {
                // Create a new user with a first and last name
                Map<String, Object> factura = new HashMap<>();
                factura.put("Codigo", codigo);
                factura.put("Nombre", nombre);
                factura.put("Ciudad", ciudad);
                factura.put("Cantidad", cantidad);
                factura.put("Activo", "Si");
                db.collection("facturas").document(codigoId)
                        .set(factura)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "Estudiante actualizado correctmente...", Toast.LENGTH_SHORT).show();
                                limpiarCampos();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Error actualizando estudiante...", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }
    }
        public void Eliminar (View view){
            if (sw == 0) {
                Toast.makeText(this, "Para modificar debe primero consultar", Toast.LENGTH_SHORT).show();
                etCodigo.requestFocus();
            } else {
                db.collection("facturas").document(codigoId)
                        .delete()
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "Documento eliminado...", Toast.LENGTH_SHORT).show();
                                limpiarCampos();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Error eliminando documento...", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }



        private void limpiarCampos () {
            etCodigo.setText("");
            etNombre.setText("");
            etCiudad.setText("");
            etCantidad.setText("");
            cbActivo.setChecked(false);
            etCodigo.requestFocus();
            sw = 0;

    }
}


