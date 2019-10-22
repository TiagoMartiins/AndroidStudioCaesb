package com.example.loginwindow.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.loginwindow.R;

import java.io.Serializable;


public class Usuario implements Serializable {

    private int id;
    private String nome;
    private String email;
    private String equipe;
    private String senha;
    private String dataCadastro;
    //private Bitmap fotoPerfil;

    public Usuario(){

    }

    public Usuario(int id,String nome,String email, String senha,String dataCadastro,String equipe){
    this.id = id;
    this.nome = nome;
    this.email = email;
    this.equipe = equipe;
    this.senha = senha;
    this.dataCadastro = dataCadastro;
    //setFotoPerfil(fotoPerfil);
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return this.nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return this.senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getEquipe() {
        return this.equipe;
    }

    public void setEquipe(String equipe) {
        this.equipe = equipe;
    }

    public String getDataCadastro(){
        return this.dataCadastro;
    }

    public void setDataCadastro(String dataCadastro){
        this.dataCadastro = dataCadastro;
    }


}
