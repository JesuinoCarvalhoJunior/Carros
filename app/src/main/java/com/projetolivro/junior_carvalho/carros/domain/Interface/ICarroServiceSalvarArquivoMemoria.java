package com.projetolivro.junior_carvalho.carros.domain.Interface;

import android.content.Context;

/**
 * Created by Junior_Carvalho on 15/04/2016.
 */
public interface ICarroServiceSalvarArquivoMemoria {

    void salvarArquivoMemoriaInterna(Context context, String url, String json);

     void salvarArquivoMemoriaExterna(Context context, String url, String json);

}
