package com.example.loginwindow.adapter;

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
import com.example.loginwindow.model.Destino;
import com.example.loginwindow.model.Origem;

import java.util.List;

public class AdapterDestino extends RecyclerView.Adapter<AdapterDestino.MyViewHolder> {
    private List<Destino> listaDestino;
    private Animation animation;


    public AdapterDestino(List<Destino> listaDestino) {
        this.listaDestino = listaDestino;
    }

    @NonNull
    @Override
    public AdapterDestino.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_destino,parent,false);
        animation = new AlphaAnimation(1, 0.5f); // Altera alpha de visível a invisível
        animation.setDuration(500); // duração - meio segundo
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE); // Repetir infinitamente
        animation.setRepeatMode(Animation.REVERSE); //Inverte a animação no final para que o botão vá desaparecendo
        return new AdapterDestino.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterDestino.MyViewHolder holder, int position) {

        Destino destino = listaDestino.get( position );
        holder.local.setText(destino.getLocal());
        holder.hora.setText(destino.getHora());
        holder.km.setText(Integer.toString(destino.getKm()));
//        holder.itemView.startAnimation(animation);

    }

    @Override
    public int getItemCount() {
        return listaDestino.size();
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
