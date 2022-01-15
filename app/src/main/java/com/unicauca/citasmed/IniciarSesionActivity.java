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
import com.unicauca.citasmed.db.DbUsuarios;
import com.unicauca.citasmed.modelo.Paciente;

import citasmed.R;

public class IniciarSesionActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    private DatabaseReference myRef;
    private EditText etCorreo;
    private EditText etPassword;
    private String message;

    public static final String EXTRA_MESSAGE = "com.unicauca.citasmed.MESSAGE";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_iniciar_sesion);

        Intent intent = getIntent();
        message = intent.getStringExtra(FragmentMedGeneral.EXTRA_MESSAGE);
        System.out.println("message: "+message);

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
                abrirActividad(MiAgendaActivity.class);
                break;
            case R.id.agendarCita:
                abrirActividad(MainActivity.class);
                break;
            case R.id.categoriaServicios:
                abrirActividad(NuestrosProfesionalesActivity.class);
                break;
            case R.id.acercaDe:
                abrirActividad(MasInformacionActivity.class);
                break;
            case R.id.sobreNosotros:
                abrirActividad(SobreNosotrosActivity.class);
                break;
            case R.id.appMovil:
                abrirActividad(SobreNosotrosActivity.class);
                break;
            case R.id.cerrarSesion:

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
                            //Guardar paciente en la DB local
                            DbUsuarios dbUsuarios = new DbUsuarios(IniciarSesionActivity.this);
                            long id = dbUsuarios.InsertarUsuario(paciente.getId_paciente(), paciente.getNombre(),
                                    paciente.getUsuario(), paciente.getCorreo(), paciente.getPassword());
                            if (id > 0){
                                Toast.makeText(IniciarSesionActivity.this, "Sesión iniciada ", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(IniciarSesionActivity.this, "Error al iniciar sesión ", Toast.LENGTH_SHORT).show();
                            }

                            if(message.equals("H")){
                                System.out.println("Regresando a Home");
                                Intent intent = new Intent(IniciarSesionActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                            else {
                                System.out.println("Regresando a Agendar Cita");
                                Intent intent = new Intent(IniciarSesionActivity.this, AgendarCitaActivity.class);
                                intent.putExtra(EXTRA_MESSAGE, message);
                                startActivity(intent);
                            }


                        } else {
                            Toast.makeText(IniciarSesionActivity.this, "Contraseña incorrecta", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(IniciarSesionActivity.this, "Correo electrónico incorrecto", Toast.LENGTH_SHORT).show();
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
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
