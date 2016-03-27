package com.projetolivro.junior_carvalho.carros.domain;

import android.content.Context;

import com.projetolivro.junior_carvalho.carros.domain.Interface.ICarroService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Junior_Carvalho on 27/03/2016.
 */
public class CarroService implements ICarroService{

   @Override
    public List<Carro> getCarros(Context context, int tipo) {
        String tipoString = context.getString(tipo);
        List<Carro> carros = new ArrayList<Carro>();

        for (int i = 0; i < 20; i++){
            Carro c = new Carro();
            c.nome = "Carro " + tipoString + ": " + i; // nome dinamico conforme o tipo
            c.desc = "Desc " + i;
            c.urlfoto = "http://www.livroandroid.com.br/livro/carros/esportivos/Ferrari_FF.png";
            carros.add(c);
        }
        return carros;
    }


}
