package com.unicauca.aplimovil;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import com.unicauca.aplimovil.adapter.AdaptadorProfesionales;
import com.unicauca.aplimovil.modelo.Profesional;


public class NuestrosProfesionalesActivity extends AppCompatActivity {
    private List<Profesional> listaProfesionales;
    private RecyclerView recyclerProfesionales;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuestros_profesionales);

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