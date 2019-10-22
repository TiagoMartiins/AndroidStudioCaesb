package com.example.loginwindow.model;


import java.util.Date;

public class Veiculo {

    private int idVeiculo;
    private Usuario matriculaUsuario;
    private int kilometragem;
    private String localOrigem;
    private String localDestino;
    private Date data;

    public Veiculo(){

    }

    public Veiculo(int idVeiculo, Usuario matriculaUsuario, int kilometragem, String localOrigem, String localDestino, Date data) {
        this.idVeiculo = idVeiculo;
        this.matriculaUsuario = matriculaUsuario;
        this.kilometragem = kilometragem;
        this.localOrigem = localOrigem;
        this.localDestino = localDestino;
        this.data = data;
    }

    public int getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(int idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public Usuario getMatriculaUsuario() {
        return matriculaUsuario;
    }

    public void setMatriculaUsuario(Usuario matriculaUsuario) {
        this.matriculaUsuario = matriculaUsuario;
    }

    public int getKilometragem() {
        return kilometragem;
    }

    public void setKilometragem(int kilometragem) {
        this.kilometragem = kilometragem;
    }

    public String getLocalOrigem() {
        return localOrigem;
    }

    public void setLocalOrigem(String localOrigem) {
        this.localOrigem = localOrigem;
    }

    public String getLocalDestino() {
        return localDestino;
    }

    public void setLocalDestino(String localDestino) {
        this.localDestino = localDestino;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }
}
