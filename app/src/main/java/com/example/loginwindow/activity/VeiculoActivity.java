package com.example.loginwindow.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginwindow.dragDrop.DragDropOnDragListener;
import com.example.loginwindow.dragDrop.DragDropOnTouchListener;
import com.example.loginwindow.R;
import com.example.loginwindow.adapter.AdapterDestino;
import com.example.loginwindow.adapter.AdapterOrigem;
import com.example.loginwindow.fragment.AlterarFragment;
import com.example.loginwindow.fragment.DestinoFragment;
import com.example.loginwindow.fragment.EditarIdCondutor;
import com.example.loginwindow.fragment.EditarIdVeiculoFragment;
import com.example.loginwindow.fragment.OrigemDestinoFragment;
import com.example.loginwindow.fragment.OrigemFragment;
import com.example.loginwindow.model.Destino;
import com.example.loginwindow.model.Origem;
import com.example.loginwindow.model.Usuario;
import com.example.loginwindow.recyclerListener.RecyclerItemClickListener;
import com.example.loginwindow.sqlite.DestinoSQLITE;
import com.example.loginwindow.sqlite.OrigemSQLITE;
import com.github.clans.fab.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class VeiculoActivity extends AppCompatActivity implements View.OnClickListener {

    //Criando atributos para cada widget, tela ou list, Criando instancia do banco
    TextView idViatura,idCondutor;
    TextView nomeViatura, nomeCondutor;
    RecyclerView listaOrigemRc, listaDestinoRc;
    Button origemLink,destinoLink;
    List<Destino> listaDestino ;
    List<Origem> listaOrigem ;
    private LinearLayout mLinearLayout,fragment;
    private ConstraintLayout constraintLayout;
    OrigemFragment origemFragment = new OrigemFragment();
    DestinoFragment destinoFragment = new DestinoFragment();
    AlterarFragment alterarFragment = new AlterarFragment();
    OrigemDestinoFragment origemDestinoFragment = new OrigemDestinoFragment();
    OrigemSQLITE bancoOrigem;
    DestinoSQLITE bancoDestino;
    TableLayout table;
    private Animation animation;
    FloatingActionButton fab;
    FrameLayout rootFrame;
    Usuario usuario;
    EditarIdVeiculoFragment editarIdVeiculoFragment = new EditarIdVeiculoFragment();
    EditarIdCondutor editarIdCondutorFragment = new EditarIdCondutor();
    String idVeiculoString,idCondutorString;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.novo);

        animation = new AlphaAnimation(1, 0.5f); // Altera alpha de visível a invisível
        animation.setDuration(1000); // duração - meio segundo
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE); // Repetir infinitamente
        animation.setRepeatMode(Animation.REVERSE); //Inverte a animação no final para que o botão vá desaparecendo

        //Referenciando todo texto ou botão da tela

        idViatura = (TextView) findViewById(R.id.idViatura);
        idCondutor = (TextView) findViewById(R.id.idCondutor);
        nomeViatura = (TextView) findViewById(R.id.nomeViatura);
        nomeCondutor = (TextView) findViewById(R.id.nomeCondutor);
        listaOrigemRc = (RecyclerView) findViewById(R.id.idOrigem);
        listaDestinoRc = (RecyclerView) findViewById(R.id.idDestino);
