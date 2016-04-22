package com.projetolivro.junior_carvalho.carros.activity.prefs;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import com.projetolivro.junior_carvalho.carros.R;

/**
 * V11 pq é compativel com andoird 3.0 e API Lvel 11 ou superior
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class ConfiguracoesV11Activity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //
        getActionBar().setDisplayHomeAsUpEnabled(true);
        //adicionar o fragment a confgiruacao
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(android.R.id.content, new PrefsFragment());
        ft.commit();
    }

    public static class PrefsFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            //carregfa as configuracoes
            addPreferencesFromResource(R.xml.preferences);
        }
    }
}
