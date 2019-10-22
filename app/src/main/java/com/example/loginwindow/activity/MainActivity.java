package com.example.loginwindow.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import com.example.loginwindow.R;
import com.example.loginwindow.fragment.EditarPrincipalFragment;
import com.example.loginwindow.model.Usuario;
import com.example.loginwindow.sqlite.UsuarioSQLITE;
import com.github.clans.fab.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.MenuInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import static java.lang.String.valueOf;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Usuario usuario;
    UsuarioSQLITE banco;
    CardView cardVeiculo,cardServico,cardHoras,cardEquipe,cardOs;
    TextView textNomeUsuario,textEquipeUsuario,textData,textOs;
    private android.widget.GridLayout.LayoutParams layoutParams;
    String msg;
    LinearLayout fragment,linearPrincipal;
    private FloatingActionButton fab;
    private Animation animation;
    private EditarPrincipalFragment editarPrincipalFragment = new EditarPrincipalFragment();
    String osPrincipal,dataPrincipal;
    GridLayout grid;
    TextInputEditText osPrincipalEdit;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Instanciando a toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        animation = new AlphaAnimation(1, 0.5f); // Altera alpha de visível a invisível
        animation.setDuration(1000); // duração - meio segundo
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE); // Repetir infinitamente
        animation.setRepeatMode(Animation.REVERSE); //Inverte a animação no final para que o botão vá desaparecendo

        //Instanciando banco
        banco = new UsuarioSQLITE(MainActivity.this);

        //Refereciando os card da tela, já pode ser Clicado :D
        cardVeiculo = findViewById(R.id.cardViewVeiculo);
        cardEquipe = findViewById(R.id.cardViewEquipe);
        cardHoras = findViewById(R.id.cardViewHoras);
        cardServico = findViewById(R.id.cardViewServico);
        cardOs = findViewById(R.id.cardViewOs);
        //Referenciado texto nome e equipe relacionando ao usuario
        textNomeUsuario = findViewById(R.id.textNomePrincipal);
        textEquipeUsuario = findViewById(R.id.textEquipePrincipal);
        //Referenciando data e hora atual,  Obs : ver como atualizar hora de segundo em segundo
        textData = findViewById(R.id.textDataPrincipal);
        //Referenciando OS
        textOs = findViewById(R.id.textOs);
        //Pegando tela para alterar Os e data
        fragment = (LinearLayout) findViewById(R.id.frameFragment);
        linearPrincipal = (LinearLayout) findViewById(R.id.linearPrincipal);
        //Referenciando
        grid = (GridLayout) findViewById(R.id.grid);

        //Pegando o usuario logado e osPrincipal
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            usuario = (Usuario) extras.get("usuario");
            osPrincipal = (String) extras.get("osPrincipal");
            dataPrincipal = (String) extras.get("dataPrincipal");
        }

        if(osPrincipal != null){
            textOs.setText("OS "+osPrincipal);
        }else{
            textOs.setText("OS 2019");
        }

        textOs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirFragmentAlterar("os");
            }
        });

        if(dataPrincipal != null){
            textData.setText(dataPrincipal);
        }else{
            textData.setText("11/10/2019");
        }

        textData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                abrirFragmentAlterar("data");
            }
        });


        fab = findViewById(R.id.fabItem);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent voltarTelaInicial = new Intent(MainActivity.this,MainActivityNova.class);
                startActivity(voltarTelaInicial);
            }
        });
        fab.startAnimation(animation);

        exibirUsuarioLogado(usuario);

        cardServico.setOnClickListener(this);
        cardHoras.setOnClickListener(this);
        cardEquipe.setOnClickListener(this);
        cardVeiculo.setOnClickListener(this);
        cardOs.setOnClickListener(this);

    }




    //Metodo para aplicar Ações quando acionado , Chamando telas especificas de cada cardView
    @Override
    public void onClick(View view) {

        switch (view.getId()){

            case R.id.cardViewEquipe:
                Toast.makeText(MainActivity.this,"Equipe",Toast.LENGTH_SHORT).show();
                break;
            case R.id.cardViewServico:
                Toast.makeText(MainActivity.this,"Servico",Toast.LENGTH_SHORT).show();
                break;
            case R.id.cardViewVeiculo:
                Intent telaVeiculo = new Intent(MainActivity.this,VeiculoTesteRecycler.class);
                telaVeiculo.putExtra("usuario",usuario);
                startActivity(telaVeiculo);
                break;
            case R.id.cardViewHoras:
                Toast.makeText(MainActivity.this,"Horas",Toast.LENGTH_SHORT).show();
                break;
            case R.id.cardViewOs:
                Intent telaListaOrdemServico = new Intent(MainActivity.this,OsActivity.class);
                startActivity(telaListaOrdemServico);
                break;
            case R.id.linearPrincipal:
                verificarFragmentVisivel();
                break;
            case R.id.grid:
                verificarFragmentVisivel();
                break;

        }

        }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void verificarFragmentVisivel() {
        if (editarPrincipalFragment.isVisible()) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.remove(editarPrincipalFragment);
            linearPrincipal.setBackground(getResources().getDrawable(R.color.branco));
            fragment.setVisibility(View.GONE);
            Log.d("Passou", "passou");
            transaction.commit();
        }
    }

    //Configurando a toolbar, Inflando menu editado, veja no xml "menu_main"
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Colocando ações no menu da toolbar, apenas deslogando usuario
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.menuSair:
                    deslogarUsuario();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void abrirFragmentAlterar(String osOuData){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        fragment.setVisibility(View.VISIBLE);
        linearPrincipal.setBackground(getResources().getDrawable(R.color.cinza));
        Bundle data = new Bundle();
        if(osOuData == "os"){
            data.putString("data",osOuData);
            editarPrincipalFragment.setArguments(data);
        }else{
            data.putString("data",osOuData);
            editarPrincipalFragment.setArguments(data);
        }
        transaction.replace(R.id.frameFragment, editarPrincipalFragment);
        transaction.commit();
    }


    //Pegando o objeto Usuario e setando de acordo com seus atributos
    @RequiresApi(api = Build.VERSION_CODES.O)
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
//            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
//            Date date = new Date();
//            System.out.println(dateFormat.format(date));
//            String stringData = dateFormat.format(date);
//            textData.setText(stringData);

        }
    }


    //Metodo para deslogar Usuario, chamado quando usuario aciona o botão sair da toolbar
    public void deslogarUsuario(){

        usuario = null;

            Intent telaInicial = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(telaInicial);
            Toast.makeText(MainActivity.this,"Deslogado com Sucesso",Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onBackPressed() {
        Intent telaMainPrincipal = new Intent(MainActivity.this,MainActivityNova.class);
        startActivity(telaMainPrincipal);
    }
}


