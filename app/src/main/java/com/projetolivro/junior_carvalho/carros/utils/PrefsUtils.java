package com.projetolivro.junior_carvalho.carros.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Junior_Carvalho on 13/04/2016.
 */
public class PrefsUtils {

    /**
    * verifica se o usuario marcou o checkbox de Push On nas condiguracoes
    * a tela de preferencia esta em res/xml/preferences.xml
    */

    public static boolean isCheckPushOn(final Context context){
        SharedPreferences sp  = PreferenceManager.getDefaultSharedPreferences(context);
        return sp.getBoolean("PREF_CHECK_PUSH", false);
    }


    public static boolean isCheckTypeNetWork(final Context context){
        SharedPreferences spref  = PreferenceManager.getDefaultSharedPreferences(context);
        return spref.getBoolean("PREF_TYPE_NETWORK", false);
    }


}
