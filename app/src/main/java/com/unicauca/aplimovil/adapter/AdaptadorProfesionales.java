package com.unicauca.aplimovil.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;
import com.unicauca.aplimovil.R;
import com.unicauca.aplimovil.modelo.Profesional;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class AdaptadorProfesionales extends RecyclerView.Adapter<AdaptadorProfesionales.ViewHolderProfesionales> {

    private List<Profesional> listaProfesionales;

    public AdaptadorProfesionales(List<Profesional> listaProfesionales) {
        this.listaProfesionales = listaProfesionales;
    }

    private List<Profesional> getListaProfesionales(){
        if(listaProfesionales == null)
            listaProfesionales = new ArrayList<>();
        return listaProfesionales;
    }

    @NonNull
    @Override
    public AdaptadorProfesionales.ViewHolderProfesionales onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_profesionales, null, false);
        return new ViewHolderProfesionales(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdaptadorProfesionales.ViewHolderProfesionales holder, int position) {
        Profesional p = listaProfesionales.get(position);
        holder.tvNombre.setText(p.getNombre());

    }

    @Override
    public int getItemCount() {
        return getListaProfesionales().size();
    }

    public class ViewHolderProfesionales extends RecyclerView.ViewHolder{
        TextView tvNombre;

        public ViewHolderProfesionales(@NonNull View itemView) {
            super(itemView);
            tvNombre = (TextView) itemView.findViewById(R.id.idNombre);

        }
    }
}