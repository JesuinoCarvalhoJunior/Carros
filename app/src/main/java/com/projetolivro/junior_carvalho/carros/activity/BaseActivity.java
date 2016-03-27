package com.projetolivro.junior_carvalho.carros.activity;


import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import livroandroid.lib.utils.NavDrawerUtil;
import com.projetolivro.junior_carvalho.carros.R;
import com.projetolivro.junior_carvalho.carros.fragments.CarroTabFragment;
import com.projetolivro.junior_carvalho.carros.fragments.CarrosFragment;
import com.projetolivro.junior_carvalho.carros.fragments.SiteLivroFragment;

/**
 * Created by Junior_Carvalho on 25/03/2016.
 */

// classe mae base para todas as outras
public class BaseActivity extends livroandroid.lib.activity.BaseActivity {

    protected DrawerLayout drawerLayout;

    // metodo ativa a ToolBar
    protected void setUpToolBar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
        }
    }

    // a configuracao do menu lateral é feito aqi na base activity para
    // que seja possivel reutilizar o codigo no projeto
    // configura nav drawer

    protected void setupNavDrawer() {
        final ActionBar actionBar = getSupportActionBar();
        // icone do menu do nav drawer

        actionBar.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);

     /*     if (navigationView != null && drawerLayout != null) {
           // Atualiza a imagem e textos do header
            NavDrawerUtil.setHeaderValues(navigationView,
                    R.id.containerNavDrawerListViewHeader,
                    R.drawable.nav_drawer_header,
                    R.drawable.ic_logo_user,
                    R.string.nav_drawer_username,
                    R.string.nav_drawer_email);*/

            if (navigationView != null && drawerLayout != null) {
               // Atualiza os dados do header do Navigation View
                setNavViewValues(navigationView,
                        R.string.nav_drawer_username,
                        R.string.nav_drawer_email,
                        R.drawable.jr);


                // trata evento de clique no menu
            navigationView.setNavigationItemSelectedListener(
                    new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(MenuItem menuItem) {
                            //seleciona linha
                            menuItem.setChecked(true);
                            // fecha o menu
                            drawerLayout.closeDrawers();
                            // trata o evento do menu
                            onNavDrawerItemSelected(menuItem);
                            return true;
                        }
                    });
        }
    }

    // Atualiza os dados do header do Navigation View
    // Adicionar no cabecalho - Imagem + Nome + Email
    public static void setNavViewValues(NavigationView navView, int nome, int email, int img) {
        View headerView = navView.getHeaderView(0);
        TextView tNome = (TextView) headerView.findViewById(R.id.tUserName);
                TextView tEmail = (TextView) headerView.findViewById(R.id.tUserEmail);
                ImageView imgView = (ImageView) headerView.findViewById(R.id.imgUserPhoto);
                tNome.setText(nome);
                tEmail.setText(email);
                imgView.setImageResource(img);
        }
// trata evento nos itens do menu lateral

    private void onNavDrawerItemSelected(MenuItem menuItem) {

        switch (menuItem.getItemId()) {

            case R.id.nav_item_carros_todos:
                // mostrar as 3 tabs classico, esportivos e luxo
                replaceFragment(new CarroTabFragment());
                toast("clicou em carros");
                break;
            case R.id.nav_item_carros_classicos:
                replaceFragment(CarrosFragment.newInstance(R.string.classicos));
                toast("carros classicos");
                break;
            case R.id.nav_item_carros_esportivos:
                replaceFragment(CarrosFragment.newInstance(R.string.esportivos));
                toast("carros esportivos");
                break;
            case R.id.nav_item_carros_luxo:
                replaceFragment(CarrosFragment.newInstance(R.string.luxo));
                toast("carros luxo");
                break;
            case R.id.nav_item_site_livro:
                replaceFragment(new SiteLivroFragment());
                snack(drawerLayout, "site livro");
                break;
            case R.id.nav_item_settings:
                toast("configuracoes");
                break;
               }
    }
    // adiciona o fragment ao cenro da tela
    // esse metodo encapsula o codigo para adicionar um fragment dinamicamente ao layout
    //replace = substituir o fragment
    protected void replaceFragment(Fragment frag){
        getSupportFragmentManager().beginTransaction().replace(R.id.container, frag, "TAG").commit();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //trata cique no botao qe abre o menu
                // o botao fica no canto superior esquerdo
                if (drawerLayout != null) {
                    openDrawer();
                    return true;
                }
        }
        return super.onOptionsItemSelected(item);
    }

    // abre o menu lateral
    protected void openDrawer() {
        if (drawerLayout != null) {
            drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    protected void closeDrawer() {
        if (drawerLayout != null) {
            drawerLayout.closeDrawer(GravityCompat.START);

        }
    }

}


