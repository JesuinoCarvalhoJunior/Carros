package com.projetolivro.junior_carvalho.carros.activity;

import android.app.backup.BackupManager;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.projetolivro.junior_carvalho.carros.R;
import com.projetolivro.junior_carvalho.carros.adapter.TabsAdapter;
import com.projetolivro.junior_carvalho.carros.fragments.AboutDialog;
import com.projetolivro.junior_carvalho.carros.utils.GetTypeNetwork;
import com.projetolivro.junior_carvalho.carros.utils.PrefsUtils;

import livroandroid.lib.utils.Prefs;

public class MainActivity extends BaseActivity {

    private BackupManager backupManager;

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
        // naao precisa mais
        //  replaceFragment(new CarroTabFragment());

        //exibir tabs
        setupViewPagerTabs();

        FloatingActionButton fa = (FloatingActionButton) findViewById(R.id.fab);
        fa.setBackgroundColor(Color.BLUE);


        // FAB
        final boolean chk = PrefsUtils.isCheckPushOn(this);
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (chk) {
                    snack(v, "As notificações estão habilitadas!");
                } else

                    snack(v, "Nenhum evento implementado!");
            }
        });


        Log.d("tag", "getfiledir > : " + getFilesDir());
        Log.d("tag", "getfiledir > : " + getFileStreamPath("arquivo.txt"));
        Log.d("tag", "getfiledir > : " + getExternalFilesDir(Environment.DIRECTORY_DCIM));
        Log.d("tag", "getfiledir > : " + getCacheDir());

        // gerenciador de BACKUP
        // faz com que sempre que alguma preferencia for salva, entao faz bkp
        backupManager = new BackupManager(getContext());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_about) {
            AboutDialog.showAbout(getSupportFragmentManager());
            return true;
        }
        if (id == R.id.type_network) {

            final boolean chkType = PrefsUtils.isCheckTypeNetWork(this);
            if (!chkType) {
                toast("Serviço não habilitado!");
            } else
                toast(GetTypeNetwork.getTypeNetwork(this));
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    // configura TABs e ViewPager
    private void setupViewPagerTabs() {

        //ViewPager
        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        // limite indica para viewpager manter 2 tabs a mais alem da qe esta sendo visualizda
        viewPager.setOffscreenPageLimit(2);
        //
        viewPager.setAdapter(new TabsAdapter(getContext(), getSupportFragmentManager()));

        //TABs
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        //cria as tabes com o mesmo adapter utilisado pelo viewpager
        tabLayout.setupWithViewPager(viewPager);
        int cor = ContextCompat.getColor(getContext(), R.color.white);
        // cor do texto = branca,
        // cor de fundo azul ou (Laranja) foi definida no layout // "@color/primary"
        // ou poderia utiliar para cor do layout
        // tabLayout.setBackgroundColor(Color.BLUE);

        // primeiro parametro = cor do texto, segundo cor do texto referente aba selecionada
        //  tabLayout.setTabTextColors(Color.BLACK,Color.YELLOW);
        tabLayout.setTabTextColors(cor, cor);
        // muda a cor da barra inferior da tab selecionada
        //   tabLayout.setSelectedTabIndicatorColor(Color.GREEN);


        //region Description - Configura a utlima tab selecionada pelo usuario
        int tabIdx = Prefs.getInteger(getContext(), "tabIdx");
        viewPager.setCurrentItem(tabIdx);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //salva o indice da pagina/tab selecionada
                Prefs.setInteger(getContext(), "tabIdx", viewPager.getCurrentItem());

                // faz o bkp da preferencia
                backupManager.dataChanged();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }


        });
        //endregion
    }


}
