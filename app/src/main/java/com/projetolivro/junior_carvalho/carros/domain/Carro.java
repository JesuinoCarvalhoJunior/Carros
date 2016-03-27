package com.projetolivro.junior_carvalho.carros.domain;

import java.io.Serializable;

/**
 * Created by Junior_Carvalho on 27/03/2016.
 */
public class Carro implements Serializable {

    private static final long serialVersionUID = -647602086045131846L;
    public long id;
    public String tipo;
    public String nome;
    public String desc;
    public String urlfoto;
    public String urlIinfo;
    public String urlVideo;
    public String latitude;
    public String longitude;

    @Override
    public String toString() {
        return "Carro{" + "nome='" + nome + '\'' +  '}';
    }
}
