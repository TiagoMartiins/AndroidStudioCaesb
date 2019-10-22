package com.example.loginwindow.fragmentPager;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.loginwindow.R;
import com.example.loginwindow.model.OrdemServico;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ScreenSlidePageFragment extends Fragment {

    /**
     * Created OS list
     *
     */
    private List<OrdemServico> listaOs = new ArrayList<>();

    public TextView titulo,descricao,data;
    int position;


    public ScreenSlidePageFragment(int position) {
        this.position = position;
        this.criarOs();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_screen_slide_page, container, false);

        OrdemServico os = listaOs.get(position);

        titulo = view.findViewById(R.id.titulo);
        titulo.setText(os.getTitulo());
        descricao = view.findViewById(R.id.descricao);
        descricao.setText(os.getDescricao());
        data = view.findViewById(R.id.data);
        String dataString = String.valueOf(os.getData());
        data.setText(dataString);

        return view;
    }

    public void criarOs() {

        OrdemServico os = new OrdemServico("Os 01930", "Os sendo executada", Calendar.getInstance().getTime());
        listaOs.add(os);

        OrdemServico os2 = new OrdemServico("Os 029183", "Os nova atualizada", Calendar.getInstance().getTime());
        listaOs.add(os2);

        OrdemServico os3 = new OrdemServico("Os 1239821", "teste 2", Calendar.getInstance().getTime());
        listaOs.add(os3);

        OrdemServico os4 = new OrdemServico("Os 19373", "teste 3", Calendar.getInstance().getTime());
        listaOs.add(os4);

        OrdemServico os5 = new OrdemServico("Os 192", "Rodando Os..", Calendar.getInstance().getTime());
        listaOs.add(os5);

        OrdemServico os6 = new OrdemServico("Os 12", "Atualizando Os 12", Calendar.getInstance().getTime());
        listaOs.add(os6);

        OrdemServico os7 = new OrdemServico("Os 13", "Aasodkaodasmda", Calendar.getInstance().getTime());
        listaOs.add(os7);

        OrdemServico os8 = new OrdemServico("Os 14", "asdkladasdasdamçdas", Calendar.getInstance().getTime());
        listaOs.add(os8);
    }


}
