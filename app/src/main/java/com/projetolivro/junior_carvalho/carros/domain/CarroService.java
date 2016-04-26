package com.projetolivro.junior_carvalho.carros.domain;

import android.content.Context;
import android.util.Log;

import com.projetolivro.junior_carvalho.carros.R;
import com.projetolivro.junior_carvalho.carros.domain.BD.CarroDB;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import livroandroid.lib.utils.FileUtils;
import livroandroid.lib.utils.HttpHelper;

/**
 * Created by Junior_Carvalho on 27/03/2016.
 */
//public class CarroService {//} implements ICarroService {
//public class CarroService extends CarroServiceSalvarArquivoMemoria {
public class CarroService {

    private static final boolean LOG_ON = false;
    private static final String TAG = "CarroService";

    private static final String URL = "http://www.livroandroid.com.br/livro/carros/carros_{tipo}.json";


/*
// pesquisa no arquivo de cache
    public static List<Carro> getCarros(Context context, int tipo) throws IOException {
        List<Carro> carros = getCarrosFromArquivo(context, tipo);
        if (carros != null && carros.size() > 0) {
            // encontrou o arquivo
            return carros;
        }
        // se nao encontrar, busca no webservice
        carros = getCarrosFromWebService(context, tipo);
        return carros;
    }*/


    // ontem lista de carro lida no arquivo de cache
    //abre  o arquivo salvo, se existir, e cria a lista d ecarros
    public static List<Carro> getCarrosFromArquivo(Context context, int tipo) throws IOException {

        String tipoString = getTipo(tipo);
        String fileName = String.format("carros_%s.json", tipoString);

        Log.d(TAG, "abrindo arquivo: " + fileName);

        //lê o arquivo da memoria interna
        String json = FileUtils.readFile(context, fileName, "UTF-8");

        if (json == null) {
            Log.d(TAG, "Arquivo: " + fileName + " não encontrado.");
            return null;
        }
        List<Carro> carros = parserJSON(context, json);
        Log.d(TAG, "Retornando carros do arquivo: " + fileName + ".");
        return carros;



    }


    // pesquisa no banco de dados
    // parametro "refresh = false  = busca carros no BD"
    // parametro "refresh = true  = busca carros no Webservice, Ex: ao executar o Pull Refresh"
    public static List<Carro> getCarros(Context context, int tipo, boolean refresh) throws IOException {

        // busca os carros no BD somente se REFRESH = FALSE
        List<Carro> carros = !refresh ? getCarrosFromBanco(context, tipo) : null ;

        if (carros != null && carros.size() > 0) {
            // retorna os carros encotrados no BD
            return carros;
        }
        // se nao encontrar, busca no webservice
        carros = getCarrosFromWebService(context, tipo);
        return carros;
    }

    private static List<Carro> getCarrosFromBanco(Context context, int tipo) throws IOException {

        CarroDB db = new CarroDB(context);
        try {
            String tipoString = getTipo(tipo);
            List<Carro> carros = db.findAllByTipo(tipoString);
            Log.d(TAG, "Retornando : " + carros.size() + " carros[" + tipoString + "] do banco.");
            return carros;

        } finally {
            db.close();
        }
    }



/*
   // Salva arquivo na memoria interna e externa
    // utilizando JSON
    public static List<Carro> getCarrosFromWebService(Context context, int tipo) throws IOException {

        String tipoString = getTipo(tipo);
        String url = URL.replace("{tipo}", tipoString);

        Log.d(TAG, "URL: " + url + ".");

        //faz requisicao HTTP no servidor e retorna a string com o conteudo
        HttpHelper http = new HttpHelper();
        String json = http.doGet(url);

        List<Carro> carros = parserJSON(context, json);

        //salva o arquivo json na memoria
        CarroServiceSalvarArquivoMemoria salvarArquivoMemoria = new CarroServiceSalvarArquivoMemoria();
        //salva o arquivo json n a memoria interna
        salvarArquivoMemoria.salvarArquivoMemoriaInterna(context, url, json);
        //salva o arquivo json n a memoria externa
        salvarArquivoMemoria.salvarArquivoMemoriaExterna(context, url, json);
        return carros;
    }*/

    // utilizando JSON
    public static List<Carro> getCarrosFromWebService(Context context, int tipo) throws IOException {

        String tipoString = getTipo(tipo);
        String url = URL.replace("{tipo}", tipoString);

        Log.d(TAG, "URL: " + url + ".");

        //faz requisicao HTTP no servidor e retorna a string com o conteudo
        HttpHelper http = new HttpHelper();
        String json = http.doGet(url);

        List<Carro> carros = parserJSON(context, json);

        //Depois de buscar os arquivo no webservice , salva no bd
        salvaCarros(context, tipo, carros);
        return carros;
    }

    private static void salvaCarros(Context context, int tipo, List<Carro> carros) {
        CarroDB db = new CarroDB(context);

        try {
            //Deleta os carros antigos pelo tipo ara limpar o BD
            String tipoString = getTipo(tipo);
            db.deleteCarroByTipo(tipoString);
            //salva todos os carros
            for (Carro c : carros) {
                c.tipo = tipoString;
                Log.d(TAG, "Salvando carro: " + c.nome);
                db.save(c);
            }
        } finally {
            db.close();
        }

    }

