package com.projetolivro.junior_carvalho.carros.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.projetolivro.junior_carvalho.carros.R;
import com.projetolivro.junior_carvalho.carros.domain.Carro;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarroFragment extends BaseFragment {

    private  Carro carro;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_carro, container, false);
        // classe carro nao extends mais de implementa mais Serializable
        // e sim Parcelable
        //carro = (Carro) getArguments().getSerializable("carro");
        // usando Parcelable
        //carro =  getArguments().getParcelable("carro");

        //usando Parcels na dependencia
        carro = Parcels.unwrap(getArguments().getParcelable("carro"));
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // atualiza a view do fragment com dados do carro

        setTextString(R.id.tDesc, carro.desc);

        final ImageView imgView = (ImageView) getView().findViewById(R.id.img);
        Picasso.with(getContext()).load(carro.urlFoto).fit().into(imgView);
    }

}
