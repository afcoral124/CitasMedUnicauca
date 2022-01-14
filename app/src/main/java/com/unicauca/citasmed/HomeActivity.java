package com.unicauca.citasmed;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;
import com.unicauca.citasmed.db.DbHelper;
import com.unicauca.citasmed.db.DbUsuarios;
import com.unicauca.citasmed.modelo.Paciente;
import com.unicauca.citasmed.modelo.Profesional;

import java.util.ArrayList;

import citasmed.R;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private DbUsuarios dbUsuarios;
    private Paciente paciente;
    private TextView tvNombreUsuarioNav;
    private TextView tvUsernameUsuarioNav;
    private CardView cvBotonIniciarSesion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Menu hamburguesa
        Toolbar toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);
        View navHeader = navigationView.getHeaderView(0);
        tvNombreUsuarioNav = navHeader.findViewById(R.id.tvNombreUsuarioNav);
        tvUsernameUsuarioNav = navHeader.findViewById(R.id.tvUsernameUsuarioNav);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.musicatxt, R.string.musicatxt);
        drawer.addDrawerListener(toggle);

        cvBotonIniciarSesion = findViewById(R.id.cvBotonIniciarSesion);
        //Creando la base de datos local
        DbHelper dbHelper = new DbHelper(HomeActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbUsuarios = new DbUsuarios(HomeActivity.this);
        if (db != null){
            Toast.makeText(HomeActivity.this, "Leyendo Base de datos  ", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(HomeActivity.this, "Error al crear la base de datos ", Toast.LENGTH_SHORT).show();
        }
        paciente = dbUsuarios.LeerUsuarios();
        if (paciente != null){

            tvNombreUsuarioNav.setText(paciente.getNombre());
            tvUsernameUsuarioNav.setText(paciente.getUsuario());
            cvBotonIniciarSesion.setVisibility(View.GONE);

        }else {
            Toast.makeText(HomeActivity.this, "No hay paciente logueado ", Toast.LENGTH_SHORT).show();
        }
        toggle.syncState();
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.misCitas:
                Log.d("opcion 1", "seleccionada opcion 1");
                abrirActividad(MiAgendaActivity.class);
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
                abrirActividad(SobreNosotrosActivity.class);
                break;
            case R.id.appMovil:
                Log.d("opcion 3", "seleccionada opcion 3");
                break;
            case R.id.cerrarSesion:
                Log.d("opcion 3", "Borrados los datos de la tabla");
                dbUsuarios.borrarDatos();
                tvNombreUsuarioNav.setText("Nombre");
                tvUsernameUsuarioNav.setText("Username");
                cvBotonIniciarSesion.setVisibility(View.VISIBLE);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
    public void abrirMiAgenda(View view){
        Intent intent = new Intent(this, MiAgendaActivity.class);
        startActivity(intent);
    }

    public void abrirAgendarCitas(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    public void abrirNuestrosProfesionales(View view){
        Intent intent = new Intent(this, NuestrosProfesionalesActivity.class);
        startActivity(intent);
    }

    public void abrirActividad(Class clase){
        Intent newAct = new Intent(getApplicationContext(), clase);
        startActivity(newAct);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            finishAffinity();
            //super.onBackPressed();
        }
    }

    public void abrirIniciarSesionActivity(View view){
        Intent intent = new Intent(this, IniciarSesionActivity.class);
        startActivity(intent);
    }

}
