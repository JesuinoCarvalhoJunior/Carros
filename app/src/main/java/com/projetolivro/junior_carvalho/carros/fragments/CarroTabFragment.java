package com.projetolivro.junior_carvalho.carros.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.projetolivro.junior_carvalho.carros.R;
import com.projetolivro.junior_carvalho.carros.adapter.TabsAdapter;

import livroandroid.lib.fragment.BaseFragment;

/**
 * Created by Junior_Carvalho on 27/03/2016.
 */
public class CarroTabFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_carros_tab, container, false);

        //ViewPage
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewPager);
        // limite indica para viewpager manter 2 tabs a mais alem da qe esta sendo visualizda
        viewPager.setOffscreenPageLimit(2);
        //
        viewPager.setAdapter(new TabsAdapter(getContext(), getChildFragmentManager()));

        //TABs
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tablayout);
        //cria as tabes com o mesmo adapter utilisado pelo viewpager
        tabLayout.setupWithViewPager(viewPager);
        int cor = ContextCompat.getColor(getContext(), R.color.white);
        // cor do texto = branca,
        // cor de fundo azul foi definida no layout // "@color/primary"
        // ou poderia utiliar para cor do layout
        // tabLayout.setBackgroundColor(Color.BLUE);

        // primeiro parametro = cor do texto, segundo cor do texto referente aba selecionada
        //  tabLayout.setTabTextColors(Color.BLACK,Color.YELLOW);
        tabLayout.setTabTextColors(cor, cor);

        // muda a cor da barra inferior da tab selecionada
        //  tabLayout.setSelectedTabIndicatorColor(Color.GREEN);

        return view;
    }
}
