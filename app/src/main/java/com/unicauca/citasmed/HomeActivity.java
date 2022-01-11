package com.unicauca.citasmed;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import citasmed.R;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Menu hamburguesa
        Toolbar toolbar = findViewById(R.id.toolbar);
        drawer = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navView);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.musicatxt, R.string.musicatxt);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        //Escritura en la DB
        FirebaseDatabase database = FirebaseDatabase.getInstance("https://citasmeddb-default-rtdb.firebaseio.com/");
        DatabaseReference myRef = database.getReference("message");
        myRef.setValue("Hello, World!");

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                String value = dataSnapshot.getValue(String.class);
                Log.d(TAG, "Value is: " + value);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    // Read from the database


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
            super.onBackPressed();
        }
    }
}
