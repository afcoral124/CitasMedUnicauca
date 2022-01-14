package com.unicauca.citasmed;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;

import java.util.Calendar;

import citasmed.R;

public class AgendarCitaActivity extends AppCompatActivity {
    TextView tvFechaDato;
    private int id_profesional;
    private String horaSeleccionada="";
    private DatabaseReference myRef;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agendar_cita);

        //Recibimos el dato del médico seleccionado
        Intent intent = getIntent();
        String message = intent.getStringExtra(FragmentMedGeneral.EXTRA_MESSAGE);
        id_profesional = Integer.valueOf(message);
        tvFechaDato = findViewById(R.id.tvFechaDato);
        System.out.println("El intent recibió el id profesional: "+id_profesional);

        //Poniendo invisibles los ultimos datos
        LinearLayout layoutHoras = findViewById(R.id.lyHoras);
        layoutHoras.setVisibility(View.INVISIBLE);

        RelativeLayout relativeAgendar = findViewById(R.id.rlbtnAgendarCita);
        relativeAgendar.setVisibility(View.INVISIBLE);

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
            }
        }, year, month, day);
        dpd.show();

        if(!tvFechaDato.equals("__ /__ /____")){
            consultarAgendaProfesional();
        }
    }

    private void consultarAgendaProfesional() {
        //Leer las citas asociadas al profesional escogido, y en la fecha escogida

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
