package com.projetolivro.junior_carvalho.carros;

import android.app.Application;
import android.util.Log;

/**
 * Created by Junior_Carvalho on 25/03/2016.
 */

// utilizar variaveis de classe staticas nao é recomendado

// o receomendado é criar classe qe herda de Application (classe Application é um Singleton)
//Um Singleton é uma classe que permite a criação de uma única instância da classe.
//        O código para ser um singleton precisa controlar a unicidade de instância.


// CarrosAplication- classe para variaveis globais
// a classe Applaction faz parte do ciclo de vida da aplicacao
// o android vai criar essa classe junto ao processo da aplicacao

    // essa classe precisa ser registrada no manifest.xml

public class CarrosApplication extends Application {

    private static final String TAG = "CarrosApplication";
    private static CarrosApplication instance = null;

    public static CarrosApplication getInstance() {
        return instance; // Singleton
    }


    @Override
    public void onCreate(){
        super.onCreate();
        Log.d("TAG", "CarrosApplication.onCreate()");
        //Salva a instance para termos acesso como Singleton
        instance = this;
    }

    @Override
    public void onTerminate(){
        super.onTerminate();
        Log.d("TAG", "CarrosApplication.onTerminate()");
    }
}
