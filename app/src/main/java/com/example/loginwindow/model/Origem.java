package com.example.loginwindow.model;

import java.io.Serializable;

public class Origem implements Serializable {
    private int id;
    private String local;
    private String hora;
    private int km;

    public Origem() {
    }

    public Origem( String local, String hora, int km) {
        this.local = local;
        this.hora = hora;
        this.km = km;
    }

    public Origem(int id, String local, String hora, int km) {
        this.id = id;
        this.local = local;
        this.hora = hora;
        this.km = km;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public int getKm() {
        return km;
    }

    public void setKm(int km) {
        this.km = km;
    }
}
