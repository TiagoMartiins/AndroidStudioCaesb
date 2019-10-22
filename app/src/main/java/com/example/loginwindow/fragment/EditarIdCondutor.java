package com.example.loginwindow.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.loginwindow.R;
import com.example.loginwindow.activity.VeiculoActivity;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditarIdCondutor extends Fragment {

    TextInputEditText nome;
    Button btOk;
    ImageButton btFechar;
    String idCondutor;


    public EditarIdCondutor() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_editar_id_condutor, container, false);

        nome = (TextInputEditText) view.findViewById(R.id.idCondutor);


        //Referenciado botoes da tela para confirmar alteraçao e fechar fragment
        btOk = (Button) view.findViewById(R.id.btOk);
        btFechar = (ImageButton) view.findViewById(R.id.btCancelar);


        btFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent voltarTelaVeiculo = new Intent(EditarIdCondutor.this.getActivity(), VeiculoActivity.class);
                startActivity(voltarTelaVeiculo);
            }
        });

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verificarCampos()){
                    Intent voltarTelaVeiculo = new Intent(EditarIdCondutor.this.getActivity(), VeiculoActivity.class);
                    voltarTelaVeiculo.putExtra("idCondutor",idCondutor);
                    startActivity(voltarTelaVeiculo);
                }
            }
        });

        return view;
    }

    public boolean verificarCampos(){

        //Recuperando campos da interface Cadastro
        idCondutor = nome.getText().toString();

        if(idCondutor.isEmpty()){
            Toast toast = Toast.makeText(EditarIdCondutor.this.getContext(), "Preencha o nome", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return false;
        }

        return true;
    }

}
