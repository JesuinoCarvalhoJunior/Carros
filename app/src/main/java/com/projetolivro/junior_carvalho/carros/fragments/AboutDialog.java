package com.projetolivro.junior_carvalho.carros.fragments;


import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.widget.TextView;

import com.projetolivro.junior_carvalho.carros.R;

import livroandroid.lib.utils.AndroidUtils;

/**
 * Created by Junior_Carvalho on 26/03/2016.
 */
public class AboutDialog extends DialogFragment {

    // metodo utilitario para mostrar o dialog

    public static void showAbout(android.support.v4.app.FragmentManager fm) {
        FragmentTransaction ft = fm.beginTransaction();
        Fragment prev = fm.findFragmentByTag("dialog_about");

        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        new AboutDialog().show(ft, "dialog_about");
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        //cria html com texto de sobre o aplicaivo
        SpannableStringBuilder aboutBody = new SpannableStringBuilder();

        //versoa do aplicativo
        String versionName = AndroidUtils.getVersionName(getActivity());

        //converte o texto do string.xml para HTML
        aboutBody.append(Html.fromHtml(getString(R.string.about_dialog_text, versionName)));

        //infla o layout

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        TextView view = (TextView) inflater.inflate(R.layout.dialog_about, null);
        view.setText(aboutBody);
        view.setMovementMethod(new LinkMovementMethod());

        //cria o dialog customizado
        return new AlertDialog.Builder(getActivity()).
                setTitle(R.string.about_dialog_title).
                setView(view).
                setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                            // @Override
                            public void onClick(DialogInterface dialog, int whichButton) {
                                dialog.dismiss();
                            }
                        }
                ).create();

    }

}
