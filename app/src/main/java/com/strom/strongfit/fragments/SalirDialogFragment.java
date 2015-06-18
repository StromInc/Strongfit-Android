package com.strom.strongfit.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.strom.strongfit.R;

/**
 * Created by USER on 17/06/2015.
 */
//esto crea el dialogo
public class SalirDialogFragment extends DialogFragment{
    public interface DialogListener{
        public void onDialogPositiveClick(DialogFragment dialog);
        public void onDialogNegativeClick(DialogFragment dialog);
    }
    DialogListener listener;
    int titulo = R.string.app_name;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try{
            listener = (DialogListener)activity;
        }catch (ClassCastException e){

        }
    }
    public void setTitulo(int titulo){
        this.titulo = titulo;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        //Si le dio si
        builder.setTitle(titulo)
                .setPositiveButton("Si, estoy seguro", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onDialogPositiveClick(SalirDialogFragment.this);
                    }
                }) //Si le dio no
                .setNegativeButton("No lo estoy", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        listener.onDialogNegativeClick(SalirDialogFragment.this);
                    }
                });
        return builder.create();
    }
}
