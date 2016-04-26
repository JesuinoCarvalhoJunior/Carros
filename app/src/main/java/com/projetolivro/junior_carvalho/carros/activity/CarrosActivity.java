package com.projetolivro.junior_carvalho.carros.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.projetolivro.junior_carvalho.carros.R;
import com.projetolivro.junior_carvalho.carros.fragments.CarrosFragment;

public class CarrosActivity extends  BaseActivity { //AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carros);


        // configura toolbar como a action bar
        setUpToolBar();

        //habilita set para esquerda (up navegation)
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // define o título da Action Bar
        getSupportActionBar().setTitle(getString(getIntent().getIntExtra("tipo", 0)));

        if (savedInstanceState == null) {
            // cria o fragment com o mesmo Bundle (args) da intent
            CarrosFragment carfrag = new CarrosFragment();
            carfrag.setArguments(getIntent().getExtras());
            //adicionar o fragmento ao layout
            getSupportFragmentManager().
                    beginTransaction().
                    add(R.id.container, carfrag).
                    commit();
        }


    }
    // androidmanifest.xml sera configurado  a activity CarroActivity
    // para que sua mae seja MainActivity para poder utilizar o up navegation

/*    // metodo ativa a ToolBar
    protected void setUpToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }*/
}