//        mLinearLayout = (LinearLayout) findViewById(R.id.rl);
        origemLink = (Button) findViewById(R.id.origemLink);
        destinoLink = (Button) findViewById(R.id.destinoLink);
        fragment = (LinearLayout) findViewById(R.id.frameFragment);
        constraintLayout = (ConstraintLayout) findViewById(R.id.constraintLayout);
        table = (TableLayout) findViewById(R.id.tableLayout);
        rootFrame = (FrameLayout) findViewById(R.id.root);
        fab = (FloatingActionButton) findViewById(R.id.fabItem);

        fab.startAnimation(animation);

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            usuario = (Usuario) extras.get("usuario");
            idVeiculoString = (String) extras.get("idVeiculo");
            idCondutorString = (String) extras.get("idCondutor");
        }

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent voltarTelaInicial = new Intent(VeiculoActivity.this,MainActivity.class);
                voltarTelaInicial.putExtra("usuario",usuario);
                startActivity(voltarTelaInicial);
            }
        });

        origemLink.setOnClickListener(this);
        destinoLink.setOnClickListener(this);
        constraintLayout.setOnClickListener(this);
        rootFrame.setOnClickListener(this);

        if(idVeiculoString != null ){
            idViatura.setText(idVeiculoString);
        }else {
            idViatura.setText("33-31");
        }
        idViatura.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirFragmentoIdVeiculo();
            }
        });
        if(idCondutorString != null ){
            idCondutor.setText(idCondutorString);
        }else{
            idCondutor.setText("381209");
        }
        idCondutor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirFragmentoIdCondutor();
            }
        });


        nomeViatura.setText("GM Onix PBL-2475/DF");
        nomeCondutor.setText("Eduardo Pereira Tassinari");

        //Instanciando o banco
        try {
            bancoOrigem = new OrigemSQLITE(this);
            bancoDestino = new DestinoSQLITE(this);
            if(listaOrigem == null){
                listaOrigem = new ArrayList<>();
            }
            if(listaDestino == null){
                listaDestino = new ArrayList<>();
            }
            bancoOrigem.reloadEmployeesFromDatabase(listaOrigem);
            bancoDestino.reloadEmployeesFromDatabase(listaDestino);
            imprimirNaListaOrigem(listaOrigem);
            imprimirNaListaDestino(listaDestino);

        }catch (Exception e){
            e.printStackTrace();
        }


        setupUI(findViewById(R.id.constraintLayout));


        listaOrigemRc.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        listaOrigemRc,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                view.setPressed(true);
                                abrirFragmentoAlterar(position,"origem");
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                Origem origem = listaOrigem.get(position);
                                try{
                                    bancoOrigem.delete(origem);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                bancoOrigem.reloadEmployeesFromDatabase(listaOrigem);
                                Toast toast = Toast.makeText(VeiculoActivity.this, "Item Removido", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                                Intent voltarTelaVeiculo = new Intent(VeiculoActivity.this,VeiculoActivity.class);
                                startActivity(voltarTelaVeiculo);
                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );

        listaDestinoRc.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        listaDestinoRc,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                abrirFragmentoAlterar(position,"destino");
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {
                                Destino destino = listaDestino.get(position);
                                try{
                                    bancoDestino.delete(destino);
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                                bancoDestino.reloadEmployeesFromDatabase(listaDestino);
                                Toast toast = Toast.makeText(VeiculoActivity.this, "Item Removido", Toast.LENGTH_SHORT);
                                toast.setGravity(Gravity.CENTER, 0, 0);
                                toast.show();

                                Intent voltarTelaVeiculo = new Intent(VeiculoActivity.this,VeiculoActivity.class);
                                startActivity(voltarTelaVeiculo);

                            }

                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                            }
                        }
                )
        );



    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.destinoLink) {
            if (listaOrigem.size() <= listaDestino.size()) {
                Toast toast = Toast.makeText(this, "Você precisa de uma Origem", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                abrirOuFecharFragmentDestino();
            }
        }
        if (view.getId() == R.id.constraintLayout || view.getId() == R.id.root) {

        }
        if (view.getId() == R.id.origemLink) {
            if (listaOrigem.size() != listaDestino.size()) {
                Toast toast = Toast.makeText(this, "Você precisa definir um destino", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            } else {
                Bundle data = new Bundle();
                data.putString("data","origem");
               abrirOuFecharFragmentOrigem2(data);
            }
        }

    }

    public void imprimirNaListaOrigem(List<Origem> origemList){


        //Iniciando o adaptador da lista e passando essa lista por parametro
        AdapterOrigem adapter = new AdapterOrigem(origemList);

        //Configurando o adaptador, setando o recyclerview para mostrar o resultado
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listaOrigemRc.setLayoutManager(layoutManager);
        listaOrigemRc.setHasFixedSize(true);
        listaOrigemRc.setAdapter( adapter );
    }

    public void imprimirNaListaDestino(List<Destino> destinoList){


        //Iniciando o adaptador da lista e passando essa lista por parametro
        AdapterDestino adapter = new AdapterDestino(destinoList);

        //Configurando o adaptador, setando o recyclerview para mostrar o resultado
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        listaDestinoRc.setLayoutManager(layoutManager);
        listaDestinoRc.setHasFixedSize(true);
        listaDestinoRc.setAdapter( adapter );
    }

    public void abrirOuFecharFragmentOrigem(){
        if (origemFragment.isVisible()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(origemFragment);
            constraintLayout.setBackground(getResources().getDrawable(R.color.branco));
            fragment.setVisibility(View.GONE);
            Log.d("Passou", "passou");
            transaction.commit();
        }else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            fragment.setVisibility(View.VISIBLE);
            constraintLayout.setBackground(getResources().getDrawable(R.color.cinza));
            constraintLayout.getBackground().setAlpha(240);
            transaction.replace(R.id.frameFragment, origemFragment);
            Log.d("nao passou", "nao passou");
            transaction.commit();
        }
    }

    public void abrirOuFecharFragmentOrigem2(Bundle goTo){
        if (origemDestinoFragment.isVisible()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(origemDestinoFragment);
            constraintLayout.setBackground(getResources().getDrawable(R.color.branco));
            fragment.setVisibility(View.GONE);
            Log.d("Passou", "passou");
            transaction.commit();
        }else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            fragment.setVisibility(View.VISIBLE);
            constraintLayout.setBackground(getResources().getDrawable(R.color.cinza));
            constraintLayout.getBackground().setAlpha(240);
            origemDestinoFragment.setArguments(goTo);
            transaction.replace(R.id.frameFragment, origemDestinoFragment);
            Log.d("nao passou", "nao passou");
            transaction.commit();
        }
    }

    public void abrirOuFecharFragmentDestino(){
        if (destinoFragment.isVisible()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(destinoFragment);
            constraintLayout.setBackground(getResources().getDrawable(R.color.branco));
            fragment.setVisibility(View.GONE);
            Log.d("Passou", "passou");
            transaction.commit();
        }else {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            fragment.setVisibility(View.VISIBLE);
            constraintLayout.setBackground(getResources().getDrawable(R.color.cinza));
            transaction.replace(R.id.frameFragment, destinoFragment);
            Log.d("nao passou", "nao passou");
            transaction.commit();
        }
    }

    public void abrirOuFecharFragmentAlterar(){
        if(alterarFragment.isVisible()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(alterarFragment);
            constraintLayout.setBackground(getResources().getDrawable(R.color.branco));
            fragment.setVisibility(View.GONE);
            Log.d("Passou", "passou");
            transaction.commit();
        }
    }

    public void abrirFragmentoAlterar(int position,String goTo){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment.setVisibility(View.VISIBLE);
        constraintLayout.setBackground(getResources().getDrawable(R.color.cinza));
        Bundle data = new Bundle();
        if(goTo == "origem") {
            Origem origem = listaOrigem.get(position);
            data.putString("data", "origem");
            data.putInt("id", origem.getId());
            data.putString("local", origem.getLocal());
            data.putInt("kms", origem.getKm());
            alterarFragment.setArguments(data);
        }else if(goTo == "destino"){
            Destino destino = listaDestino.get(position);
            data.putString("data","destino");
            data.putInt("id",destino.getId());
            data.putString("local",destino.getLocal());
            data.putInt("kms",destino.getKm());
            alterarFragment.setArguments(data);
        }
        transaction.replace(R.id.frameFragment, alterarFragment);
        Log.d("nao passou", "nao passou");
        transaction.commit();
    }

    public void abrirFragmentoIdVeiculo(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment.setVisibility(View.VISIBLE);
        constraintLayout.setBackground(getResources().getDrawable(R.color.cinza));
        transaction.replace(R.id.frameFragment, editarIdVeiculoFragment);
        transaction.commit();
    }

    public void abrirFragmentoIdCondutor(){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment.setVisibility(View.VISIBLE);
        constraintLayout.setBackground(getResources().getDrawable(R.color.cinza));
        transaction.replace(R.id.frameFragment,editarIdCondutorFragment);
        transaction.commit();
    }


    @Override
    protected void onResume() {
        super.onResume();

    }

    public void setupUI(final View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof TextView)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    if (destinoFragment.isVisible()){
                        destinoFragment.onPause();
                        System.out.println("entrou 1");
                    }
                    if(origemFragment.isVisible()){
                        origemFragment.onPause();
                        System.out.println("entrou 2");
                    }

                    return false;
                }
            });
        }

        //If a layout container, iterate over children and seed recursion.
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    @Override
    public void onBackPressed() {
        Intent voltarTelaMain = new Intent(VeiculoActivity.this,MainActivity.class);
        startActivity(voltarTelaMain);
    }
}

