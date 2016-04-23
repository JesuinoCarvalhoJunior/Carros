package com.projetolivro.junior_carvalho.carros.activity;

import android.os.Bundle;

import android.widget.ImageView;

import com.projetolivro.junior_carvalho.carros.R;
import com.projetolivro.junior_carvalho.carros.domain.Carro;
import com.projetolivro.junior_carvalho.carros.fragments.CarroFragment;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

public class CarroActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carro);
        // Configura a Toolbar como a action bar
        setUpToolBar();

        // Título da toolbar e botão up navigation
        Carro c = Parcels.unwrap(getIntent().getParcelableExtra("carro"));
        getSupportActionBar().setTitle(c.nome);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Imagem de header na action bar
        ImageView appBarImg = (ImageView) findViewById(R.id.appBarImg);
        Picasso.with(getContext()).load(c.urlFoto).into(appBarImg);

        // Adiciona o fragment no layout
        if (savedInstanceState == null) {
            // Cria o fragment com o mesmo Bundle (args) da intent
            CarroFragment frag = new CarroFragment();
            frag.setArguments(getIntent().getExtras());
            // Adiciona o fragment no layout
            getSupportFragmentManager().beginTransaction().add(R.id.CarroFragment, frag).commit();
        }
    }


 /*   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carro);

        // configura toolbar como a action bar
        setUpToolBar();

        // titulo da toolbar e botao up navigation
        //usando Parcels na dependencia
        Carro c = Parcels.unwrap(getIntent().getParcelableExtra("carro"));

        // define o título da Action Bar
        getSupportActionBar().setTitle(c.nome);

        //habilita set para esquerda (up navegation)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // imagem de header no action bar

        ImageView appBarImg = (ImageView) findViewById(R.id.appBarImg);


        Picasso.with(getContext()).load(c.urlFoto).into(appBarImg);

        // add o layout ao fragment
        if (savedInstanceState == null) {
            // cria o fragment com o mesmo Bundle (args) da intent
            CarroFragment carfrag = new CarroFragment();
            carfrag.setArguments(getIntent().getExtras());
            //adicionar o fragmento ao layout
            getSupportFragmentManager().
                    beginTransaction().
                    add(R.id.CarroFragment, carfrag).commit();
        }
    }
    // androidmanifest.xml sera configurado  a activity CarroActivity
    // para que sua mae seja MainActivity para poder utilizar o up navegation*/
}
