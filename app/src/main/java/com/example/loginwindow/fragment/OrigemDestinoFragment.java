package com.example.loginwindow.fragment;


import android.annotation.SuppressLint;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginwindow.R;
import com.example.loginwindow.activity.VeiculoActivity;
import com.example.loginwindow.activity.VeiculoTesteRecycler;
import com.example.loginwindow.model.Destino;
import com.example.loginwindow.model.Origem;
import com.example.loginwindow.sqlite.DestinoSQLITE;
import com.example.loginwindow.sqlite.OrigemSQLITE;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 */
public class OrigemDestinoFragment extends Fragment implements View.OnClickListener {

    TextView origemTitulo,destinoTitulo;
    TextInputEditText localOrigem,horaOrigem,kmOrigem,localDestino,horaDestino,kmDestino;
    TextInputLayout inputLocalOrigem, inputHoraOrigem, inputKmOrigem, inputLocalDestino, inputHoraDestino, inputKmDestino;
    String localStringO,horaStringO,localStringD,horaStringD;
    int kmIntO,kmIntD,intO,intD;
    ImageButton btLocation, btFechar, btHorasOrigem,btHorasDestino;
    Button btOk;
    LinearLayout linearBotoes, linearOrigem,linearDestino;
    String origemOuDestino;
    boolean isAlterar = false;
    private Animation animation;
    OrigemSQLITE bancoOrigem;
    DestinoSQLITE bancoDestino;
    String data1, hora1;
    Origem origem;
    Destino destino;


    public OrigemDestinoFragment() {
        // Required empty public constructor
    }


