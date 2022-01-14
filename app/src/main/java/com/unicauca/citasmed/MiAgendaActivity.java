package com.unicauca.citasmed;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.unicauca.citasmed.adapter.AdaptadorCitas;
import com.unicauca.citasmed.adapter.AdaptadorProfesionales;
import com.unicauca.citasmed.modelo.Cita;
import com.unicauca.citasmed.modelo.Profesional;

import java.util.ArrayList;
import java.util.List;

import citasmed.R;


public class MiAgendaActivity extends Activity {
    private RecyclerView recyclerCitas;
    private DatabaseReference myRef;
    private int id_paciente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mi_agenda);

        //Consultar en SQLite cual es el id del paciente
        //si el paciente está logueado:
        if(true){
            id_paciente=1;
            recyclerCitas = findViewById(R.id.RecyclerCitas);
            recyclerCitas.setLayoutManager(new LinearLayoutManager(this));
            consultarCitas();
        }else{//si no esta logueado
            setContentView(R.layout.activity_mi_agenda);
        }



    }

    private void consultarCitas() {
        myRef = FirebaseDatabase.getInstance().getReference(); //referencia al nodo principal
        Query q = myRef.child("Citas").orderByChild("id_paciente").equalTo(id_paciente);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    List<Cita> value = new ArrayList<>();
                    for(DataSnapshot ds : snapshot.getChildren()) {
                        Cita cita = ds.getValue(Cita.class);
                        value.add(cita);
                    }
                    AdaptadorCitas adaptador = new AdaptadorCitas(value);
                    recyclerCitas.setAdapter(adaptador);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.getMessage()); //Don't ignore errors!
            }
        });
    }


    public void irAtras(View view){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    public void abrirIniciarSesionActivity(View view){
        Intent intent = new Intent(this, IniciarSesionActivity.class);
        startActivity(intent);
    }
}
