package com.unicauca.citasmed;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import android.os.Bundle;
import android.view.View;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.unicauca.citasmed.adapter.AdaptadorProfesionales;
import com.unicauca.citasmed.modelo.Profesional;

import java.util.ArrayList;
import java.util.List;

import citasmed.R;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    FragmentCategorias fragCategorias;
    FragmentProfesionales fragProfesionales;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragCategorias = new FragmentCategorias();

        getSupportFragmentManager().beginTransaction().add(R.id.contenedorFragmentos, fragCategorias).commit();

        llenarSpinner();
    }

    public void llenarSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.ciudades, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    //Cuando se presiona una de las categorías, o el botón Atrás
    public void onClick(View view) {
        fragProfesionales = new FragmentProfesionales();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (view.getId()){
            case R.id.MedicoGeneral:
                System.out.println("Hasta aquí va bien");
                transaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragmentos, fragProfesionales).addToBackStack(null);
                break;
            case R.id.cardAtras:
                onBackPressed();
                break;
        }transaction.commit();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        //System.out.println(parent.getItemAtPosition(pos));
        Log.d("Total", String.valueOf(parent.getItemAtPosition(pos)));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }




}

