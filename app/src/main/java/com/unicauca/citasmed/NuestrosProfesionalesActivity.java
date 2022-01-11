package com.unicauca.citasmed;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import com.unicauca.citasmed.adapter.AdaptadorProfesionales;
import com.unicauca.citasmed.modelo.Profesional;
import citasmed.R;


public class NuestrosProfesionalesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private List<Profesional> listaProfesionales;
    private RecyclerView recyclerProfesionales;
    public LinearLayout opcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuestros_profesionales);

        llenarSpinner();
        generarProfesionalesMedicinaGeneral();
        generarProfesionalesCardiologia();
        generarProfesionalesFisioterapia();
        generarProfesionalesFonoaudiologia();
        generarProfesionalesGinecologia();
        generarProfesionalesOdontologia();
        generarProfesionalesOftalmologia();
        generarProfesionalesOncologia();
        generarProfesionalesTraumatologia();
    }

    public void onClick(View view) {
       onBackPressed();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void llenarSpinner() {
        Spinner spinner = (Spinner) findViewById(R.id.spinner2);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.profesiones, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    public void ocultarOpciones() {
        LinearLayout medgeneral = findViewById(R.id.LyMed_General);
        LinearLayout cardiologia = findViewById(R.id.LyCardiologia);
        LinearLayout fisioterapia = findViewById(R.id.LyFisioterapia);
        LinearLayout fonoaudiologia = findViewById(R.id.LyFonoaudiologia);
        LinearLayout ginecologia = findViewById(R.id.LyGinecologia);
        LinearLayout odontologia = findViewById(R.id.LyOdontologia);
        LinearLayout oftalmologia = findViewById(R.id.LyOftalmologia);
        LinearLayout oncologia = findViewById(R.id.LyOncologia);
        LinearLayout traumatologia = findViewById(R.id.LyTraumatologia);

        medgeneral.setVisibility(View.GONE);
        cardiologia.setVisibility(View.GONE);
        fisioterapia.setVisibility(View.GONE);
        fonoaudiologia.setVisibility(View.GONE);
        ginecologia.setVisibility(View.GONE);
        odontologia.setVisibility(View.GONE);
        oftalmologia.setVisibility(View.GONE);
        oncologia.setVisibility(View.GONE);
        traumatologia.setVisibility(View.GONE);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        //System.out.println(parent.getItemAtPosition(pos));
        ocultarOpciones();

        switch (String.valueOf(parent.getItemAtPosition(pos))){
            case "Med.General":
                opcion = findViewById(R.id.LyMed_General);
                opcion.setVisibility(View.VISIBLE);
                break;
            case "Cardiología":
                opcion = findViewById(R.id.LyCardiologia);
                opcion.setVisibility(View.VISIBLE);
                break;
            case "Fisioterapia":
                opcion = findViewById(R.id.LyFisioterapia);
                opcion.setVisibility(View.VISIBLE);
                break;
            case "Fonoaudiología":
                opcion = findViewById(R.id.LyFonoaudiologia);
                opcion.setVisibility(View.VISIBLE);
                break;
            case "Ginecología":
                opcion = findViewById(R.id.LyGinecologia);
                opcion.setVisibility(View.VISIBLE);
                break;
            case "Odontología":
                opcion = findViewById(R.id.LyOdontologia);
                opcion.setVisibility(View.VISIBLE);
                break;
            case "Oftalmología":
                opcion = findViewById(R.id.LyOftalmologia);
                opcion.setVisibility(View.VISIBLE);
                break;
            case "Oncología":
                opcion = findViewById(R.id.LyOncologia);
                opcion.setVisibility(View.VISIBLE);
                break;
            case "Traumatología":
                opcion = findViewById(R.id.LyTraumatologia);
                opcion.setVisibility(View.VISIBLE);
                break;

            default:
                Log.d("Total", String.valueOf(parent.getItemAtPosition(pos)));
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    private void generarProfesionalesMedicinaGeneral(){
        //Arreglo de profesionales
        listaProfesionales = new ArrayList<>();
        recyclerProfesionales = findViewById(R.id.recyclerMedGeneral);
        recyclerProfesionales.setLayoutManager(new LinearLayoutManager(this));

        //CREAMOS DE MANERA ESTÁTICA LA LISTA ---------------------------------------
        Profesional p = null;
        p = new Profesional("Maria Fernanda Manrique Sotelo");
        listaProfesionales.add(p);
        p = new Profesional("Camilo Stiven Ibarra Nuñez");
        listaProfesionales.add(p);
        p = new Profesional("Cristina Aguilera");
        listaProfesionales.add(p);
        p = new Profesional("Maria Fernanda Manrique Sotelo");
        listaProfesionales.add(p);
        p = new Profesional("Camilo Stiven Ibarra Nuñez");
        listaProfesionales.add(p);
        p = new Profesional("Cristina Aguilera");
        listaProfesionales.add(p);
        p = new Profesional("Maria Fernanda Manrique Sotelo");
        listaProfesionales.add(p);
        p = new Profesional("Camilo Stiven Ibarra Nuñez");
        listaProfesionales.add(p);
        p = new Profesional("Cristina Aguilera");
        listaProfesionales.add(p);
        p = new Profesional("Maria Fernanda Manrique Sotelo");
        listaProfesionales.add(p);
        p = new Profesional("Camilo Stiven Ibarra Nuñez");
        listaProfesionales.add(p);
        p = new Profesional("Cristina Aguilera");
        listaProfesionales.add(p);
        p = new Profesional("Maria Fernanda Manrique Sotelo");
        listaProfesionales.add(p);
        p = new Profesional("Camilo Stiven Ibarra Nuñez");
        listaProfesionales.add(p);
        p = new Profesional("Cristina Aguilera");
        listaProfesionales.add(p);

        //---------------------------------------------------------------------------

        AdaptadorProfesionales adaptador = new AdaptadorProfesionales(listaProfesionales);
        recyclerProfesionales.setAdapter(adaptador);
    }

    private void generarProfesionalesCardiologia(){
        //Arreglo de profesionales
        listaProfesionales = new ArrayList<>();
        recyclerProfesionales = findViewById(R.id.recyclerCardiologia);
        recyclerProfesionales.setLayoutManager(new LinearLayoutManager(this));

        //CREAMOS DE MANERA ESTÁTICA LA LISTA ---------------------------------------
        Profesional p = null;
        p = new Profesional("Milton Bermudez");
        listaProfesionales.add(p);
        p = new Profesional("Armando Casas");
        listaProfesionales.add(p);

        //---------------------------------------------------------------------------

        AdaptadorProfesionales adaptador = new AdaptadorProfesionales(listaProfesionales);
        recyclerProfesionales.setAdapter(adaptador);
    }
    private void generarProfesionalesFisioterapia(){
        //Arreglo de profesionales
        listaProfesionales = new ArrayList<>();
        recyclerProfesionales = findViewById(R.id.recyclerFisioterapia);
        recyclerProfesionales.setLayoutManager(new LinearLayoutManager(this));

        //CREAMOS DE MANERA ESTÁTICA LA LISTA ---------------------------------------
        Profesional p = null;
        p = new Profesional("Ana María Muñoz Fernández");
        listaProfesionales.add(p);
        p = new Profesional("Laura Andrea Mamián Cerón");
        listaProfesionales.add(p);
        p = new Profesional("Carlos Alberto Duque Manríquez");
        listaProfesionales.add(p);
        //---------------------------------------------------------------------------

        AdaptadorProfesionales adaptador = new AdaptadorProfesionales(listaProfesionales);
        recyclerProfesionales.setAdapter(adaptador);
    }
    private void generarProfesionalesFonoaudiologia(){
        //Arreglo de profesionales
        listaProfesionales = new ArrayList<>();
        recyclerProfesionales = findViewById(R.id.recyclerFonoaudiologia);
        recyclerProfesionales.setLayoutManager(new LinearLayoutManager(this));

        //CREAMOS DE MANERA ESTÁTICA LA LISTA ---------------------------------------
        Profesional p = null;
        p = new Profesional("Ana María Muñoz Fernández");
        listaProfesionales.add(p);
        p = new Profesional("Laura Andrea Mamián Cerón");
        listaProfesionales.add(p);
        p = new Profesional("Carlos Alberto Duque Manríquez");
        listaProfesionales.add(p);
        //---------------------------------------------------------------------------

        AdaptadorProfesionales adaptador = new AdaptadorProfesionales(listaProfesionales);
        recyclerProfesionales.setAdapter(adaptador);
    }
    private void generarProfesionalesGinecologia(){
        //Arreglo de profesionales
        listaProfesionales = new ArrayList<>();
        recyclerProfesionales = findViewById(R.id.recyclerGinecologia);
        recyclerProfesionales.setLayoutManager(new LinearLayoutManager(this));

        //CREAMOS DE MANERA ESTÁTICA LA LISTA ---------------------------------------
        Profesional p = null;
        p = new Profesional("Ana María Muñoz Fernández");
        listaProfesionales.add(p);
        p = new Profesional("Laura Andrea Mamián Cerón");
        listaProfesionales.add(p);
        p = new Profesional("Carlos Alberto Duque Manríquez");
        listaProfesionales.add(p);
        //---------------------------------------------------------------------------

        AdaptadorProfesionales adaptador = new AdaptadorProfesionales(listaProfesionales);
        recyclerProfesionales.setAdapter(adaptador);
    }
    private void generarProfesionalesOdontologia(){
        //Arreglo de profesionales
        listaProfesionales = new ArrayList<>();
        recyclerProfesionales = findViewById(R.id.recyclerOdontologia);
        recyclerProfesionales.setLayoutManager(new LinearLayoutManager(this));

        //CREAMOS DE MANERA ESTÁTICA LA LISTA ---------------------------------------
        Profesional p = null;
        p = new Profesional("Ana María Muñoz Fernández");
        listaProfesionales.add(p);
        p = new Profesional("Laura Andrea Mamián Cerón");
        listaProfesionales.add(p);
        p = new Profesional("Carlos Alberto Duque Manríquez");
        listaProfesionales.add(p);
        //---------------------------------------------------------------------------

        AdaptadorProfesionales adaptador = new AdaptadorProfesionales(listaProfesionales);
        recyclerProfesionales.setAdapter(adaptador);
    }
    private void generarProfesionalesOftalmologia(){
        //Arreglo de profesionales
        listaProfesionales = new ArrayList<>();
        recyclerProfesionales = findViewById(R.id.recyclerOftalmologia);
        recyclerProfesionales.setLayoutManager(new LinearLayoutManager(this));

        //CREAMOS DE MANERA ESTÁTICA LA LISTA ---------------------------------------
        Profesional p = null;
        p = new Profesional("Ana María Muñoz Fernández");
        listaProfesionales.add(p);
        p = new Profesional("Laura Andrea Mamián Cerón");
        listaProfesionales.add(p);
        p = new Profesional("Carlos Alberto Duque Manríquez");
        listaProfesionales.add(p);
        //---------------------------------------------------------------------------

        AdaptadorProfesionales adaptador = new AdaptadorProfesionales(listaProfesionales);
        recyclerProfesionales.setAdapter(adaptador);
    }
    private void generarProfesionalesOncologia(){
        //Arreglo de profesionales
        listaProfesionales = new ArrayList<>();
        recyclerProfesionales = findViewById(R.id.recyclerOncologia);
        recyclerProfesionales.setLayoutManager(new LinearLayoutManager(this));

        //CREAMOS DE MANERA ESTÁTICA LA LISTA ---------------------------------------
        Profesional p = null;
        p = new Profesional("Ana María Muñoz Fernández");
        listaProfesionales.add(p);
        p = new Profesional("Laura Andrea Mamián Cerón");
        listaProfesionales.add(p);
        p = new Profesional("Carlos Alberto Duque Manríquez");
        listaProfesionales.add(p);
        //---------------------------------------------------------------------------

        AdaptadorProfesionales adaptador = new AdaptadorProfesionales(listaProfesionales);
        recyclerProfesionales.setAdapter(adaptador);
    }
    private void generarProfesionalesTraumatologia(){
        //Arreglo de profesionales
        listaProfesionales = new ArrayList<>();
        recyclerProfesionales = findViewById(R.id.recyclerTraumatologia);
        recyclerProfesionales.setLayoutManager(new LinearLayoutManager(this));

        //CREAMOS DE MANERA ESTÁTICA LA LISTA ---------------------------------------
        Profesional p = null;
        p = new Profesional("Ana María Muñoz Fernández");
        listaProfesionales.add(p);
        p = new Profesional("Laura Andrea Mamián Cerón");
        listaProfesionales.add(p);
        p = new Profesional("Carlos Alberto Duque Manríquez");
        listaProfesionales.add(p);
        //---------------------------------------------------------------------------

        AdaptadorProfesionales adaptador = new AdaptadorProfesionales(listaProfesionales);
        recyclerProfesionales.setAdapter(adaptador);
    }
}