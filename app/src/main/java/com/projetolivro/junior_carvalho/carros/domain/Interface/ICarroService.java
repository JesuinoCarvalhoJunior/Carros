package com.projetolivro.junior_carvalho.carros.domain.Interface;

import android.content.Context;

import com.projetolivro.junior_carvalho.carros.domain.Carro;

import java.util.List;

/**
 * Created by Junior_Carvalho on 27/03/2016.
 */
public interface ICarroService {

     List<Carro> getCarros(Context context, int tipo);
}
