package com.example.loginwindow.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginwindow.R;
import com.example.loginwindow.model.Destino;
import com.example.loginwindow.model.Origem;

import java.util.List;

public class AdapterOrigemTeste extends RecyclerView.Adapter<AdapterOrigemTeste.MyViewHolder> {
    private List<Origem> listaOrigem;
    private Animation animation;
    private List<Destino> listaDestino;


    public AdapterOrigemTeste(List<Origem> listaOrigem, List<Destino> listaDestino) {
        this.listaOrigem = listaOrigem;
        this.listaDestino = listaDestino;
    }

    public AdapterOrigemTeste(List<Origem> listaOrigem){
        this.listaOrigem = listaOrigem;
    }

    @NonNull
    @Override
    public AdapterOrigemTeste.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemLista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_teste_origem_destino,parent,false);
        animation = new AlphaAnimation(1, 0.5f); // Altera alpha de visível a invisível
        animation.setDuration(500); // duração - meio segundo
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE); // Repetir infinitamente
        animation.setRepeatMode(Animation.REVERSE); //Inverte a animação no final para que o botão vá desaparecendo
        return new AdapterOrigemTeste.MyViewHolder(itemLista);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterOrigemTeste.MyViewHolder holder, int position) {

        Origem origem = listaOrigem.get( position );
        holder.local.setText(origem.getLocal());
        holder.hora.setText(origem.getHora());
        holder.km.setText(Integer.toString(origem.getKm()));
//        holder.itemView.startAnimation(animation);

        Destino destino = new Destino();

        System.out.println(listaOrigem.size());
        System.out.println(listaDestino.size());
        System.out.println("Primeira fora do IF"+position);
        System.out.println("Primeira dentro do IF"+position);
        System.out.println("listaDestino.size"+listaDestino.size());
        if(listaDestino.size() > position){
            destino = listaDestino.get(position);
            holder.localD.setText(destino.getLocal());
            holder.horaD.setText(destino.getHora());
            holder.kmD.setText(Integer.toString(destino.getKm()));
        }



    }

    @Override
    public int getItemCount() {
        return listaOrigem.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView local;
        TextView hora;
        TextView km;
        TextView localD;
        TextView horaD;
        TextView kmD;
        ConstraintLayout ctDestino;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            local = itemView.findViewById(R.id.local);
            hora = itemView.findViewById(R.id.hora);
            km = itemView.findViewById(R.id.kms);

            localD = itemView.findViewById(R.id.localD);
            horaD = itemView.findViewById(R.id.horaD);
            kmD = itemView.findViewById(R.id.kmsD);


            ctDestino = itemView.findViewById(R.id.constraintDestino);
        }
    }
}
