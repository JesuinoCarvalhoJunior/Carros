package com.projetolivro.junior_carvalho.carros.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.projetolivro.junior_carvalho.carros.R;
import com.projetolivro.junior_carvalho.carros.fragments.AboutDialog;
import com.projetolivro.junior_carvalho.carros.fragments.CarroTabFragment;
import com.projetolivro.junior_carvalho.carros.fragments.CarrosFragment;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //ativa toolbar
        setUpToolBar();
        //configura active para usar nav drawer
        setupNavDrawer();
        // iniciliza o aplicativo e adicionar lista de carros
       //  replaceFragment(CarrosFragment.newInstance(R.string.carros));

        // exibir as 3 tabs ao inicializar
        replaceFragment(new CarroTabFragment());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        if (id == R.id.action_about){
            AboutDialog.ShowAbout(getSupportFragmentManager());
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
