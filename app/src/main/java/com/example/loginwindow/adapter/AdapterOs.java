package com.example.loginwindow.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginwindow.R;
import com.example.loginwindow.model.OrdemServico;

import java.util.List;

public class AdapterOs extends RecyclerView.Adapter<AdapterOs.MyViewHolder> {

    private List<OrdemServico> listaOs;


    public AdapterOs(List<OrdemServico> listaOs) {
        this.listaOs = listaOs;
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

        OrdemServico os = listaOs.get( position );
        holder.titulo.setText(os.getTitulo());
        holder.descricao.setText(os.getDescricao());
        String data = String.valueOf(os.getData());
        holder.data.setText(data);

    }

    @Override
    public int getItemCount() {
        return listaOs.size();
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
