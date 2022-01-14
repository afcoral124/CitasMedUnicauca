package com.unicauca.citasmed.adapter;

import static android.content.ContentValues.TAG;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.unicauca.citasmed.AgendarCitaActivity;
import com.unicauca.citasmed.modelo.Cita;
import com.unicauca.citasmed.modelo.Profesional;

import java.util.ArrayList;
import java.util.List;

import citasmed.R;


public class AdaptadorCitas extends RecyclerView.Adapter<AdaptadorCitas.ViewHolderCitas> implements View.OnClickListener {

    private List<Cita> listaCitas;
    private View.OnClickListener listener;
    private DatabaseReference myRef;
    private Profesional p;
    private String profesion;

    public AdaptadorCitas(List<Cita> listaCitas) {
        this.listaCitas = listaCitas;
    }

    private List<Cita> getListaCitas(){
        if(listaCitas == null)
            listaCitas = new ArrayList<>();
        return listaCitas;
    }

    @NonNull
    @Override
    public AdaptadorCitas.ViewHolderCitas onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_citas, null, false);

        view.setOnClickListener(this);

        return new ViewHolderCitas(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorCitas.ViewHolderCitas holder, int position) {
        //Obtengo la cita actual (itera según la posición que dibuja el recyclerview)
        Cita c = listaCitas.get(position);

        //Conexion a la DB
        myRef = FirebaseDatabase.getInstance().getReference(); //referencia al nodo principal

        //Consulta 1: Profesional de la Cita actual
        Query q = myRef.child("Profesionales").orderByChild("id_profesional").equalTo(c.getId_profesional());
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    List<Profesional> value = new ArrayList<>();
                    for(DataSnapshot ds : snapshot.getChildren()) {
                        Profesional persona = ds.getValue(Profesional.class);
                            value.add(persona);
                        //profesional p es el unico de la lista value
                        p = value.get(0);
                        System.out.println(p.getNombre());
                        holder.tvProfesional.setText(p.getNombre());
                        holder.tvCiudad.setText(p.getCiudad());
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.getMessage()); //Don't ignore errors!
            }
        });
        //Grafico los datos del profesional


        //Consulta 2: profesion del profesional de la cita actual
        Query q2 =  myRef.child("Profesiones");
        q2.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    List<String> value2 = new ArrayList<>();
                    for(DataSnapshot ds : snapshot.getChildren()) {
                        String profesion = ds.getValue(String.class);
                        value2.add(profesion);
                    }
                    //Grafico el dato de profesión
                    profesion=value2.get(p.getId_profesion());
                    System.out.println("profesion es: "+profesion);
                    holder.tvProfesion.setText(profesion);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.getMessage()); //Don't ignore errors!
            }
        });


        //Grafico los datos de fecha y hora que sí dependen del objeto cita
        holder.tvFecha.setText(c.getFecha());
        holder.tvHora.setText(c.getHora());


    }

    @Override
    public int getItemCount() {
        return getListaCitas().size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class ViewHolderCitas extends RecyclerView.ViewHolder{
        TextView tvProfesion;
        TextView tvProfesional;
        TextView tvCiudad;
        TextView tvFecha;
        TextView tvHora;

        public ViewHolderCitas(@NonNull View itemView) {
            super(itemView);
            tvProfesion = (TextView) itemView.findViewById(R.id.tvProfesion);
            tvProfesional = (TextView) itemView.findViewById(R.id.tvNombreProfesional);
            tvCiudad = (TextView) itemView.findViewById(R.id.tvCiudad);
            tvFecha = (TextView) itemView.findViewById(R.id.tvFecha);
            tvHora = (TextView) itemView.findViewById(R.id.tvHora);
        }
    }
}