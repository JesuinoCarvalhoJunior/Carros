package com.projetolivro.junior_carvalho.carros.domain.Servico;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.projetolivro.junior_carvalho.carros.domain.Interface.ICarroServiceSalvarArquivoMemoria;

import java.io.File;

import livroandroid.lib.utils.FileUtils;
import livroandroid.lib.utils.IOUtils;
import livroandroid.lib.utils.SDCardUtils;

/**
 * Created by Junior_Carvalho on 16/04/2016.
 */
public class CarroServiceSalvarArquivoMemoria implements ICarroServiceSalvarArquivoMemoria {

    private static final String TAG = "CarroService";

    @Override
    public  void salvarArquivoMemoriaInterna(Context context, String url, String json) {
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        File file = FileUtils.getFile(context, fileName);
        IOUtils.writeString(file, json);
        Log.d(TAG, "Arquivo salvo: " + file);
    }

    @Override
    public  void salvarArquivoMemoriaExterna(Context context, String url, String json) {
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        //cria um arquivo privado
        File f = SDCardUtils.getPrivateFile(context, fileName, Environment.DIRECTORY_DOWNLOADS);
        IOUtils.writeString(f, json);
        Log.d(TAG, "1- Arquivo privado salvo na pasta DOWNLOADS" + f);
        // cria arquivo publico

        f = SDCardUtils.getPublicFile(fileName, Environment.DIRECTORY_DOWNLOADS);
        IOUtils.writeString(f, json);
        Log.d(TAG, "1- Arquivo publico salvo na pasta DOWNLOADS" + f);
    }
}