    @SuppressLint("WrongViewCast")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_origem_destino_novo2, container, false);

        animation = new AlphaAnimation(1, 0.5f); // Altera alpha de visível a invisível
        animation.setDuration(1500); // duração - meio segundo
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE); // Repetir infinitamente
        animation.setRepeatMode(Animation.REVERSE); //Inverte a animação no final para que o botão vá desaparecendo

        //Titulos
        origemTitulo = view.findViewById(R.id.textOrigem);
        destinoTitulo = view.findViewById(R.id.textDestino);
        //Inputs Origem
        localOrigem = view.findViewById(R.id.localOrigem);
        horaOrigem = view.findViewById(R.id.horaOrigem);
        kmOrigem = view.findViewById(R.id.kmOrigem);
        //Inputs Destino
        localDestino = view.findViewById(R.id.localDestino);
        horaDestino = view.findViewById(R.id.horaDestino);
        kmDestino = view.findViewById(R.id.kmDestino);
        //Botoes
        btLocation = view.findViewById(R.id.btLocalizaçao);
        btFechar = view.findViewById(R.id.btCancelar);
        btHorasOrigem = (ImageButton) view.findViewById(R.id.btHorasOrigem);
        btHorasDestino = (ImageButton) view.findViewById(R.id.btHorasDestino);
        btOk = (Button) view.findViewById(R.id.btOk);
        //Linear dos Botoes
        linearBotoes = view.findViewById(R.id.linearBotoes);
        //LinearOrigem e LinearDestino
        linearOrigem = view.findViewById(R.id.linearOrigem);
        linearDestino = view.findViewById(R.id.linearDestino);
        //Input layout
        inputLocalOrigem = view.findViewById(R.id.textInputLocalOrigem);
        inputHoraOrigem = view.findViewById(R.id.textInputHoraOrigem);
        inputKmOrigem = view.findViewById(R.id.textInputKmOrigem);
        inputLocalDestino = view.findViewById(R.id.textInputLocalDestino);
        inputHoraDestino = view.findViewById(R.id.textInputHoraDestino);
        inputKmDestino = view.findViewById(R.id.textInputKmDestino);

        linearBotoes.startAnimation(animation);

        //Instanciando banco
        bancoOrigem = new OrigemSQLITE(this.getContext());
        bancoDestino = new DestinoSQLITE(this.getContext());



        Bundle data = getArguments();
        origemOuDestino = data.getString("data");
        data = getArguments();

        if(isAlterar) {
            intO = data.getInt("id_origem");
            localStringO = data.getString("local_origem");
            kmIntO = data.getInt("kms_origem");
            horaStringO = data.getString("horas_origem");
            intD = data.getInt("id_destino");
            localStringD = data.getString("local_destino");
            kmIntD = data.getInt("kms_destino");
            horaStringD = data.getString("horas_destino");
            System.out.println("----------------");
            System.out.println("----------------");
            System.out.println("----------------");
            System.out.println("----------------");
            System.out.println(intD);
            System.out.println("----------------");
            System.out.println("----------------");
            System.out.println("----------------");
            System.out.println("----------------");
            origem = new Origem(intO,localStringO,horaStringO,kmIntO);
            destino = new Destino(intD,localStringD,horaStringD,kmIntD);
            localOrigem.setText(localStringO);
            kmOrigem.setText(String.valueOf(kmIntO));
            localDestino.setText(localStringD);
            kmDestino.setText(String.valueOf(kmIntD));
        }

        atualizarHorario();

        if(horaStringO != null){
            horaOrigem.setText(horaStringO);
        }else{
            horaOrigem.setText(hora1);

        }
        if(horaStringD != null){
            horaDestino.setText(horaStringD);
        }else{
            horaDestino.setText(hora1);
        }

        btLocation.setOnClickListener(this);
        btFechar.setOnClickListener(this);
        btHorasOrigem.setOnClickListener(this);
        btHorasDestino.setOnClickListener(this);
        btOk.setOnClickListener(this);
        linearOrigem.setOnClickListener(this);
        linearDestino.setOnClickListener(this);

        inputLocalOrigem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animarLinearOrigem();
            }
        });
        inputHoraOrigem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animarLinearOrigem();
            }
        });
        inputKmOrigem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animarLinearOrigem();
            }
        });
        inputLocalDestino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animarLinearDestino();
            }
        });
        inputHoraDestino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animarLinearDestino();
            }
        });
        inputKmDestino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                animarLinearDestino();
            }
        });



        return view;
    }




    public void atualizarHorario(){
        Date agora = new Date();
        SimpleDateFormat formata = new SimpleDateFormat("dd/MM/yyyy");
        data1 = formata.format(agora);
        formata = new SimpleDateFormat("hh:mm");
        formata.setTimeZone(TimeZone.getTimeZone("America/Sao_Paulo"));
        hora1 = formata.format(agora);
    }

    public void animarLinearOrigem(){
        linearOrigem.startAnimation(animation);
        linearDestino.clearAnimation();
    }

    public void animarLinearDestino(){
        linearDestino.startAnimation(animation);
        linearOrigem.clearAnimation();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btOk) {

            adicionarOuAlterar();

        }
        if (view.getId() == R.id.btCancelar) {
            Intent voltarTelaVeiculo = new Intent(OrigemDestinoFragment.this.getActivity(), VeiculoTesteRecycler.class);
            startActivity(voltarTelaVeiculo);

        }
        if(view.getId() == R.id.btHorasOrigem){
            atualizarHorario();
            horaOrigem.setText(hora1);
        }
        if(view.getId() == R.id.btHorasDestino){
            atualizarHorario();
            horaDestino.setText(hora1);
        }
    }

    public void adicionarOuAlterar(){
            if (verificarCamposOrigem()) {
                try {
                    if(isAlterar){
                        bancoOrigem.updateOrigem(origem);
                        Toast toast = Toast.makeText(OrigemDestinoFragment.this.getContext(), "Alterado Origem com Sucesso", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }else{
                        bancoOrigem.addOrigem(localStringO, horaStringO, kmIntO);
                        Toast toast = Toast.makeText(OrigemDestinoFragment.this.getContext(), "Inserido Origem com Sucesso", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent voltarTelaVeiculo = new Intent(OrigemDestinoFragment.this.getActivity(), VeiculoTesteRecycler.class);
                startActivity(voltarTelaVeiculo);
            }
            if(verificarCamposDestino()){
                try {
                    if(isAlterar){
                        if(destino.getId() == 0){
                            bancoDestino.addDestino(localStringD,horaStringD,kmIntD);
                            Toast toast = Toast.makeText(OrigemDestinoFragment.this.getContext(), "Inserido Destino com Sucesso", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }else{
                            bancoDestino.updateDestino(destino);
                            Toast toast = Toast.makeText(OrigemDestinoFragment.this.getContext(), "Alterado Destino com Sucesso", Toast.LENGTH_SHORT);
                            toast.setGravity(Gravity.CENTER, 0, 0);
                            toast.show();
                        }

                    }else{
                        bancoDestino.addDestino(localStringD, horaStringD, kmIntD);
                        Toast toast = Toast.makeText(OrigemDestinoFragment.this.getContext(), "Inserido Destino com Sucesso", Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER, 0, 0);
                        toast.show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                Intent voltarTelaVeiculo = new Intent(OrigemDestinoFragment.this.getActivity(), VeiculoTesteRecycler.class);
                startActivity(voltarTelaVeiculo);
            }


    }


    public boolean verificarCamposOrigem(){

        //Recuperando campos da interface Cadastro
        localStringO = localOrigem.getText().toString().toUpperCase();
        horaStringO = horaOrigem.getText().toString();
        try {
            if(kmIntO != 0)
            kmIntO = Integer.parseInt(kmOrigem.getText().toString());
        }catch (NumberFormatException e){
            Toast toast = Toast.makeText(OrigemDestinoFragment.this.getContext(), "Erro de formatação, tente novamente", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }

        if(localStringO.isEmpty()){
            Toast toast = Toast.makeText(OrigemDestinoFragment.this.getContext(), "Preencha o local", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return false;
        }else if(horaStringO.isEmpty()){
            Toast toast = Toast.makeText(OrigemDestinoFragment.this.getContext(), "Preencha a hora", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return false;
        }else if(kmIntO == 0){
            Toast toast = Toast.makeText(OrigemDestinoFragment.this.getContext(), "Preencha a kilometragem", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return false;
        }


        return true;
    }

    public boolean verificarCamposDestino(){

        //Recuperando campos da interface Cadastro
        localStringD = localDestino.getText().toString().toUpperCase();
        horaStringD = horaDestino.getText().toString();
        try {
            if(kmIntO != 0)
            kmIntD = Integer.parseInt(kmDestino.getText().toString());
        }catch (NumberFormatException e){
            Toast toast = Toast.makeText(OrigemDestinoFragment.this.getContext(), "Erro de formatação, tente novamente", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
        }

        if(localStringD.isEmpty()){
            Toast toast = Toast.makeText(OrigemDestinoFragment.this.getContext(), "Preencha o local", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return false;
        }else if(horaStringD.isEmpty()){
            Toast toast = Toast.makeText(OrigemDestinoFragment.this.getContext(), "Preencha a hora", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return false;
        }else if(kmIntD == 0){
            Toast toast = Toast.makeText(OrigemDestinoFragment.this.getContext(), "Preencha a kilometragem", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return false;
        }


        return true;
    }

}
