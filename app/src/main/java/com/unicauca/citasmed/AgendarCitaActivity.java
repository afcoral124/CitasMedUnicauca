package com.unicauca.citasmed;

import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.unicauca.citasmed.adapter.AdaptadorProfesionales;
import com.unicauca.citasmed.modelo.Cita;
import com.unicauca.citasmed.modelo.Profesional;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import citasmed.R;

public class AgendarCitaActivity extends AppCompatActivity {
    private TextView tvFechaDato;
    private int id_profesion;
    private int id_profesional;
    private String horaSeleccionada="";

    private TextView tvProfesionProfesional;
    private TextView tvNombreProfesional;

    private DatabaseReference myRef;

    private LinearLayout layoutHoras;
    private RelativeLayout relativeAgendar;

    private CardView btn9am;
    private CardView btn10am;
    private CardView btn3pm;
    private CardView btn4pm;

    private TextView tv9am;
    private TextView tv10am;
    private TextView tv3pm;
    private TextView tv4pm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar_cita);

        //Conexión a la base de datos
        myRef = FirebaseDatabase.getInstance().getReference(); //referencia al nodo principal

        //Recibimos el dato del médico seleccionado del intent
        Intent intent = getIntent();
        String message = intent.getStringExtra(FragmentMedGeneral.EXTRA_MESSAGE);
        id_profesional = Integer.valueOf(message);

        System.out.println("El intent recibió el id profesional: "+id_profesional);
        consultarProfesional();

        //Relacionando las variables con los elementos xml
        tvFechaDato = findViewById(R.id.tvFechaDato);
        tvNombreProfesional= findViewById(R.id.tvNombreProfesional);
        tvProfesionProfesional= findViewById(R.id.tvProfesionProfesional);
        layoutHoras = findViewById(R.id.lyHoras);
        relativeAgendar = findViewById(R.id.rlbtnAgendarCita);
        btn9am = findViewById(R.id.btn9am);
        btn10am = findViewById(R.id.btn10am);
        btn3pm = findViewById(R.id.btn3pm);
        btn4pm = findViewById(R.id.btn4pm);
        tv9am = findViewById(R.id.tv9am);
        tv10am = findViewById(R.id.tv10am);
        tv3pm = findViewById(R.id.tv3pm);
        tv4pm = findViewById(R.id.tv4pm);

        //Haciendo invisibles al contenedor de horas y del boton Agendar Cita
        layoutHoras.setVisibility(View.INVISIBLE);
        relativeAgendar.setVisibility(View.INVISIBLE);

    }


    public void consultarProfesional(){

        //Consulta 1: nombre
        Query q = myRef.child("Profesionales").orderByChild("id_profesional").equalTo(id_profesional);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    List<Profesional> value = new ArrayList<>();
                    for(DataSnapshot ds : snapshot.getChildren()) {
                        Profesional persona = ds.getValue(Profesional.class);
                            value.add(persona);
                    }
                        tvNombreProfesional.setText(value.get(0).getNombre());
                        id_profesion = value.get(0).getId_profesion();
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.getMessage()); //Don't ignore errors!
            }
        });

        //Consulta 2: profesion
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
                    tvProfesionProfesional.setText(value2.get(id_profesion));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.getMessage()); //Don't ignore errors!
            }
        });

    }

    public void abrirCalendario(View view) {
        Calendar calendario = Calendar.getInstance();
        int year = calendario.get(Calendar.YEAR);
        int month = calendario.get(Calendar.MONTH);
        int day = calendario.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dpd = new DatePickerDialog(AgendarCitaActivity.this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                String fecha = i2 + "/" + (i1+1) + "/" + i;
                tvFechaDato.setText(fecha);
                if(!tvFechaDato.equals("__ /__ /____")){
                    System.out.println("Cambió la fecha seleccionada en el calendario de la app");
                    activarElementos();
                    consultarAgendaProfesional();
                }
            }
        }, year, month, day);
        dpd.show();


    }

    private void activarElementos(){
        layoutHoras.setVisibility(View.VISIBLE);
        btn9am.setVisibility(View.VISIBLE);
        btn10am.setVisibility(View.VISIBLE);
        btn3pm.setVisibility(View.VISIBLE);
        btn4pm.setVisibility(View.VISIBLE);
    }

    private void consultarAgendaProfesional() {

        //Leer las citas asociadas al profesional escogido, y en la fecha escogida

        Query q = myRef.child("Citas").orderByChild("id_profesional").equalTo(id_profesional);
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                   /* GenericTypeIndicator<ArrayList<Profesional>> t = new GenericTypeIndicator<ArrayList<Profesional>>() {};
                    ArrayList<Profesional> value = snapshot.getValue(t);
                    System.out.println(" valor "+value.get(0).getNombre());*/
                    List<Cita> citasDelDiaEscogido = new ArrayList<>();
                    for(DataSnapshot ds : snapshot.getChildren()) {

                        Cita citaExistente = ds.getValue(Cita.class);
                        System.out.println("---------------------------------------------------------------------");
                        System.out.println("La fecha de esta cita es: "+citaExistente.getFecha());
                        System.out.println("tvFechaDato es igual a: "+tvFechaDato.getText());
                        System.out.println("Comparando las dos fechas de arriba...");

                        if (citaExistente.getFecha().equals(tvFechaDato.getText())){

                            System.out.println("Coincidieron las dos fechas");
                            //Citas pendientes del médico escogido en la fecha escogida
                            citasDelDiaEscogido.add(citaExistente);
                            System.out.println("La hora de esta cita es: "+citaExistente.getHora());

                        }else{

                            System.out.println("No coincidieron las dos fechas: F");

                        }
                    }
                    System.out.println("El numero de citas del médico en la fecha "+ tvFechaDato.getText() +" es: "+citasDelDiaEscogido.size());

                    //Analizamos las horas de las citas de esta fecha
                    for (int i= 0; i<citasDelDiaEscogido.size();i++){

                        //Si ya había una cita a las 9am
                        if(citasDelDiaEscogido.get(i).getHora().equals(tv9am.getText())){
                            btn9am.setVisibility(View.GONE);
                            System.out.println("la cita con id "+citasDelDiaEscogido.get(i).getId_cita()+" coincide con la hora 9AM");
                        }

                        //Si ya había una cita a las 10am
                        if(citasDelDiaEscogido.get(i).getHora().equals(tv10am.getText())){
                            btn10am.setVisibility(View.GONE);
                            System.out.println("la cita con id "+citasDelDiaEscogido.get(i).getId_cita()+" coincide con la hora 10AM");
                        }

                        //Si ya había una cita a las 3pm
                        if(citasDelDiaEscogido.get(i).getHora().equals(tv3pm.getText())){
                            btn3pm.setVisibility(View.GONE);
                            System.out.println("la cita con id "+citasDelDiaEscogido.get(i).getId_cita()+" coincide con la hora 3PM");
                        }

                        //Si ya había una cita a las 4pm
                        if(citasDelDiaEscogido.get(i).getHora().equals(tv4pm.getText())){
                            btn4pm.setVisibility(View.GONE);
                            System.out.println("la cita con id "+citasDelDiaEscogido.get(i).getId_cita()+" coincide con la hora 4PM");
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.getMessage()); //Don't ignore errors!
            }
        });


        //Si no hay citas ya programadas entonces todos los botones se hacen visibles

        //Si hay alguna cita ya programada, entonces esa hora ya no se muestra, el resto sí


    }


    public void seleccionarHora(View view) {
    }







    public void irAtras(View view){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



}
