package com.example.loginwindow.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.loginwindow.R;
import com.example.loginwindow.sqlite.UsuarioSQLITE;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CadastroActivity extends AppCompatActivity implements View.OnClickListener {


    private static final int SELECAO_GALERIA = 200;
    EditText nomeText,senhaText,emailText,equipeText;
    UsuarioSQLITE banco;
    Button galery_btn;
    Bitmap imagemEscolhida;
    ImageView imagemUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        nomeText = (EditText) findViewById(R.id.nomeCadastro);
        senhaText = (EditText) findViewById(R.id.senhaCadastro);
        emailText = (EditText) findViewById(R.id.emailCadastro);
        equipeText = (EditText) findViewById(R.id.equipeCadastro);
        galery_btn = (Button) findViewById(R.id.btnGalery);
        imagemUser = (ImageView) findViewById(R.id.imageView2);



        //Metodo para esconder teclado
        setupUI(findViewById(R.id.cadastroActivity));

        galery_btn.setOnClickListener(this);


    }



    public void cadastrar(View view){
        if(verificarCampos()){
            try {
                banco = new UsuarioSQLITE(CadastroActivity.this);
                banco.addUser(nomeText, emailText, senhaText,equipeText);
                Toast.makeText(CadastroActivity.this,"Cadastrado com Sucesso",Toast.LENGTH_SHORT).show();
                Intent irParaLogin = new Intent(CadastroActivity.this, LoginActivity.class);
                startActivity(irParaLogin);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    public boolean verificarCampos(){
        //Recuperando campos da interface Cadastro
        String nome = nomeText.getText().toString();
        String email = emailText.getText().toString();
        String senha = senhaText.getText().toString();

        if(nome.isEmpty()){
            Toast.makeText(CadastroActivity.this,"Preencha o nome",Toast.LENGTH_SHORT).show();
            return false;
        }else if(email.isEmpty()){
            Toast.makeText(CadastroActivity.this,"Preencha o email",Toast.LENGTH_SHORT).show();
            return false;
        }else if(senha.isEmpty()){
            Toast.makeText(CadastroActivity.this,"Preencha a senha",Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
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
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboardActivity(CadastroActivity.this);
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
    public void onClick(View view) {
        if (view.getId() == R.id.btnGalery){

            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI );
            if ( i.resolveActivity(getPackageManager()) != null ){
                startActivityForResult(i, SELECAO_GALERIA );
            }
    }
}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Bitmap imagem = null;

            try {

                switch ( requestCode ){
                    case SELECAO_GALERIA:
                        Uri localImagemSelecionada = data.getData();
                        imagem = MediaStore.Images.Media.getBitmap(getContentResolver(), localImagemSelecionada );
                        break;
                }

                if ( imagem != null ){

                    imagemEscolhida = imagem;
                    Toast.makeText(CadastroActivity.this,"image was setted",Toast.LENGTH_SHORT).show();
                    imagemUser.setImageBitmap(imagemEscolhida);


                }

            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }


}
