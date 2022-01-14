package com.unicauca.citasmed;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

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
 * Use the {@link FragmentOncologia#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentOncologia extends Fragment implements AdapterView.OnItemSelectedListener {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private String ciudad;
    private View vista;
    public static final String EXTRA_MESSAGE = "com.unicauca.citasmed.MESSAGE";

    private RecyclerView recyclerProfesionales;
    public LinearLayout opcion;                  //Es el Layout que se muestra al seleccionar un item del Spinner
    private DatabaseReference myRef;

    public FragmentOncologia() {
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
    public static FragmentOncologia newInstance(String param1, String param2) {
        FragmentOncologia fragment = new FragmentOncologia();
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

        vista = inflater.inflate(R.layout.fragment_oncologos, container, false);
        llenarSpinner(vista);
        recyclerProfesionales= (RecyclerView) vista.findViewById(R.id.RecyclerOncologia);
        return vista;
    }

    public void llenarSpinner(View vista) {
        Spinner spinner = (Spinner) vista.findViewById(R.id.spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.ciudades, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // An item was selected. You can retrieve the selected item using
        //System.out.println(parent.getItemAtPosition(pos));
        ciudad = String.valueOf(parent.getItemAtPosition(pos));
        mostrarListaProfesionales(vista);
        Log.d("Total", String.valueOf(parent.getItemAtPosition(pos)));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void mostrarListaProfesionales(View vista){
        opcion = vista.findViewById(R.id.lyOncologia);
        opcion.setVisibility(View.VISIBLE);
        generarProfesionales(7);

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
                        if (persona.getCiudad().equals(ciudad)){
                            value.add(persona);
                        }
                    }
                    AdaptadorProfesionales adaptador = new AdaptadorProfesionales(value);
                    adaptador.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getContext(), "Seleccion: "+value.get
                                            (recyclerProfesionales.getChildAdapterPosition(view)).getNombre(),
                                    Toast.LENGTH_SHORT).show();

                            //Llamar a la actividad de agendar cita, con un intent que nos guarde
                            //la información del medico seleccionado
                            Intent intent = new Intent(getContext(), AgendarCitaActivity.class);
                            String message = String.valueOf(value.get(recyclerProfesionales.getChildAdapterPosition(view)).getId_profesional());
                            intent.putExtra(EXTRA_MESSAGE, message);
                            startActivity(intent);
                        }
                    });
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