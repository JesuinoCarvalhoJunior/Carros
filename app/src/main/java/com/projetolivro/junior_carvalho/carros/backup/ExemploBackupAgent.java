package com.projetolivro.junior_carvalho.carros.backup;

import android.app.backup.BackupAgentHelper;
import android.app.backup.BackupDataInput;
import android.app.backup.BackupDataOutput;
import android.app.backup.FileBackupHelper;
import android.app.backup.SharedPreferencesBackupHelper;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.IOException;

import livroandroid.lib.utils.Prefs;

/**
 * Created by Junior_Carvalho on 26/04/2016.
 */
public class ExemploBackupAgent extends BackupAgentHelper {

    @Override
    public void onCreate() {
        //cria helpe. Fara o bkp dos dados utilizando
        //a chave Prefs.PREF_ID

        // arquivo preferencia
        SharedPreferencesBackupHelper helper = new SharedPreferencesBackupHelper(this, Prefs.PREF_ID);

        // arquivo da memeoria itnerna
        FileBackupHelper file = new FileBackupHelper(this, "carros_clasicos.json");

        //adiciona o helper ao agente de bkps de a preferencia e arquivo da mem interna
        addHelper("livroandroid", helper);
        addHelper("livroandroid", file);

    }

    @Override
    public void onBackup(ParcelFileDescriptor oldState, BackupDataOutput data,
                         ParcelFileDescriptor newState) throws IOException {
        super.onBackup(oldState, data, newState);
        Log.d("backup", "BKP realizado com sucesso!");
    }

    @Override
    public void onRestore(BackupDataInput data, int appVersionCode, ParcelFileDescriptor newState)
            throws IOException {
        super.onRestore(data, appVersionCode, newState);
        Log.d("backup", "RESTORE realizado com sucesso!");
    }

}
