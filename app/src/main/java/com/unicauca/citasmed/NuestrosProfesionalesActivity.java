package com.unicauca.citasmed;
import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.unicauca.citasmed.adapter.AdaptadorProfesionales;
import com.unicauca.citasmed.modelo.Profesional;
import citasmed.R;


public class NuestrosProfesionalesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    private List<Profesional> listaProfesionales;
    private ArrayList<Profesional> listaMedGeneral;
    private RecyclerView recyclerProfesionales;
    public LinearLayout opcion;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuestros_profesionales);

        llenarSpinner();

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
        double eleccion;

        switch (String.valueOf(parent.getItemAtPosition(pos))){
            case "Med.General":
                opcion = findViewById(R.id.LyMed_General);
                opcion.setVisibility(View.VISIBLE);
                recyclerProfesionales = findViewById(R.id.recyclerMedGeneral);
                eleccion=0;
                break;
            case "Cardiología":
                opcion = findViewById(R.id.LyCardiologia);
                opcion.setVisibility(View.VISIBLE);
                recyclerProfesionales = findViewById(R.id.recyclerCardiologia);
                eleccion=1;
                break;
            case "Fisioterapia":
                opcion = findViewById(R.id.LyFisioterapia);
                opcion.setVisibility(View.VISIBLE);
                recyclerProfesionales = findViewById(R.id.recyclerFisioterapia);
                eleccion=2;
                break;
            case "Fonoaudiología":
                opcion = findViewById(R.id.LyFonoaudiologia);
                opcion.setVisibility(View.VISIBLE);
                recyclerProfesionales = findViewById(R.id.recyclerFonoaudiologia);
                eleccion=3;
                break;
            case "Ginecología":
                opcion = findViewById(R.id.LyGinecologia);
                opcion.setVisibility(View.VISIBLE);
                recyclerProfesionales = findViewById(R.id.recyclerGinecologia);
                eleccion=4;
                break;
            case "Odontología":
                opcion = findViewById(R.id.LyOdontologia);
                opcion.setVisibility(View.VISIBLE);
                recyclerProfesionales = findViewById(R.id.recyclerOdontologia);
                eleccion=5;
                break;
            case "Oftalmología":
                opcion = findViewById(R.id.LyOftalmologia);
                opcion.setVisibility(View.VISIBLE);
                recyclerProfesionales = findViewById(R.id.recyclerOftalmologia);
                eleccion=6;
                break;
            case "Oncología":
                opcion = findViewById(R.id.LyOncologia);
                opcion.setVisibility(View.VISIBLE);
                recyclerProfesionales = findViewById(R.id.recyclerOncologia);
                eleccion=7;
                break;
            case "Traumatología":
                opcion = findViewById(R.id.LyTraumatologia);
                opcion.setVisibility(View.VISIBLE);
                recyclerProfesionales = findViewById(R.id.recyclerTraumatologia);
                eleccion=8;
                break;

            default:
                Log.d("Total", String.valueOf(parent.getItemAtPosition(pos)));
                eleccion=9;
                recyclerProfesionales = findViewById(R.id.recyclerMedGeneral);
                break;
        }
        generarProfesionales(eleccion);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
    //Lectura en la DB
    private void generarProfesionales(double eleccion){
        //Arreglo de profesionales
        System.out.println(eleccion);


        recyclerProfesionales.setLayoutManager(new LinearLayoutManager(this));

        myRef = FirebaseDatabase.getInstance().getReference(); //referencia al nodo principal
        Query q = myRef.child("Profesionales").orderByChild("id_profesion").equalTo(eleccion);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                   /* GenericTypeIndicator<ArrayList<Profesional>> t = new GenericTypeIndicator<ArrayList<Profesional>>() {};
                    ArrayList<Profesional> value = snapshot.getValue(t);
                    System.out.println(" valor "+value.get(0).getNombre());*/
                    List<Profesional> value = new ArrayList<>();
                    for(DataSnapshot ds : snapshot.getChildren()) {
                        Profesional persona = ds.getValue(Profesional.class);
                        value.add(persona);
                    }
                    AdaptadorProfesionales adaptador = new AdaptadorProfesionales(value);
                    recyclerProfesionales.setAdapter(adaptador);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.getMessage()); //Don't ignore errors!
            }
        });


        //Escritura en la DB
       // FirebaseDatabase database = FirebaseDatabase.getInstance();
       // DatabaseReference myRef = database.getReference("Profesionales"); //clave
        //myRef.setValue("hola"); //valor

        //---------------------------------------------------------------------------
    }
}