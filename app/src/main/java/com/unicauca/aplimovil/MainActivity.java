package com.unicauca.aplimovil;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    FragmentCategorias fragCategorias;
    FragmentProfesionales fragProfesionales;

    int opcion=0;

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

    public void onClick(View view) {
        fragProfesionales = new FragmentProfesionales();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (view.getId()){
            case R.id.MedicoGeneral:
                opcion=1;
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

