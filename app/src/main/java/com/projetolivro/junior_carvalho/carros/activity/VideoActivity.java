package com.projetolivro.junior_carvalho.carros.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.NavUtils;
import android.view.MenuItem;

import com.projetolivro.junior_carvalho.carros.R;
import com.projetolivro.junior_carvalho.carros.domain.Carro;
import com.projetolivro.junior_carvalho.carros.fragments.VideoFragment;

import org.parceler.Parcels;

/**
 * Created by Junior_Carvalho on 05/06/2016.
 */


public class VideoActivity extends BaseActivity {

    private Carro carro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        // Configura a Toolbar como a action bar

        setUpToolBar();
        carro = Parcels.unwrap(getIntent().getParcelableExtra("carro"));
        getSupportActionBar().setTitle(carro.nome);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (savedInstanceState == null) {
            // Adiciona o fragment ao layout da activity
            VideoFragment videoFragment = new VideoFragment();
            videoFragment.setArguments(getIntent().getExtras());
            getSupportFragmentManager().beginTransaction().replace(R.id.fragLayout,
                    videoFragment).commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent intent = NavUtils.getParentActivityIntent(getActivity());
                intent.putExtra("carro", Parcels.wrap(carro));
                NavUtils.navigateUpTo(getActivity(), intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
