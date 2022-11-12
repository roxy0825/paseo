package com.example.paseo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class PaseoAdapter  extends RecyclerView.Adapter <PaseoAdapter.paseoViewHolder>{
    ArrayList<ClsPaseo> obpaseo;
    public PaseoAdapter(ArrayList<ClsPaseo> obpaseo) {
        this.obpaseo = obpaseo;
    }





    @NonNull
    @Override
    public paseoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.paseoresource,null,false);
        return new PaseoAdapter.paseoViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull paseoViewHolder holder, int position) {
        holder.codigo.setText(obpaseo.get(position).getCodigo());
        holder.nombre.setText(obpaseo.get(position).getNombre());
        holder.ciudad.setText(obpaseo.get(position).getCiudad());
        holder.cantidad.setText(obpaseo.get(position).getCantidad());

    }

    @Override
    public int getItemCount() {
        return obpaseo.size();
    }

    public static class paseoViewHolder extends RecyclerView.ViewHolder {
        TextView codigo, nombre, ciudad, cantidad;
        public paseoViewHolder(@NonNull View itemView) {
            super(itemView);
            codigo = itemView.findViewById(R.id.etcodigo);
            nombre = itemView.findViewById(R.id.etnombre);
            ciudad = itemView.findViewById(R.id.etciudad);
            cantidad = itemView.findViewById(R.id.etcantidad);

        }

    }
}
