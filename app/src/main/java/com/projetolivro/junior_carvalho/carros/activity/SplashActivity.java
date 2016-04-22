package com.projetolivro.junior_carvalho.carros.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;

import com.projetolivro.junior_carvalho.carros.R;
import com.projetolivro.junior_carvalho.carros.utils.PermissionUtils;

import livroandroid.lib.utils.AlertUtils;

/**
 * Splash para listar as permissões.
 */
public class SplashActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

   /*     // Lista de permissões necessárias.
        String permissions[] = new String[]{
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.CAMERA,
                Manifest.permission.READ_CALENDAR,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_CONTACTS

        };

        // Valida lista de permissões.
        boolean ok = PermissionUtils.validate(this, 0, permissions);*/

        // Valida lista de permissões.
        String permissions[] = getResources().getStringArray(R.array.array_permission);

        boolean ok = PermissionUtils.validate(this, 0, permissions);


        if (ok) {
            // Tudo OK, pode entrar.
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }





    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        for (int result : grantResults) {
            if (result == PackageManager.PERMISSION_DENIED) {
                // Negou a permissão. Mostra alerta e fecha.
                AlertUtils.alert(getContext(), R.string.app_name, R.string.msg_alerta_permissao, R.string.ok,  new Runnable() {
                    @Override
                    public void run() {
                        // Negou permissão. Sai do app.
                        finish();
                    }
                });
                return;
            }
        }

        // Permissões concedidas, pode entrar.
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}
