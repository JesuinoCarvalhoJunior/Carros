package com.projetolivro.junior_carvalho.carros.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.projetolivro.junior_carvalho.carros.R;
import com.projetolivro.junior_carvalho.carros.activity.CarroActivity;
import com.projetolivro.junior_carvalho.carros.domain.BD.CarroDB;
import com.projetolivro.junior_carvalho.carros.domain.Carro;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

/**
 * A simple {@link Fragment} subclass.
 */
public class CarroFragment extends BaseFragment {

    private Carro carro;

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

        // informa ao android que este fragment contem MENU = menu_frag_carro.xml
        setHasOptionsMenu(true);

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_frag_carro, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_edit) {
//            toast("Editar " + carro.nome);

            EditarCarroDialog.show(getFragmentManager(), carro, new EditarCarroDialog.Callback() {
                @Override
                public void onCarroUpdated(Carro carro) {
                    toast("Carro [" + carro.nome + "] atualizado.");
                    // Salva o carro depois de fechar o dialog
                    CarroDB db = new CarroDB(getContext());
                    db.save(carro);
                    // atualiza o titulo com o novo nome
                    CarroActivity ca = (CarroActivity) getActivity();
                    ca.setTitle(carro.nome);

                }
            });
            return true;

        } else if (item.getItemId() == R.id.action_delete) {
            toast("Deletar " + carro.nome);
            return true;
        } else if (item.getItemId() == R.id.action_share) {
            toast("Compartilhar");

        } else if (item.getItemId() == R.id.action_maps) {
            toast("Mapa");
        } else if (item.getItemId() == R.id.action_video) {
            toast("Video");
        }
        return super.onOptionsItemSelected(item);
    }
}
