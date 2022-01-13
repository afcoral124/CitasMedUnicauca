package com.unicauca.citasmed;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
 * Use the {@link FragmentCardiologia#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentCardiologia extends Fragment {

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

    public FragmentCardiologia() {
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
    public static FragmentCardiologia newInstance(String param1, String param2) {
        FragmentCardiologia fragment = new FragmentCardiologia();
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

        View vista = inflater.inflate(R.layout.fragment_cardiologos, container, false);
        recyclerProfesionales= (RecyclerView) vista.findViewById(R.id.RecyclerCardiologia);
        mostrarListaProfesionales(vista);
        return vista;
    }

    public void mostrarListaProfesionales(View vista){
        opcion = vista.findViewById(R.id.lyCardiologia);
        opcion.setVisibility(View.VISIBLE);
        generarProfesionales(1);

    }

    public void seleccionarProfesional(View view){

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