package com.projetolivro.junior_carvalho.carros.activity.prefs;

import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.projetolivro.junior_carvalho.carros.R;

@SuppressWarnings("deprecation")
public class ConfiguracoesActivity extends PreferenceActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
   //     setContentView(R.layout.activity_configuracoes);
        //Carrega as configuacoes
        addPreferencesFromResource(R.xml.preferences);

    }
}
