package com.unicauca.citasmed;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.unicauca.citasmed.modelo.Paciente;

import citasmed.R;

public class IniciarSesionActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    private DatabaseReference myRef;
    private EditText etCorreo;
    private EditText etPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        //Menu hamburguesa
        Toolbar toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.musicatxt, R.string.musicatxt);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Conexión a la base de datos
        myRef = FirebaseDatabase.getInstance().getReference(); //referencia al nodo principal

        etCorreo=findViewById(R.id.etCorreo);
        etPassword=findViewById(R.id.etPassword);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.misCitas:
                Log.d("opcion 1", "seleccionada opcion 1");
                break;
            case R.id.agendarCita:
                Log.d("opcion 2", "seleccionada opcion 2");
                abrirActividad(MainActivity.class);
                break;
            case R.id.categoriaServicios:
                Log.d("opcion 3", "seleccionada opcion 3");
                abrirActividad(NuestrosProfesionalesActivity.class);
                break;
            case R.id.acercaDe:
                Log.d("opcion 3", "seleccionada opcion 3");
                break;
            case R.id.sobreNosotros:
                Log.d("opcion 3", "seleccionada opcion 3");
                break;
            case R.id.appMovil:
                Log.d("opcion 3", "seleccionada opcion 3");
                break;
            case R.id.cerrarSesion:
                Log.d("opcion 3", "seleccionada opcion 3");
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void abrirActividad(Class clase){

        Intent newAct = new Intent(getApplicationContext(), clase);
        startActivity(newAct);
    }


    public void iniciarSesion(View view){
        validarCredenciales();
    }

    public void validarCredenciales(){
        Query q = myRef.child("Pacientes").orderByChild("correo").equalTo(etCorreo.getText().toString());
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // dataSnapshot is the "issue" node with all children with id 0

                    for (DataSnapshot ds : dataSnapshot.getChildren()) {
                        // do something with the individual "issues"
                        Paciente paciente = ds.getValue(Paciente.class);

                        if (paciente.getPassword().equals(etPassword.getText().toString())) {
                            Toast.makeText(IniciarSesionActivity.this, "Nombre: "+paciente.getNombre(), Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(IniciarSesionActivity.this, "Password is wrong", Toast.LENGTH_LONG).show();
                        }
                    }
                } else {
                    Toast.makeText(IniciarSesionActivity.this, "User not found", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void regresar(View view){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
