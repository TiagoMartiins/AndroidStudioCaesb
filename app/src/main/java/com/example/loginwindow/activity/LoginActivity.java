package com.example.loginwindow.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.loginwindow.R;
import com.example.loginwindow.model.Usuario;
import com.example.loginwindow.sqlite.UsuarioSQLITE;
import com.google.android.material.textfield.TextInputEditText;

import static android.view.View.GONE;

public class LoginActivity extends AppCompatActivity {

    TextInputEditText campoEmail,campoSenha;
    UsuarioSQLITE usuarioSQLITE;
    Usuario usuario,usuarioTeste;
    String email,senha;
    ImageView imageView;
    ProgressBar progressBar;
    Button logar;
    TextView ntc;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Referenciado os campos da tela Login,Email,Senha,ProfressBar,Botões
        campoEmail = findViewById(R.id.editLoginEmail);
        campoSenha = findViewById(R.id.editLoginSenha);
        progressBar = findViewById(R.id.progressLogin);
        imageView = findViewById(R.id.imageView);
        logar = findViewById(R.id.button);
        ntc = findViewById(R.id.textView);

        //Instanciando classe de negocio e classe modelo de banco para usuario
        usuarioSQLITE = new UsuarioSQLITE(this);
        usuario = new Usuario();
        usuarioTeste = new Usuario();


        //Colocando Animação nos componentes da tela
        Animation deslocamento = new TranslateAnimation(1000, 0,0 , 0);
        deslocamento.setDuration(1000);
        imageView.startAnimation(deslocamento);
        campoEmail.startAnimation(deslocamento);
        campoSenha.startAnimation(deslocamento);
        logar.startAnimation(deslocamento);
        ntc.startAnimation(deslocamento);

        //Metodo para esconder teclado
        //Passa por parametro o id do layout
        setupUI(findViewById(R.id.parent));


        //Torna invisivel a barra de progresso
        progressBar.setVisibility( GONE );
    }


    public boolean verificarCampos(){
        //Recuperando campos da interface Login
        email = campoEmail.getText().toString();
        senha = campoSenha.getText().toString();


        if(senha.isEmpty()){
            Toast.makeText(LoginActivity.this,"Preencha a senha",Toast.LENGTH_SHORT).show();
            return false;
        }else if(email.isEmpty()){
            Toast.makeText(LoginActivity.this,"Preencha o email",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }
    public void logar(View view){
        if(verificarCampos()){
            usuario.setEmail(email);
            usuario.setSenha(senha);
            Usuario usuarioLogado = usuarioSQLITE.autenticarUsuario(usuario);
            if(usuarioLogado != null) {
                progressBar.setVisibility( View.VISIBLE );
                Intent telaPrincipal = new Intent(LoginActivity.this, MainActivityNova.class);
                System.out.println("------ usuario id :");
                System.out.println(usuarioLogado.getId());
                System.out.println(usuarioLogado.getNome());
                telaPrincipal.putExtra("usuario", usuarioLogado);
                startActivity(telaPrincipal);
                Toast toast = Toast.makeText(LoginActivity.this, "Logado com sucesso", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER,0,0);
                toast.show();

            }else{
                Toast.makeText(LoginActivity.this, "Usuario ou senha incorreto", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(LoginActivity.this, "Preencha corretamente os campos", Toast.LENGTH_SHORT).show();
        }
    }

    public void abriTelaCadastro(View view){
        Intent telaCadastro = new Intent(LoginActivity.this, CadastroActivity.class);
        startActivity(telaCadastro);
    }

    public static void hideSoftKeyboardActivity(Activity activity) {
        InputMethodManager inputMethodManager =
                (InputMethodManager) activity.getSystemService(
                        Activity.INPUT_METHOD_SERVICE);
        if(activity.getCurrentFocus() != null) {
            inputMethodManager.hideSoftInputFromWindow(
                    activity.getCurrentFocus().getWindowToken(), 0);
        }

    }



    public void setupUI(View view) {

        // Set up touch listener for non-text box views to hide keyboard.
        if (!(view instanceof EditText || view instanceof TextView)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboardActivity(LoginActivity.this);
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


}
