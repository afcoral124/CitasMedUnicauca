package com.unicauca.aplimovil;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    FragmentCategorias fragCategorias;
    FragmentProfesionales fragProfesionales;
    int opcion=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragCategorias = new FragmentCategorias();

        getSupportFragmentManager().beginTransaction().add(R.id.contenedorFragmentos, fragCategorias).commit();
    }

    public void onClick(View view) {
        fragProfesionales = new FragmentProfesionales();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        switch (view.getId()){
            case R.id.MedicoGeneral:
                opcion=1;
                transaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragmentos, fragProfesionales);
                break;
            case R.id.cardAtras:
                if (opcion==1) {
                    opcion=0;
                    transaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragmentos, fragCategorias);
                }
                else{
                    opcion=1;
                    transaction = getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragmentos, fragProfesionales);
                }
                break;
        }transaction.commit();
    }
}