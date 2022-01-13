package com.unicauca.citasmed;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentProfesionales#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentProfesionales extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RecyclerView recyclerProfesionales;
    public LinearLayout opcion;                  //Es el Layout que se muestra al seleccionar un item del Spinner
    private DatabaseReference myRef;

    public FragmentProfesionales() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentMusica.
     */
    // TODO: Rename and change types and number of parameters
    public static FragmentProfesionales newInstance(String param1, String param2) {
        FragmentProfesionales fragment = new FragmentProfesionales();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View vista = inflater.inflate(R.layout.fragment_profesionales, container, false);
        recyclerProfesionales= (RecyclerView) vista.findViewById(R.id.recyclerMedGeneral);
        mostrarListaProfesionales(vista);
        return vista;
    }

    public void mostrarListaProfesionales(View vista){
        ocultarOpciones(vista);
        opcion = vista.findViewById(R.id.lyMed_General);
        opcion.setVisibility(View.VISIBLE);
        recyclerProfesionales = vista.findViewById(R.id.RecyclerMedGeneral);
        generarProfesionales(0);

    }

    public void seleccionarProfesional(View view){

    }

    public void ocultarOpciones(View vista) {
        LinearLayout medgeneral = vista.findViewById(R.id.lyMed_General);
        LinearLayout cardiologia = vista.findViewById(R.id.lyCardiologia);
        LinearLayout fisioterapia = vista.findViewById(R.id.lyFisioterapia);
        LinearLayout fonoaudiologia = vista.findViewById(R.id.lyFonoaudiologia);
        LinearLayout ginecologia = vista.findViewById(R.id.lyGinecologia);
        LinearLayout odontologia = vista.findViewById(R.id.lyOdontologia);
        LinearLayout oftalmologia = vista.findViewById(R.id.lyOftalmologia);
        LinearLayout oncologia = vista.findViewById(R.id.lyOncologia);
        LinearLayout traumatologia = vista.findViewById(R.id.lyTraumatologia);

        System.out.println("Hasta aquí va bien interno");
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

    private void generarProfesionales(double eleccion){
        //Arreglo de profesionales
        System.out.println(eleccion);


        recyclerProfesionales.setLayoutManager(new LinearLayoutManager(getContext()));

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