package com.projetolivro.junior_carvalho.carros.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.projetolivro.junior_carvalho.carros.R;
import com.projetolivro.junior_carvalho.carros.adapter.CarroAdapter;
import com.projetolivro.junior_carvalho.carros.domain.BD.CarroDB;
import com.projetolivro.junior_carvalho.carros.domain.Carro;

import org.parceler.Parcels;

import java.util.List;

// utilizada para selecionar carro
public class CarrosIntentActivity extends BaseActivity {


    private List<Carro> carros;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carros_intent);


         //(*1*) le as informacoes da intent
        Intent intent = getIntent();
        Uri uri = intent.getData();
        // impriime ACTION_VIEW ou ACTION_PICK
        Log.d("livroandroid ", "Action: " + intent.getAction());
        // imprre Carro
        Log.d("livroandroid ", "Schema: " + uri.getScheme());
        //impreme br.com.livroandroid
        Log.d("livroandroid ", "Host: " + uri.getHost());
        // imprime /carro ou /carro/nome
        Log.d("livroandroid ", "Path: " + uri.getPath());
        // imprime /schema  nome do carro
        Log.d("livroandroid ", "PathSegmentsw: " + uri.getPathSegments());

// RecyclerView
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        // configura a tollbar como a action bar
        setUpToolBar();

        CarroDB db = new CarroDB(this);

        try {
            if ("/carros".equals(uri.getPath())){
                //(*2*) lista todos os carros
                this.carros = db.findAll();

                recyclerView.setAdapter(new CarroAdapter(this, carros, onClickCarros()));

            } else {
                //(*3*)  busca carro pelo nome

                List<String> paths = uri.getPathSegments();
                String nome = paths.get(1);

                Carro carro = db.findAllByNome(nome);
                // se encontrou o carro, abre a activity oara mostralo

                Intent carroIntent = new Intent(this, CarroActivity.class);
                carroIntent.putExtra("carro", Parcels.wrap(carro));
                startActivity(carroIntent);
                finish();
            }
        } finally {
            db.close();
        }

    }

    private CarroAdapter.CarroOnClickListener onClickCarros() {
        return new CarroAdapter.CarroOnClickListener(){

            //trata o evento
            @Override
            public void onClickCarro(View view, int idx) {

                Carro c = carros.get(idx);

                //(*4*)  retorna o caro selecionado para quem chamou
                Intent intent = new Intent();
                intent.putExtra("nome", c.nome);
                intent.putExtra("url_foto", c.urlFoto);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }

            @Override
            public void onLongClickCarro(View view, int idx) {

                // nada aqui
            }
        };
    }
}
