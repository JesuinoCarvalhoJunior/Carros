package com.projetolivro.junior_carvalho.carros.utils;

import android.content.DialogInterface;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.projetolivro.junior_carvalho.carros.R;

import java.io.IOException;
import java.io.InputStream;

public class Exibe_Perc_Bateria extends AppCompatActivity {
    private static final String SETTING_PERCENT = "status_bar_show_battery_percent";

    protected void onCreate(Bundle savedInstanceState) {
        boolean z = true;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exibe__perc__bateria);


        //  ((TextView) findViewById(C0002R.id.txtFaq)).setText(Html.fromHtml(getString(C0002R.string.faq)));
        CheckBox cbBatPercent = (CheckBox) findViewById(R.id.checkBatPercent);

        if (Settings.System.getInt(getApplicationContext().getContentResolver(), SETTING_PERCENT, 0) != 1) {
            z = false;
        }
        cbBatPercent.setChecked(z);
        cbBatPercent.setOnCheckedChangeListener(new C00001());
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        //    getMenuInflater().inflate(C0002R.menu.mnu_main, menu);
        return true;
    }

/*
    public boolean onOptionsItemSelected(MenuItem item) {
 switch (item.getItemId()) {
            case C0002R.id.mnuTranslators:
                return mnuTranslators();
            case C0002R.id.mnuInfo:
                return mnuInfo();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
*/

    private boolean mnuInfo() {
        StringBuffer buf = new StringBuffer();
        buf.append("<b>VERSION.RELEASE</b><br />" + Build.VERSION.RELEASE + "<br />");
        buf.append("<b>VERSION.INCREMENTAL</b><br />" + Build.VERSION.INCREMENTAL + "<br />");
        buf.append("<b>VERSION.SDK_INT</b><br />" + Build.VERSION.SDK_INT + "<br />");
        buf.append("<b>BOARD</b><br />" + Build.BOARD + "<br />");
        buf.append("<b>BRAND</b><br />" + Build.BRAND + "<br />");
        buf.append("<b>DEVICE</b><br />" + Build.DEVICE + "<br />");
        buf.append("<b>FINGERPRINT</b><br />" + Build.FINGERPRINT + "<br />");
        buf.append("<b>HOST</b><br />" + Build.HOST + "<br />");
        buf.append("<b>ID</b><br />" + Build.ID + "<br />");
        showStringDialog("System Info", Html.fromHtml(buf.toString()));
        return true;
    }
/*

    private boolean mnuTranslators() {
        String raw = getRawResource(C0002R.raw.translators);
        if (raw == null) {
            return false;
        }
        showStringDialog("Translators", Html.fromHtml(raw));
        return true;
    }
*/

/*    private String getRawResource(int id) {
        try {
            InputStream in_s = getResources().openRawResource(id);
            byte[] b = new byte[in_s.available()];
            in_s.read(b);
            return new String(b);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }*/

    private void showStringDialog(CharSequence title, CharSequence message) {
/*        new Builder(this).setIcon(17301659).setMessage(message).
                setNeutralButton(17039370, new C00012()).show();*/
    }

    /* renamed from: de.kroegerama.android4batpercent.ActMain.1 */
    class C00001 implements CompoundButton.OnCheckedChangeListener {
        C00001() {
        }



        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked) {
                Settings.System.putInt(Exibe_Perc_Bateria.this.getApplicationContext().getContentResolver(), Exibe_Perc_Bateria.SETTING_PERCENT, 1);
                if (Settings.System.getInt(Exibe_Perc_Bateria.this.getApplicationContext().getContentResolver(), Exibe_Perc_Bateria.SETTING_PERCENT, 0) == 1) {
                    Toast.makeText(Exibe_Perc_Bateria.this, "Ok 0", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Toast.makeText(Exibe_Perc_Bateria.this, "Erro", Toast.LENGTH_SHORT).show();
                    return;
                }
            }

            Settings.System.putInt(Exibe_Perc_Bateria.this.getApplicationContext().getContentResolver(), Exibe_Perc_Bateria.SETTING_PERCENT, 0);
            if (Settings.System.getInt(Exibe_Perc_Bateria.this.getApplicationContext().getContentResolver(), Exibe_Perc_Bateria.SETTING_PERCENT, 1) == 0) {
                Toast.makeText(Exibe_Perc_Bateria.this, "Ok 1", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(Exibe_Perc_Bateria.this, "Erro", Toast.LENGTH_SHORT).show();
            }
        }
    }



    //<editor-fold desc="Description">
    /* renamed from: de.kroegerama.android4batpercent.ActMain.2 */
    class C00012 implements DialogInterface.OnClickListener {
        C00012() {
        }

        public void onClick(DialogInterface dialog, int which) {
            dialog.dismiss();
        }
    }
    //</editor-fold>
}
