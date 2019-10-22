package com.example.loginwindow.fragment;


import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.loginwindow.R;
import com.example.loginwindow.activity.CadastroActivity;
import com.example.loginwindow.activity.VeiculoActivity;
import com.example.loginwindow.gps.GpsTracker;
import com.example.loginwindow.model.Origem;
import com.example.loginwindow.sqlite.OrigemSQLITE;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrigemFragment extends Fragment {

    TextInputEditText horas,km,local;
    Button btOk;
    ImageButton btLocation, btFechar;
    OrigemSQLITE banco;
    String localString,horaString;
    int kmInt;


    public OrigemFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_origem, container, false);
        banco = new OrigemSQLITE(this.getContext());

        horas = (TextInputEditText) view.findViewById(R.id.hora);
        km = (TextInputEditText) view.findViewById(R.id.km);
        local = (TextInputEditText) view.findViewById(R.id.local);

        btOk = (Button) view.findViewById(R.id.btOk);
        btLocation = (ImageButton) view.findViewById(R.id.btLocalizaçao);
        btFechar = (ImageButton) view.findViewById(R.id.btCancelar);



        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GpsTracker gps = new GpsTracker(OrigemFragment.this.getContext());
                if(gps.canGetLocation()){
                    local.setText(String.valueOf(gps.getLatitude()));
                }
                System.out.println("entrou");
            }
        });

        btFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent voltarTelaVeiculo = new Intent(OrigemFragment.this.getActivity(), VeiculoActivity.class);
                startActivity(voltarTelaVeiculo);
            }
        });

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verificarCampos()){

                    try {
                        banco.addOrigem(localString, horaString, kmInt);
                        Toast toast = Toast.makeText(OrigemFragment.this.getContext(), "Inserido com Sucesso", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    Intent voltarTelaVeiculo = new Intent(OrigemFragment.this.getActivity(),VeiculoActivity.class);
                    startActivity(voltarTelaVeiculo);
                }
            }
        });

        String data1, hora1;

        java.util.Date agora = new java.util.Date();
        SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
        data1 = formata.format(agora);
        formata = new SimpleDateFormat("hh:mm");
        formata.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
        hora1 = formata.format(agora);
        System.out.print(data1);
        System.out.print(hora1);

        horas.setText(hora1);

        return view;
    }

    public boolean verificarCampos(){

        //Recuperando campos da interface Cadastro
        localString = local.getText().toString().toUpperCase();
        horaString = horas.getText().toString();
        try {
            kmInt = Integer.parseInt(km.getText().toString());
        }catch (NumberFormatException e){
            Toast toast = Toast.makeText(OrigemFragment.this.getContext(), "Erro de formatação, tente novamente", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }

        if(localString.isEmpty()){
            Toast toast = Toast.makeText(OrigemFragment.this.getContext(), "Preencha o local", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return false;
        }else if(horaString.isEmpty()){
            Toast toast = Toast.makeText(OrigemFragment.this.getContext(), "Preencha a hora", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return false;
        }else if(kmInt == 0){
            Toast toast = Toast.makeText(OrigemFragment.this.getContext(), "Preencha a kilometragem", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return false;
        }


        return true;
    }

}
