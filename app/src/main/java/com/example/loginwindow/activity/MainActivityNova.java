package com.example.loginwindow.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import com.example.loginwindow.R;
import com.example.loginwindow.fragment.AlterarFragment;
import com.example.loginwindow.model.Destino;
import com.example.loginwindow.model.Origem;
import com.example.loginwindow.model.Usuario;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivityNova extends AppCompatActivity implements View.OnClickListener {

    TextView textNomeUsuario,textEquipeUsuario,textData;
    CardView cardFolha,cardOs,cardEncargo,cardMais;
    Usuario usuario;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_principal);


        textNomeUsuario = findViewById(R.id.textNomePrincipal);
        textEquipeUsuario = findViewById(R.id.textEquipePrincipal);
        //Referenciando data e hora atual,  Obs : ver como atualizar hora de segundo em segundo
        textData = findViewById(R.id.textDataPrincipal);

        //Referenciado cardOs e cardFolha
        cardOs = findViewById(R.id.cardViewOs);
        cardFolha = findViewById(R.id.cardViewFolha);
        cardEncargo = findViewById(R.id.cardViewEncargo);
        cardMais = findViewById(R.id.cardViewMais);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            usuario = (Usuario) extras.get("usuario");
        }

        exibirUsuarioLogado(usuario);


        cardOs.setOnClickListener(this);
        cardFolha.setOnClickListener(this);
        cardEncargo.setOnClickListener(this);
        cardMais.setOnClickListener(this);

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){

            case R.id.cardViewFolha:
                Intent telaFolha = new Intent(MainActivityNova.this,MainActivity.class);
                telaFolha.putExtra("usuario",usuario);
                startActivity(telaFolha);
                break;
            case R.id.cardViewOs:
                Intent telaOs = new Intent(MainActivityNova.this,OsActivity.class);
                startActivity(telaOs);
                break;
            case R.id.cardViewEncargo:
                Intent telaEncargo = new Intent(MainActivityNova.this,EncargoActivity.class);
                startActivity(telaEncargo);
            case R.id.cardViewMais:
                Intent telaVeiculoListaNova = new Intent(MainActivityNova.this,EncargoActivity.class);
                startActivity(telaVeiculoListaNova);

        }
    }

    //Pegando o objeto Usuario e setando de acordo com seus atributos

    public void exibirUsuarioLogado(Usuario usuario){
        if(usuario != null){
            System.out.println(usuario);
            String usuarioFormatado = usuario.getNome().substring(0,1).toUpperCase() + usuario.getNome().substring(1);
            textNomeUsuario.setText(usuarioFormatado);
            String equipeFormatada;
            if(usuario.getEquipe().length() > 2){
                equipeFormatada = usuario.getEquipe().substring(0,1).toUpperCase() + usuario.getEquipe().substring(1);
                textEquipeUsuario.setText(equipeFormatada);
            }else{
                textEquipeUsuario.setText(usuario.getEquipe());
            }

            System.out.println(usuario.getEquipe());


            //Setando data de acordo com DateFormat
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            System.out.println(dateFormat.format(date));
            String stringData = dateFormat.format(date);
            textData.setText(stringData);

        }
    }

    @Override
    public void onBackPressed() {
        try {
            AlertDialog alertDialog = new AlertDialog.Builder(MainActivityNova.this).create();
            alertDialog.setTitle("Log out");
            alertDialog.setMessage("Tem certeza que deseja sair?");
            alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Sim", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    deslogarUsuario();
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


    public void deslogarUsuario(){

        usuario = null;

        Intent telaInicial = new Intent(MainActivityNova.this, LoginActivity.class);
        startActivity(telaInicial);
        Toast toast = Toast.makeText(MainActivityNova.this, "Deslogado com Sucesso", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0,0);
        toast.show();

    }
}