    private static String getTipo(int tipo) {
        if (tipo == R.string.classicos) {
            return "classicos";
        } else if (tipo == R.string.esportivos) {
            return "esportivos";
        }


        return "luxo";
    }

/*    //faz leiura do arqyuivo qe esta na pasta /res/raw
    private static String readFile(Context context, int tipo) throws IOException {
        if (tipo == R.string.classicos) {
            return FileUtils.readRawFileString(context, R.raw.carros_classicos, "UTF-8");
        } else if (tipo == R.string.esportivos) {
            return FileUtils.readRawFileString(context, R.raw.carros_esportivos, "UTF-8");
        }
        return FileUtils.readRawFileString(context, R.raw.carros_luxo, "UTF-8");

    }*/


    private static List<Carro> parserJSON(Context context, String json) throws IOException {
        List<Carro> carros = new ArrayList<Carro>();
        try {
            JSONObject root = new JSONObject(json);
            JSONObject obj = root.getJSONObject("carros");
            JSONArray jsonCarros = obj.getJSONArray("carro");
            // Insere cada carro na lista
            for (int i = 0; i < jsonCarros.length(); i++) {
                JSONObject jsonCarro = jsonCarros.getJSONObject(i);
                Carro c = new Carro();
                // Lê as informações de cada carro
                c.nome = jsonCarro.optString("nome");
                c.desc = jsonCarro.optString("desc");
                c.urlFoto = jsonCarro.optString("url_foto");
                c.urlIinfo = jsonCarro.optString("url_info");
                c.urlVideo = jsonCarro.optString("url_video");
                c.latitude = jsonCarro.optString("latitude");
                c.longitude = jsonCarro.optString("longitude");
                if (LOG_ON) {
                    Log.d(TAG, "Carro " + c.nome + " > " + c.urlFoto);
                }
                carros.add(c);
            }
            if (LOG_ON) {
                Log.d(TAG, carros.size() + " encontrados.");
            }
        } catch (JSONException e) {
            throw new IOException(e.getMessage(), e);
        }
        return carros;
    }


    /**
     * Salvar arquivo na memoria interna
     * sera salvo o arquivo JSON que esta sendo usando pra buscar os carros
     */

/*    @Override
    public static void salvarArquivoMemoriaInterna(Context context, String url, String json) {
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        File file = FileUtils.getFile(context, fileName);
        IOUtils.writeString(file, json);
        Log.d(TAG, "Arquivo salvo: " + file);
    }

    @Override
    public static void salvarArquivoMemoriaExterna(Context context, String url, String json) {
        String fileName = url.substring(url.lastIndexOf("/") + 1);
        //cria um arquivo privado
        File f = SDCardUtils.getPrivateFile(context, fileName, Environment.DIRECTORY_DOWNLOADS);
        IOUtils.writeString(f, json);
        Log.d(TAG, "1- Arquivo privado salvo na pasta DOWNLOADS" + f);
        // cria arquivo publico

        f = SDCardUtils.getPublicFile(fileName, Environment.DIRECTORY_DOWNLOADS);
        IOUtils.writeString(f, json);
        Log.d(TAG, "1- Arquivo publico salvo na pasta DOWNLOADS" + f);
    }*/


   /*
     private static void salvarArquivoMemoriaInterna(Context context, String url, String json){
    }
    private static void salvarArquivoMemoriaExterna(Context context, String url, String json){
    }
    */


    // utilizando XML

/*
    public List<Carro> getCarros(Context context, int tipo) throws IOException {

        String xml = readFile(context, tipo);
        List<Carro> carros = parseXML(context, xml);
        return carros;

    }



    //faz leiura do arqyuivo qe esta na pasta /res/raw
    private static String readFile(Context context, int tipo) throws IOException {
        if (tipo == R.string.classicos) {
            return FileUtils.readRawFileString(context, R.raw.carros_classicos, "UTF-8");

        } else if (tipo == R.string.esportivos) {
            return FileUtils.readRawFileString(context, R.raw.carros_esportivos, "UTF-8");

        }
        return FileUtils.readRawFileString(context, R.raw.carros_luxo, "UTF-8");
    }

    // faz o parser do XML e cria a lista e carros
    private List<Carro> parseXML(Context context, String xml) {
        List<Carro> carros = new ArrayList<Carro>();
        Element root = XMLUtils.getRoot(xml, "UTF-8");

        // lê todas as tags <carro>

        List<Node> nodeCarros = XMLUtils.getChildren(root, "carro");
        //insere cada carro na lista

        for (Node node : nodeCarros) {
            Carro c = new Carro();

            // ê informacoes de cada carro
            c.nome = XMLUtils.getText(node, "nome");
            c.desc = XMLUtils.getText(node, "desc");
            c.urlfoto = XMLUtils.getText(node, "url_foto");
            c.urlIinfo = XMLUtils.getText(node, "url_info");
            c.urlVideo = XMLUtils.getText(node, "url_video");
            c.latitude = XMLUtils.getText(node, "latitude");
            c.longitude = XMLUtils.getText(node, "longitude");

            if (LOG_ON) {
                Log.d(TAG, "Carro" + c.nome + "> " + c.urlfoto);
            }
            carros.add(c);
        }
        if (LOG_ON) {
            Log.d(TAG, carros.size() + " encotrados.");
        }
        return carros;

    }

*/




    /*public List<Carro> getCarros(Context context, int tipo) {
        String tipoString = context.getString(tipo);
        List<Carro> carros = new ArrayList<Carro>();

        for (int i = 0; i < 20; i++) {
            Carro c = new Carro();
            c.nome = "Carro " + tipoString + ": " + i; // nome dinamico conforme o tipo
            c.desc = "Desc " + i;
            c.urlfoto = "http://www.livroandroid.com.br/livro/carros/esportivos/Ferrari_FF.png";
            carros.add(c);
        }
        return carros;
    }
*/

}
