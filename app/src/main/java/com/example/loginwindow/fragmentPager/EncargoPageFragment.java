package com.example.loginwindow.fragmentPager;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.loginwindow.R;
import com.example.loginwindow.model.Encargo;
import com.example.loginwindow.model.OrdemServico;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class EncargoPageFragment extends Fragment {

    /**
     * Created OS list
     *
     */
    private List<Encargo> listaEncargo = new ArrayList<>();

    public TextView titulo,descricao,data;
    int position;


    public EncargoPageFragment(int position) {
        this.position = position;
        this.criarEncargo();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_screen_slide_page, container, false);

        Encargo encargo = listaEncargo.get(position);

        view.setBackground(getResources().getDrawable(R.color.transparenteVerde));

        titulo = view.findViewById(R.id.titulo);
        titulo.setText(encargo.getTitulo());
        descricao = view.findViewById(R.id.descricao);
        descricao.setText(encargo.getDescricao());
        data = view.findViewById(R.id.data);
        String dataString = String.valueOf(encargo.getData());
        data.setText(dataString);

        return view;
    }

    public void criarEncargo(){

        Encargo os =  new Encargo("Encargo 1","ENCARGO 1 SENDO EXECUTADO", Calendar.getInstance().getTime());
        listaEncargo.add(os);

        Encargo os2 =  new Encargo("Encargo 2","ENCARGO 2 SENDO EXECUTADO",Calendar.getInstance().getTime());
        listaEncargo.add(os2);

        Encargo os3 =  new Encargo("Encargo 3","ENCARGO 3 SENDO EXECUTADO ",Calendar.getInstance().getTime());
        listaEncargo.add(os3);

        Encargo os4 =  new Encargo("Encargo 4","ENCARGO 4 SENDO EXECUTADO ",Calendar.getInstance().getTime());
        listaEncargo.add(os4);

        Encargo os5 =  new Encargo("Encargo 5","ENCARGO 5 SENDO EXECUTADO ",Calendar.getInstance().getTime());
        listaEncargo.add(os5);

        Encargo os6 =  new Encargo("Encargo 6","ENCARGO 6 SENDO EXECUTADO ",Calendar.getInstance().getTime());
        listaEncargo.add(os6);

        Encargo os7 =  new Encargo("Encargo 7","ENCARGO 7 SENDO EXECUTADO",Calendar.getInstance().getTime());
        listaEncargo.add(os7);

        Encargo os8 =  new Encargo("Encargo 8","ENCARGO 8 SENDO EXECUTADO",Calendar.getInstance().getTime());
        listaEncargo.add(os8);
    }


}
