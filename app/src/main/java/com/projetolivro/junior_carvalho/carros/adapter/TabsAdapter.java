package com.projetolivro.junior_carvalho.carros.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.TabHost;

import com.projetolivro.junior_carvalho.carros.R;
import com.projetolivro.junior_carvalho.carros.fragments.CarrosFragment;

/**
 * Created by Junior_Carvalho on 27/03/2016.
 * Fornece conteudo para criar as tbas e paginas do pageview
 */
public class TabsAdapter extends FragmentPagerAdapter {

    private Context context;

    public TabsAdapter(Context context, FragmentManager fm) {
        super(fm);
        this.context = context;
    }

    // retorna 3 fragments que precisa ser confgurado no viewpager
    @Override
    public Fragment getItem(int position) {
        Fragment f = null;

        if (position == 0) {
            f = CarrosFragment.newInstance(R.string.classicos);
        } else if (position == 1) {
            f = CarrosFragment.newInstance(R.string.esportivos);
        } else
            f = CarrosFragment.newInstance(R.string.luxo);
        return f;
    }

    @Override
    public int getCount() {
        return 3; //0;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getString(R.string.classicos);
        } else if (position == 1){
            return context.getString(R.string.esportivos);
        }else
            return  context.getString(R.string.luxo);
    }


}
