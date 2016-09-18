package com.projetolivro.junior_carvalho.carros.fragments;


import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.PopupMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.projetolivro.junior_carvalho.carros.CarrosApplication;
import com.projetolivro.junior_carvalho.carros.R;
import com.projetolivro.junior_carvalho.carros.activity.CarroActivity;
import com.projetolivro.junior_carvalho.carros.activity.MapaActivity;
import com.projetolivro.junior_carvalho.carros.activity.VideoActivity;
import com.projetolivro.junior_carvalho.carros.domain.BD.CarroDB;
import com.projetolivro.junior_carvalho.carros.domain.Carro;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

import livroandroid.lib.utils.IntentUtils;

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

        //popmenu
        view.findViewById(R.id.imgPlayVideo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showVideo(carro.urlVideo,v);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // atualiza a view do fragment com dados do carro

        setTextString(R.id.tDesc, carro.desc);

        final ImageView imgView = (ImageView) getView().findViewById(R.id.img);
        Picasso.with(getContext()).load(carro.urlFoto).fit().into(imgView);

        // Config Latitude e Logitude

        setTextString(R.id.tLatLng, String.format("Lat/Lng: %s/%s", carro.latitude, carro.longitude));

        // Adiciona o fragment ao Mapa
        MapaFragment mapaFragment = new MapaFragment();
        mapaFragment.setArguments(getArguments());
        getChildFragmentManager()
                .beginTransaction()
                .replace(R.id.mapFragment, mapaFragment)
                .commit();
    }

    //Menu de opções
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_frag_carro, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_edit) {
            //toast("Editar: " + carro.nome);
            EditarCarroDialog.show(getFragmentManager(), carro, new EditarCarroDialog.Callback() {
                @Override
                public void onCarroUpdated(Carro carro) {
                    toast("Carro [" + carro.nome + "] atualizado");
                    // Salva o carro
                    CarroDB db = new CarroDB(getContext());
                    db.save(carro);
                    // Atualiza o título com o novo nome
                    CarroActivity a = (CarroActivity) getActivity();
                    a.setTitle(carro.nome);
                    // Envia o evento/mensagem para o bus informando que precisa ser atualizada a lista
                    // a mensagem sera interceptada pela classe CarrosFragments
                    CarrosApplication.getInstance().getBus().post("refresh");
                }
            });
            return true;

        } else if (item.getItemId() == R.id.action_delete) {
            toast("Deletar " + carro.nome);
            DeletarCarroDialog.show(getFragmentManager(), new DeletarCarroDialog.Callback() {
                @Override
                public void onClickYes() {
                    toast("Carro [" + carro.nome + "] deletado.");
                    // Deleta o carro
                    CarroDB db = new CarroDB(getActivity());
                    db.delete(carro);
                    // Fecha a activity
                    getActivity().finish();
                    // Envia o evento para o bus informando que precisa ser atualizada a lista
                    // a mensagem sera interceptada pela classe CarrosFragments
                    CarrosApplication.getInstance().getBus().post("refresh");
                }
            });

            return true;
        } else if (item.getItemId() == R.id.action_share) {
            toast("Compartilhar");

        } else if (item.getItemId() == R.id.action_maps) {
            //abre outra activity para mosrar o mapa

            Intent intent = new Intent(getContext(), MapaActivity.class);
            intent.putExtra("carro", Parcels.wrap(carro));
            startActivity(intent);
            return true;
         //   toast("Mapa");
        } else if (item.getItemId() == R.id.action_video) {
         // URL do video
            final String url = carro.urlVideo;
            // le a view qe é a ancora do popup (é a view do botao do action bar)
            View  menuItemView = getActivity().findViewById(item.getItemId());
             if (menuItemView != null && url != null){
                 // mostra video
                 showVideo(url, menuItemView);
             }

            //toast("Video");
        }
        return super.onOptionsItemSelected(item);
    }

    // cria o popup posiciona na ancora
    private void showVideo(final String url, View ancoraView) {
        if(url != null && ancoraView != null) {
            // Cria o PopupMenu posicionado na âncora
            PopupMenu popupMenu = new PopupMenu(getActionBar().getThemedContext(), ancoraView);
            popupMenu.inflate(R.menu.menu_popup_video);

            popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    if (item.getItemId() == R.id.action_video_browser) {
                        // Abre o vídeo no browser
                        IntentUtils.openBrowser(getContext(), url);

                    } else if (item.getItemId() == R.id.action_video_player) {
                        // Abre o vídeo no Player de Vídeo Nativo
                        IntentUtils.showVideo(getContext(), url);

                    } else if (item.getItemId() == R.id.action_video_videoview) {
                        // Abre outra activity com VideoView
                        Intent intent = new Intent(getContext(), VideoActivity.class);
                        intent.putExtra("carro", Parcels.wrap(carro));
                        startActivity(intent);

                    }else if(item.getItemId() == R.id.action_maps){
                        Intent intent = new Intent(getContext(),MapaActivity.class);
                        startActivity(intent);


                    }
                    return true;
                }
            });
            popupMenu.show();
        }
    }
}
