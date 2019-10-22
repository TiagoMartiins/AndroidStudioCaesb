package com.example.loginwindow.adapter;

import android.graphics.Point;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginwindow.R;
import com.example.loginwindow.model.OrdemServico;
import com.example.loginwindow.model.Origem;

import java.util.List;

public class AdapterOrigem extends RecyclerView.Adapter<AdapterOrigem.MyViewHolder> {
    private List<Origem> listaOrigem;
    private Animation animation;


    public AdapterOrigem(List<Origem> listaOrigem) {
        this.listaOrigem = listaOrigem;
    }

    @NonNull
    @Override
    public AdapterOrigem.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_origem,parent,false);
        animation = new AlphaAnimation(1, 0.5f); // Altera alpha de visível a invisível
        animation.setDuration(500); // duração - meio segundo
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE); // Repetir infinitamente
        animation.setRepeatMode(Animation.REVERSE); //Inverte a animação no final para que o botão vá desaparecendo
        return new AdapterOrigem.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOrigem.MyViewHolder holder, int position) {

        Origem origem = listaOrigem.get( position );
        holder.local.setText(origem.getLocal());
        holder.hora.setText(origem.getHora());
        holder.km.setText(Integer.toString(origem.getKm()));
//        holder.itemView.startAnimation(animation);

    }

    @Override
    public int getItemCount() {
        return listaOrigem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView local;
        TextView hora;
        TextView km;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            local = itemView.findViewById(R.id.local);
            hora = itemView.findViewById(R.id.hora);
            km = itemView.findViewById(R.id.kms);
        }
    }
}
