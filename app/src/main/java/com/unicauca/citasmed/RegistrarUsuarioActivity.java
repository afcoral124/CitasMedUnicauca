package com.unicauca.citasmed;

import static android.content.ContentValues.TAG;

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

public class RegistrarUsuarioActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    private DatabaseReference myRef;
    private EditText etNombre;
    private EditText etCorreo;
    private EditText etUsername;
    private EditText etPassword;
    private EditText etConfirmarPassword;

    private String nombrePaciente;
    private String correoPaciente;
    private String usernamePaciente;
    private String passwordPaciente;
    private String confirmarPasswordPaciente;
    private int id_paciente;

    private DbUsuarios dbUsuarios;
    private Paciente pacienteNuevo;

    private String message;
    public static final String EXTRA_MESSAGE = "com.unicauca.citasmed.MESSAGE";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
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

        etNombre = findViewById(R.id.etNombre);
        etCorreo = findViewById(R.id.etCorreo);
        etUsername = findViewById(R.id.etCorreo);
        etPassword = findViewById(R.id.etPassword);
        etConfirmarPassword = findViewById(R.id.etConfirmarPassword);
    }


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

    public void registrarPaciente(View view){
        nombrePaciente = String.valueOf(etNombre.getText());
        correoPaciente = String.valueOf(etCorreo.getText());
        usernamePaciente = String.valueOf(etUsername.getText());
        passwordPaciente = String.valueOf(etPassword.getText());
        confirmarPasswordPaciente = String.valueOf(etConfirmarPassword.getText());

        Query q = myRef.child("Pacientes");
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    id_paciente = (int) snapshot.getChildrenCount();
                    System.out.printf(" el numero de nodos es "+id_paciente);
                    crearPaciente();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.getMessage()); //Don't ignore errors!
            }
        });


    }

    public void crearPaciente(){


        System.out.println("--------------------------------------------------------------------");

        if(passwordPaciente.equals(confirmarPasswordPaciente)){
            System.out.println("Se va a registrar un paciente con los datos: ");
            System.out.println("id_paciente: "+id_paciente);
            System.out.println("nombre: "+nombrePaciente);
            System.out.println("correo: "+correoPaciente);
            System.out.println("username: "+usernamePaciente);
            System.out.println("password: "+passwordPaciente);
            pacienteNuevo = new Paciente(correoPaciente, id_paciente, nombrePaciente, passwordPaciente, usernamePaciente);

            //Escritura en la DB
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("Pacientes").child(String.valueOf(id_paciente)); //clave
            myRef.setValue(pacienteNuevo); //valor
            finalizarInicioSesion();

        }else{
            Toast.makeText(RegistrarUsuarioActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
        }





    }

    public void finalizarInicioSesion(){
        //iniciar sesión en SQLite
        //Guardar paciente en la DB local
        DbUsuarios dbUsuarios = new DbUsuarios(RegistrarUsuarioActivity.this);
        long id = dbUsuarios.InsertarUsuario(pacienteNuevo.getId_paciente(), pacienteNuevo.getNombre(),
                pacienteNuevo.getUsuario(), pacienteNuevo.getCorreo(), pacienteNuevo.getPassword());
        if (id > 0){
            Toast.makeText(RegistrarUsuarioActivity.this, "Sesión iniciada ", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(RegistrarUsuarioActivity.this, "Error al iniciar sesión ", Toast.LENGTH_SHORT).show();
        }

        //Redirigir al home o a Agendar Citas
        if(message.equals("H")){
            System.out.println("Regresando a Home");
            Intent intent = new Intent(RegistrarUsuarioActivity.this, HomeActivity.class);
            startActivity(intent);
        }
        else {
            System.out.println("Regresando a Agendar Cita");
            Intent intent = new Intent(RegistrarUsuarioActivity.this, AgendarCitaActivity.class);
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);
        }
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
