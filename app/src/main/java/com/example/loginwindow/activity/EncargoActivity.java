package com.example.loginwindow.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginwindow.R;
import com.example.loginwindow.adapter.AdapterEncargo;
import com.example.loginwindow.model.Encargo;
import com.example.loginwindow.model.Encargo;
import com.example.loginwindow.recyclerListener.RecyclerItemClickListener;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class EncargoActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<Encargo> listaEncargo = new ArrayList<>();
    private FloatingActionButton fab;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_encargo_recyclerview);

        recyclerView = findViewById(R.id.recyclerView);

        animation = new AlphaAnimation(1, 0.5f); // Altera alpha de visível a invisível
        animation.setDuration(1000); // duração - meio segundo
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE); // Repetir infinitamente
        animation.setRepeatMode(Animation.REVERSE); //Inverte a animação no final para que o botão vá desaparecendo

        //Criar Os
        this.criarEncargo();

        //Action Button
        fab = findViewById(R.id.fabItem);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent voltarTelaInicial = new Intent(EncargoActivity.this,MainActivityNova.class);
                startActivity(voltarTelaInicial);
            }
        });
        fab.startAnimation(animation);

        //Configurar Adapter

        AdapterEncargo adapter = new AdapterEncargo(listaEncargo);

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
                                Intent irParaEncargoEditado = new Intent(EncargoActivity.this,EncargoPagerActivity.class);
                                irParaEncargoEditado.putExtra("position",position);
                                startActivity(irParaEncargoEditado);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }));
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

    @Override
    public void onBackPressed() {
        Intent telaMainPrincipal = new Intent(EncargoActivity.this,MainActivityNova.class);
        startActivity(telaMainPrincipal);
    }


}
