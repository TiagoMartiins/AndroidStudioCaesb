package com.example.loginwindow.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginwindow.R;
import com.example.loginwindow.model.Encargo;

import java.util.List;

public class AdapterEncargo extends RecyclerView.Adapter<AdapterEncargo.MyViewHolder> {

    private List<Encargo> listaEncargo;


    public AdapterEncargo(List<Encargo> listaEncargo) {
        this.listaEncargo = listaEncargo;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_lista,parent,false);
        return new MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Encargo encargo = listaEncargo.get( position );
        holder.titulo.setText(encargo.getTitulo());
        holder.descricao.setText(encargo.getDescricao());
        String data = String.valueOf(encargo.getData());
        holder.data.setText(data);

    }

    @Override
    public int getItemCount() {
        return listaEncargo.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView titulo;
        TextView descricao;
        TextView data;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            titulo = itemView.findViewById(R.id.titulo);
            descricao = itemView.findViewById(R.id.descricao);
            data = itemView.findViewById(R.id.data);
        }
    }
}
