package com.unicauca.aplimovil;

import android.app.Activity;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import com.unicauca.aplimovil.adapter.AdaptadorProfesionales;
import com.unicauca.aplimovil.modelo.Profesional;


public class NuestrosProfesionalesActivity extends AppCompatActivity {
    private List<Profesional> listaProfesionales;
    private RecyclerView recyclerProfesionales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listaProfesionales = new ArrayList<>();
        recyclerProfesionales = findViewById(R.id.recyclerMedGeneral);
        recyclerProfesionales.setLayoutManager(new LinearLayoutManager(this));

        generarProfesionales();

        AdaptadorProfesionales adaptador = new AdaptadorProfesionales(listaProfesionales);
        recyclerProfesionales.setAdapter(adaptador);
    }

    private void generarProfesionales(){
        Profesional p = null;
        p = new Profesional("Maria");
        listaProfesionales.add(p);
        p = new Profesional("Camila");
        listaProfesionales.add(p);
        p = new Profesional("Cristina");
        listaProfesionales.add(p);

    }
}