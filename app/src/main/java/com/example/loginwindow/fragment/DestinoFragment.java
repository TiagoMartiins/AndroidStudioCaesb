package com.example.loginwindow.fragment;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.loginwindow.R;
import com.example.loginwindow.activity.VeiculoActivity;
import com.example.loginwindow.gps.GpsTracker;
import com.example.loginwindow.sqlite.DestinoSQLITE;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 */
public class DestinoFragment extends Fragment {
    TextInputEditText horas,km,local;
    Button btOk;
    ImageButton btLocation, btFechar;
    DestinoSQLITE banco;
    String localString,horaString;
    int kmInt;


    public DestinoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_destino, container, false);
        banco = new DestinoSQLITE(this.getContext());

        horas = (TextInputEditText) view.findViewById(R.id.hora);
        km = (TextInputEditText) view.findViewById(R.id.km);
        local = (TextInputEditText) view.findViewById(R.id.local);

        btOk = (Button) view.findViewById(R.id.btOk);
        btLocation = (ImageButton) view.findViewById(R.id.btLocalizaçao);
        btFechar = (ImageButton) view.findViewById(R.id.btCancelar);



        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GpsTracker gps = new GpsTracker(DestinoFragment.this.getContext());
                if(gps.canGetLocation()){
                    local.setText(String.valueOf(gps.getLatitude()));
                }
                System.out.println("entrou");
            }
        });

        btFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent voltarTelaVeiculo = new Intent(DestinoFragment.this.getActivity(), VeiculoActivity.class);
                startActivity(voltarTelaVeiculo);
            }
        });

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verificarCampos()){

                    try {
                        banco.addDestino(localString, horaString, kmInt);
                        System.out.println(localString);
                        System.out.println(horaString);
                        System.out.println(kmInt);
                        Toast toast = Toast.makeText(DestinoFragment.this.getContext(), "Inserido com Sucesso", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER,0,0);
                        toast.show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }

                    Intent voltarTelaVeiculo = new Intent(DestinoFragment.this.getActivity(),VeiculoActivity.class);
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
        localString = local.getText().toString().trim().toUpperCase();
        horaString = horas.getText().toString().trim();
        try {
            kmInt = Integer.parseInt(km.getText().toString());
        }catch (NumberFormatException e){
            Toast toast = Toast.makeText(DestinoFragment.this.getContext(), "Erro de formatação, tente novamente", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }

        if(localString.isEmpty()){
            Toast toast = Toast.makeText(DestinoFragment.this.getContext(), "Preencha o local", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return false;
        }else if(horaString.isEmpty()){
            Toast toast = Toast.makeText(DestinoFragment.this.getContext(), "Preencha a hora", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return false;
        }else if(kmInt == 0){
            Toast toast = Toast.makeText(DestinoFragment.this.getContext(), "Preencha a kilometragem", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return false;
        }


        return true;
    }


}
