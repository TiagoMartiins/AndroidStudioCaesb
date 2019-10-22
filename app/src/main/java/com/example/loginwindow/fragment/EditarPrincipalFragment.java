package com.example.loginwindow.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.loginwindow.R;
import com.example.loginwindow.activity.MainActivity;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 */
public class EditarPrincipalFragment extends Fragment {

    TextInputEditText nome;
    Button btOk;
    ImageButton btFechar;
    String principal;
    TextView alteraPrincipal;


    public EditarPrincipalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_editar_os_principal, container, false);

        nome = (TextInputEditText) view.findViewById(R.id.principal);


        //Referenciado botoes da tela para confirmar alteraçao e fechar fragment
        btOk = (Button) view.findViewById(R.id.btOk);
        btFechar = (ImageButton) view.findViewById(R.id.btCancelar);

        //Referenciando TextView para mudar nome OS ou DATA
        alteraPrincipal = view.findViewById(R.id.alterarPrincipal);


        Bundle data = getArguments();
        final String OSouDATA = data.getString("data");
        if(OSouDATA == "os"){
            alteraPrincipal.setText("Alterar OS");
            nome.setHint("OS");
            nome.setMaxEms(6);
            nome.setFilters(new InputFilter[] {new InputFilter.LengthFilter(6)});
            nome.setInputType(InputType.TYPE_CLASS_NUMBER);
        } else {
            alteraPrincipal.setText("Alterar DATA");
            nome.setHint("DATA");
            nome.setFilters(new InputFilter[] {new InputFilter.LengthFilter(10)});
            nome.setInputType(InputType.TYPE_CLASS_DATETIME);
        }


        btFechar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent voltarTelaOs = new Intent(EditarPrincipalFragment.this.getActivity(), MainActivity.class);
                startActivity(voltarTelaOs);
            }
        });

        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verificarCampos()){
                    Intent voltarTelaOs = new Intent(EditarPrincipalFragment.this.getActivity(), MainActivity.class);
                    if(OSouDATA == "os"){
                        voltarTelaOs.putExtra("osPrincipal",principal);
                    }else{
                        voltarTelaOs.putExtra("dataPrincipal",principal);
                    }
                    startActivity(voltarTelaOs);
                }
            }
        });

        return view;
    }

    public boolean verificarCampos(){

        //Recuperando campos da interface Cadastro
        principal = nome.getText().toString();

        if(principal.isEmpty()){
            Toast toast = Toast.makeText(EditarPrincipalFragment.this.getContext(), "Preencha o nome", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER,0,0);
            toast.show();
            return false;
        }

        return true;
    }


}
