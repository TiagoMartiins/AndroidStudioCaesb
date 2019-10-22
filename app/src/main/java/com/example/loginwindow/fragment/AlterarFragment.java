package com.example.loginwindow.fragment;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginwindow.R;
import com.example.loginwindow.activity.VeiculoActivity;
import com.example.loginwindow.gps.GpsTracker;
import com.example.loginwindow.model.Destino;
import com.example.loginwindow.model.Origem;
import com.example.loginwindow.sqlite.DestinoSQLITE;
import com.example.loginwindow.sqlite.OrigemSQLITE;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlterarFragment extends Fragment {

    TextInputEditText horas,km,local;
    Button btOk;
    ImageButton btLocation, btFechar;
    OrigemSQLITE bancoOrigem;
    DestinoSQLITE bancoDestino;
    String localString,horaString;
    int kmInt;
    int id;
    TextView titulo;


    public AlterarFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_alterar, container, false);
        try{
            bancoOrigem = new OrigemSQLITE(this.getContext());
            bancoDestino = new DestinoSQLITE(this.getContext());
        }catch (Exception e){
            e.printStackTrace();
        }

        horas = (TextInputEditText) view.findViewById(R.id.hora);
        km = (TextInputEditText) view.findViewById(R.id.km);
        local = (TextInputEditText) view.findViewById(R.id.local);

        btOk = (Button) view.findViewById(R.id.btOk);
        btLocation = (ImageButton) view.findViewById(R.id.btLocalizaçao);
        btFechar = (ImageButton) view.findViewById(R.id.btCancelar);
        titulo = (TextView) view.findViewById(R.id.titulo);

        Bundle data = getArguments();
        final String origemOuDestino = data.getString("data");
        data = getArguments();
        if(origemOuDestino == "origem"){
            titulo.setText("Origem");
        } else {
            titulo.setText("Destino");
            view.setBackgroundResource(R.drawable.borde_destino);
        }
        id = data.getInt("id");
        localString = data.getString("local");
        kmInt = data.getInt("kms");

        local.setText(localString);
        km.setText(String.valueOf(kmInt));



        btLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                GpsTracker gps = new GpsTracker(AlterarFragment.this.getContext());
                if(gps.canGetLocation()){
                    local.setText(String.valueOf(gps.getLatitude()));
                }
                System.out.println("entrou");
            }
        });

        btFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent voltarTelaVeiculo = new Intent(AlterarFragment.this.getActivity(), VeiculoActivity.class);
                startActivity(voltarTelaVeiculo);
            }
        });

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verificarCampos()){

                    try {
                        AlertDialog alertDialog = new AlertDialog.Builder(AlterarFragment.this.getContext()).create();
                        alertDialog.setTitle("Alteração");
                        alertDialog.setMessage("Tem certeza que deseja alterar?");
                        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Sim", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if(origemOuDestino == "origem"){
                                    Origem origem = new Origem(id,localString,horaString,kmInt);
                                    bancoOrigem.updateOrigem(origem);
                                }else if(origemOuDestino == "destino"){
                                    Destino destino = new Destino(id,localString,horaString,kmInt);
                                    bancoDestino.updateDestino(destino);
                                }
                                Toast toast = Toast.makeText(AlterarFragment.this.getContext(), "Alterado Com Sucesso", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER,0,0);
                                toast.show();
                                Intent voltarTelaVeiculo = new Intent(AlterarFragment.this.getActivity(),VeiculoActivity.class);
                                startActivity(voltarTelaVeiculo);
                            }
                        });
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "Não", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        });

                        alertDialog.show();
                    }catch (Exception e){
                        e.printStackTrace();
                    }
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
            Toast toast = Toast.makeText(AlterarFragment.this.getContext(), "Erro de formatação, tente novamente", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }

        if(localString.isEmpty()){
            Toast toast = Toast.makeText(AlterarFragment.this.getContext(), "Preencha o local", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return false;
        }else if(horaString.isEmpty()){
            Toast toast = Toast.makeText(AlterarFragment.this.getContext(), "Preencha a hora", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return false;
        }else if(kmInt == 0){
            Toast toast = Toast.makeText(AlterarFragment.this.getContext(), "Preencha a kilometragem", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return false;
        }


        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

        Bundle data = getArguments();
        final String origemOuDestino = data.getString("data");
        data = getArguments();
        id = data.getInt("id");
        localString = data.getString("local");
        kmInt = data.getInt("kms");

        local.setText(localString);
        km.setText(String.valueOf(kmInt));
    }
}