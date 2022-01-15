package com.unicauca.citasmed;

import static android.content.ContentValues.TAG;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
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
import com.unicauca.citasmed.db.DbUsuarios;
import com.unicauca.citasmed.modelo.Cita;
import com.unicauca.citasmed.modelo.Paciente;
import com.unicauca.citasmed.modelo.Profesional;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import citasmed.R;

public class AgendarCitaActivity extends AppCompatActivity {
    private TextView tvFechaDato;
    private int id_profesion;
    private int id_profesional;
    private int id_cita;
    private int estadoCita;
    private int id_paciente;
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

    private DbUsuarios dbUsuarios;
    private Paciente paciente;

    public static final String EXTRA_MESSAGE = "com.unicauca.citasmed.MESSAGE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar_cita);

        //Conexión a la base de datos
        myRef = FirebaseDatabase.getInstance().getReference(); //referencia al nodo principal

        dbUsuarios = new DbUsuarios(AgendarCitaActivity.this);
        paciente = dbUsuarios.LeerUsuarios();


        //Recibimos el dato del médico seleccionado del intent
        Intent intent = getIntent();
        String message = intent.getStringExtra(FragmentMedGeneral.EXTRA_MESSAGE);


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

        if(message.startsWith("A")){
            String[] parts = message.split(",");
            id_profesional = Integer.valueOf(parts[1]); // 123
            tvFechaDato.setText(parts[2]); // 654321
            consultarProfesional();
            activarElementos();
            consultarAgendaProfesional();
        }
        else {
            id_profesional = Integer.valueOf(message);
        }

        System.out.println("El intent recibió el id profesional: "+id_profesional);
        consultarProfesional();
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

        relativeAgendar.setVisibility(View.INVISIBLE);
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
    }


    public void seleccionarHora(View view) {
        tv9am.setBackgroundColor(Color.parseColor("#03A9F4"));//azul
        tv10am.setBackgroundColor(Color.parseColor("#03A9F4"));//azul
        tv3pm.setBackgroundColor(Color.parseColor("#03A9F4"));//azul
        tv4pm.setBackgroundColor(Color.parseColor("#03A9F4"));//azul

        switch (view.getId()) {
            case R.id.btn9am:
                horaSeleccionada = (String) tv9am.getText();
                tv9am.setBackgroundColor(Color.parseColor("#4CAF50"));//verde
                break;
            case R.id.btn10am:
                horaSeleccionada = (String) tv10am.getText();
                tv10am.setBackgroundColor(Color.parseColor("#4CAF50"));//verde
                break;
            case R.id.btn3pm:
                horaSeleccionada = (String) tv3pm.getText();
                tv3pm.setBackgroundColor(Color.parseColor("#4CAF50"));//verde
                break;
            case R.id.btn4pm:
                horaSeleccionada = (String) tv4pm.getText();
                tv4pm.setBackgroundColor(Color.parseColor("#4CAF50"));//verde
                break;
        }
        System.out.println("Se seleccionó la hora: "+ horaSeleccionada);
        relativeAgendar.setVisibility(View.VISIBLE);
    }

    public void agendarCita(View view) {
        //Se deben capturar todos los datos de una cita, para crear un objeto Cita con estos datos

        //id_cita----------------------------------------------
        //se busca en la base de datos cual es la cita con mayor id, y se le pone a esta el valor siguiente
        //Consulta: numero mayor de id en las citas
        Query q = myRef.child("Citas");
        q.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    id_cita = (int) snapshot.getChildrenCount();
                    /*List<Cita> value = new ArrayList<>();
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        Cita cita = ds.getValue(Cita.class);
                        System.out.println(cita.getId_cita());
                        value.add(cita);
                    }
                    */
                    System.out.printf(" el numero de nodos es "+id_cita);
                    crearCitaNueva();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d(TAG, error.getMessage()); //Don't ignore errors!
            }
        });

    }

    public void crearCitaNueva(){

        String fechaCita = (String) tvFechaDato.getText();

        estadoCita= 1;



        if(paciente != null){ //Si está logueado o si hay datos en SQLite

            id_paciente=paciente.getId_paciente();
            //esta Cita se envía a la base de datos para que quede almacenada
            Cita citaAgendada = new Cita(id_cita, fechaCita, horaSeleccionada, estadoCita, id_profesional, id_paciente);
            System.out.println("Se va a agendar la cita con los valores: ");
            System.out.println("id_cita: "+citaAgendada.getId_cita());
            System.out.println("fecha: "+citaAgendada.getFecha());
            System.out.println("hora: "+citaAgendada.getHora());
            System.out.println("estado: "+citaAgendada.getEstado());
            System.out.println("id_profesional: "+citaAgendada.getId_profesional());
            System.out.println("id_paciente: "+citaAgendada.getId_paciente());

            //Escritura en la DB
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef2 = database.getReference("Citas").child(String.valueOf(id_cita)); //clave
            myRef2.setValue(citaAgendada); //valor

            //DatabaseReference usersRef = database.child("Citas");
            //Map<String, Cita> users = new HashMap<>();
            //users.put("alanisawesome", user1);
            //usersRef.setValueAsync(users);

            //y se redirige al usuario al home activity con un Toast de "Cita agendada correctamente"
            Toast.makeText(AgendarCitaActivity.this, "Cita agendada correctamente", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, HomeActivity.class);
            startActivity(intent);

        }else{
            //Si no está logueado, entonces se redirige a interfaz iniciar sesión y la Cita no se manda a la base de datos
            Toast.makeText(AgendarCitaActivity.this, "Inicia sesión para agendar citas", Toast.LENGTH_LONG).show();

            //Llamar a la actividad de agendar cita, con un intent que nos guarde
            //la información del medico seleccionado
            Intent intent = new Intent(AgendarCitaActivity.this, IniciarSesionActivity.class);
            String message = "A,"+id_profesional+","+fechaCita;
            intent.putExtra(EXTRA_MESSAGE, message);
            startActivity(intent);

        }
    }





    public void irAtras(View view){
        onBackPressed();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }



}
