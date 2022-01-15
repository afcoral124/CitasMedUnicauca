package com.unicauca.citasmed;

import static android.content.ContentValues.TAG;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
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
import com.unicauca.citasmed.db.DbUsuarios;
import com.unicauca.citasmed.modelo.Cita;
import com.unicauca.citasmed.modelo.Paciente;
import com.unicauca.citasmed.modelo.Profesional;

import java.util.ArrayList;
import java.util.List;

import citasmed.R;


public class MiAgendaActivity extends AppCompatActivity {
    private RecyclerView recyclerCitas;
    private DatabaseReference myRef;
    private int id_paciente;
    private DbUsuarios dbUsuarios;
    private Paciente paciente;
    private TextView tvNombrePaciente;
    private TextView tvIdentificacion;

    public static final String EXTRA_MESSAGE = "com.unicauca.citasmed.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        dbUsuarios = new DbUsuarios(MiAgendaActivity.this);
        paciente = dbUsuarios.LeerUsuarios();


        //Consultar en SQLite cual es el id del paciente
        //si el paciente está logueado:
        if(paciente != null){
            setContentView(R.layout.activity_mi_agenda);
            tvNombrePaciente = findViewById(R.id.tvNombrePaciente);
            tvIdentificacion = findViewById(R.id.tvIdentificacionPaciente);
            tvNombrePaciente.setText(paciente.getNombre());
            tvIdentificacion.setText("Identificación: "+paciente.getId_paciente());
            id_paciente=paciente.getId_paciente(); //por ahora
            recyclerCitas = findViewById(R.id.RecyclerCitas);
            recyclerCitas.setLayoutManager(new LinearLayoutManager(this));
            consultarCitas();
        }else{//si no esta logueado
            setContentView(R.layout.layout_no_logueado);
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
        //Llamar a la actividad de iniciar sesión, con un intent que nos guarde
        //la información del medico seleccionado
        Intent intent = new Intent(MiAgendaActivity.this, IniciarSesionActivity.class);
        String message = "H";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }
}
