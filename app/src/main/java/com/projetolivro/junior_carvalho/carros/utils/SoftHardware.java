package com.projetolivro.junior_carvalho.carros.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.projetolivro.junior_carvalho.carros.R;

/**
 * Created by Junior_Carvalho on 12/04/2016.
 */
public class SoftHardware extends Activity implements DialogInterface.OnClickListener {
    public SoftHardware() {

    }


    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
    }

    public static void showStringDialog(Context context, CharSequence title, CharSequence
            message) {
        // AlertDialog.Builder msg = new AlertDialog.Builder(context);
        new AlertDialog.Builder(context).
                setTitle("Informações").
                setIcon(R.drawable.ic_info_black_36dp).
                setMessage(message).
                setNeutralButton("Ok", new SoftHardware()).show();
    }

/*    public void onClick(DialogInterface dialog, int which) {
        dialog.dismiss();
    }*/

    public void sairAplicacao() {
        //- ACTION_MAIN com a categoria CATEGORY_HOME = Lançamento da tela inicial.
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        //Se definido, esta atividade se tornará o começo de uma nova tarefa nesta pilha de histórico.
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public static void alertDialog1(final Context context, final String title, final String mensagem) {
        try {

            android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(context);
            //   android.support.v7.app.AlertDialog dialog = new android.support.v7.app.AlertDialog.Builder(context).


            //  AlertDialog dialog = new AlertDialog.Builder(context).
            alertDialogBuilder.setTitle(title);
            alertDialogBuilder.setMessage(mensagem);
            alertDialogBuilder.setCancelable(false);
            alertDialogBuilder.setIcon(R.drawable.ic_info_black_36dp);
            alertDialogBuilder.create();

            alertDialogBuilder.setPositiveButton("Sim",
                    new DialogInterface.OnClickListener() {
                      //   public void onClick(DialogInterface dialog, int id) {
                       @Override
                        public void onClick(DialogInterface dialog, int which) {
                             SoftHardware sh = new SoftHardware();
                             sh.sairAplicacao();
                        }
                    })
                    .setNegativeButton("Não",
                            new DialogInterface.OnClickListener() {
                                //   public void onClick(DialogInterface dialog, int id) {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    dialog.cancel();
                                }
                            });

            android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } catch (Exception e) {
            //  Log.e(TAG, e.getMessage(), e);
        }
    }


}