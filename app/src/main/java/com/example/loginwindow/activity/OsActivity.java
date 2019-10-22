package com.example.loginwindow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.system.Os;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginwindow.R;
import com.example.loginwindow.adapter.AdapterOs;
import com.example.loginwindow.model.OrdemServico;
import com.example.loginwindow.recyclerListener.RecyclerItemClickListener;
import com.github.clans.fab.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class OsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<OrdemServico> listaOs = new ArrayList<>();
    private FloatingActionButton fab;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_os_recyclerview);

        recyclerView = findViewById(R.id.recyclerView);

        animation = new AlphaAnimation(1, 0.5f); // Altera alpha de visível a invisível
        animation.setDuration(1000); // duração - meio segundo
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE); // Repetir infinitamente
        animation.setRepeatMode(Animation.REVERSE); //Inverte a animação no final para que o botão vá desaparecendo

        //Criar Os
        this.criarOs();

        //Action Button
        fab = findViewById(R.id.fabItem);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent voltarTelaInicial = new Intent(OsActivity.this,MainActivityNova.class);
                startActivity(voltarTelaInicial);
            }
        });
        fab.startAnimation(animation);

        //Configurar Adapter

        AdapterOs adapter = new AdapterOs(listaOs);

        //Configurar RecyclerView
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter( adapter );

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(),
                 recyclerView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Intent irParaOsEditada = new Intent(OsActivity.this,ScreenSlidePagerActivity.class);
                        irParaOsEditada.putExtra("position",position);
                        startActivity(irParaOsEditada);
                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }));
    }

    public void criarOs(){

        OrdemServico os =  new OrdemServico("Os 01930","Os sendo executada",Calendar.getInstance().getTime());
        listaOs.add(os);

        OrdemServico os2 =  new OrdemServico("Os 029183","Os nova atualizada",Calendar.getInstance().getTime());
        listaOs.add(os2);

        OrdemServico os3 =  new OrdemServico("Os 1239821","teste 2",Calendar.getInstance().getTime());
        listaOs.add(os3);

        OrdemServico os4 =  new OrdemServico("Os 19373","teste 3",Calendar.getInstance().getTime());
        listaOs.add(os4);

        OrdemServico os5 =  new OrdemServico("Os 192","Rodando Os..",Calendar.getInstance().getTime());
        listaOs.add(os5);

        OrdemServico os6 =  new OrdemServico("Os 12","Atualizando Os 12",Calendar.getInstance().getTime());
        listaOs.add(os6);

        OrdemServico os7 =  new OrdemServico("Os 13","Aasodkaodasmda",Calendar.getInstance().getTime());
        listaOs.add(os7);

        OrdemServico os8 =  new OrdemServico("Os 14","asdkladasdasdamçdas",Calendar.getInstance().getTime());
        listaOs.add(os8);
    }

    @Override
    public void onBackPressed() {
        Intent telaMainPrincipal = new Intent(OsActivity.this,MainActivityNova.class);
        startActivity(telaMainPrincipal);
    }


}
