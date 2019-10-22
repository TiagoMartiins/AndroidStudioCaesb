package com.example.loginwindow.model;

import java.util.Date;

public class Encargo {

    private String titulo;
    private String descricao;
    private Date data;

    public Encargo(){

    }

    public Encargo(String titulo, String descricao, Date data) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.data = data;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
