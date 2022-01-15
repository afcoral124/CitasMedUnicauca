package com.unicauca.citasmed;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.unicauca.citasmed.db.DbHelper;
import com.unicauca.citasmed.db.DbUsuarios;
import com.unicauca.citasmed.modelo.Paciente;

import citasmed.R;

public class SobreNosotrosActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;
    private TextView tvNombreUsuarioNav;
    private Paciente paciente;
    private DbUsuarios dbUsuarios;
    private TextView tvUsernameUsuarioNav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sobre_nosotros);

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
        //Creando la base de datos local
        DbHelper dbHelper = new DbHelper(SobreNosotrosActivity.this);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        dbUsuarios = new DbUsuarios(SobreNosotrosActivity.this);
        if (db != null){
            System.out.println("Leyendo Base de datos");
        }else {
            System.out.println("Error al crear la base de datos");
        }
        paciente = dbUsuarios.LeerUsuarios();
        if (paciente != null){

            tvNombreUsuarioNav.setText(paciente.getNombre());
            tvUsernameUsuarioNav.setText(paciente.getUsuario());

        }
        toggle.syncState();

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
                if(paciente!=null){
                    dbUsuarios.borrarDatos();
                    tvNombreUsuarioNav.setText("Nombre");
                    tvUsernameUsuarioNav.setText("Username");
                    paciente=null;
                }
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
            if (paciente!=null){
                super.onBackPressed();
            }
            else {
                Intent intent = new Intent(SobreNosotrosActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        }
    }


    public void regresar(View view){
        onBackPressed();
    }

}
