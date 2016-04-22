package com.projetolivro.junior_carvalho.carros.domain.BD;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.projetolivro.junior_carvalho.carros.domain.Carro;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Junior_Carvalho on 19/04/2016.
 */
public class CarroDB extends SQLiteOpenHelper {

    // nome do banco
    private static final String TAG = "sql";
    public static final String NOME_BANCO = "livro_android.sqlite";
    private static final int VERSAO_BANCO = 1;

    public CarroDB(Context context) {
        // context, bd, factory, versao
        super(context, NOME_BANCO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG, "Criando tabela carro...");

        db.execSQL("create table if not exists carro" +
                " (_id integer primary key autoincrement, " +
                "nome text, " +
                "desc text, " +
                "url_foto text," +
                "url_info text," +
                "url_video text," +
                "latitude text, " +
                "longitude text, " +
                "tipo text);");
        Log.d(TAG, "Tabela carro criada com sucesso!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
// caso mude a versai do BD , podemos escrever comando aqui. Ex:

        if (oldVersion == 1 && newVersion == 2) {
            // mudou a versao...
        }

    }

    // insere nova carro ou atualizar caso exista
    public long save(Carro carro) {
        long id = carro.id;

        SQLiteDatabase db = getWritableDatabase();
        try {
            ContentValues values = new ContentValues();

            values.put("nome", carro.nome);
            values.put("desc", carro.desc);
            values.put("url_foto", carro.urlFoto);
            values.put("url_info", carro.urlIinfo);
            values.put("url_video", carro.urlVideo);
            values.put("latitude", carro.latitude);
            values.put("longitude", carro.longitude);
            values.put("tipo", carro.tipo);

            if (id != 0) {
                String _id = String.valueOf(carro.id);

                // whereArgs = valor do parametro a ser passado para _id=?
                // sempre que uasr "_id=?" deve usar whereArgs
                // ou
                // usar assim
                // "_id = " + id
                String[] whereArgs = new String[]{
                        _id
                };
                // update carro set values ... where _id=?
                int count = db.update("carro", values, "_id=?", whereArgs);
                return count;

            } else {

                // insert into carro values (...)
                id = db.insert("carro", "", values);
                return id;
            }
        } finally {
            db.close();
        }
    }

    // deleta o carro
    public int delete(Carro carro) {

        SQLiteDatabase db = getWritableDatabase();
        try {
            // delete from carro where _id=?
            int count = db.delete("carro", "_id=?", new String[]{
                    String.valueOf(carro.id)
            });

            Log.i(TAG, "Deletou [ " + count + " ] registro!");
            return count;
        } finally {
            db.close();
        }
    }


    // deleta o carro do tipo fornecidp
    public int deleteCarroByTipo(String tipo) {

        SQLiteDatabase db = getWritableDatabase();
        try {
            // delete from carro where _id=?
            int count = db.delete("carro", "tipo=?", new String[]{
                    tipo
            });

            Log.i(TAG, "Deletrou [ " + count + " ] registro!");
            return count;
        } finally {
            db.close();
        }
    }

    // consulta a lista com todos os carros
    public List<Carro> findAll() {
        SQLiteDatabase db = getWritableDatabase();
        try {
            // select * from carro
            Cursor c = db.query("carro", null, null, null, null, null, null, null);
            return toList(c);
        } finally {
            db.close();
        }
    }

    // conulsta o carro pelo tipo
    public List<Carro> findAllByTipo(String tipo) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            // select * from carro where tipo=?
            Cursor c = db.query("carro", null, "tipo = '" + tipo + "'", null, null, null, null);
            return toList(c);
        } finally {
            db.close();
        }
    }

    // Lê cursor e cria a lista de carro
    private List<Carro> toList(Cursor c) {
        List<Carro> carros = new ArrayList<Carro>();

        if (c.moveToFirst()) {
            do {
                Carro carro = new Carro();
                carros.add(carro);
                //recuera os atributos de carro

                // index comeca em 0 e caso a coluna nao exista o indice recebe -1
                carro.id = c.getLong(c.getColumnIndex("_id"));
                carro.nome = c.getString(c.getColumnIndex("nome"));
                carro.desc = c.getString(c.getColumnIndex("desc"));
                carro.urlIinfo = c.getString(c.getColumnIndex("url_info"));
                carro.urlFoto = c.getString(c.getColumnIndex("url_foto"));
                carro.urlVideo = c.getString(c.getColumnIndex("url_video"));
                carro.latitude = c.getString(c.getColumnIndex("latitude"));
                carro.longitude = c.getString(c.getColumnIndex("longitude"));
                carro.tipo = c.getString(c.getColumnIndex("tipo"));
            } while (c.moveToNext());
        }
        return carros;
    }

    // executa um SQL
    public void execSQL(String sql) {
        SQLiteDatabase db = getWritableDatabase();

        try {
            db.execSQL(sql);
        } finally {
            db.close();
        }
    }
    // executa um SQL
    public void execSQL(String sql, Object[] args) {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.execSQL(sql, args);
        } finally {
            db.close();
        }
    }


}
