package com.unicauca.citasmed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import android.os.Bundle;
import android.view.View;

import citasmed.R;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    FragmentCategorias fragCategorias;





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

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (view.getId()){
            case R.id.MedicoGeneral:
                FragmentMedGeneral fragMed = new FragmentMedGeneral();
                transaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragmentos, fragMed).addToBackStack(null);
                break;
            case R.id.btnCardiologia:
                FragmentCardiologia fragCard = new FragmentCardiologia();
                transaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragmentos, fragCard).addToBackStack(null);
                break;
            case R.id.btnFisioterapia:
                FragmentFisioterapia fragFis = new FragmentFisioterapia();
                transaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragmentos, fragFis).addToBackStack(null);
                break;
            case R.id.btnFonoaudiologia:
                FragmentFonoaudiologia fragFon = new FragmentFonoaudiologia();
                transaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragmentos, fragFon).addToBackStack(null);
                break;
            case R.id.btnGinecologia:
                FragmentGinecologia fragGin = new FragmentGinecologia();
                transaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragmentos, fragGin).addToBackStack(null);
                break;
            case R.id.btnOdontologia:
                FragmentOdontologia fragOdo = new FragmentOdontologia();
                transaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragmentos, fragOdo).addToBackStack(null);
                break;
            case R.id.btnOftalmologia:
                FragmentOftalmologia fragOft = new FragmentOftalmologia();
                transaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragmentos, fragOft).addToBackStack(null);
                break;
            case R.id.btnOncologia:
                FragmentOncologia fragOnc = new FragmentOncologia();
                transaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragmentos, fragOnc).addToBackStack(null);
                break;
            case R.id.btnTraumatologia:
                FragmentTraumatologia fragTra = new FragmentTraumatologia();
                transaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragmentos, fragTra).addToBackStack(null);
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

