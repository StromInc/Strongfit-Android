package com.strom.strongfit.fragments;


import android.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.strom.strongfit.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddAlimentoDialogFragment extends DialogFragment {

    public static AddAlimentoDialogFragment newInstance(String title){
        AddAlimentoDialogFragment frag = new AddAlimentoDialogFragment();
        Bundle args = new Bundle();
        args.putString("titulo", title);
        frag.setArguments(args);
        return frag;
    }

    //@Nullable
    //cada campo tambien tenia el @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_alimento_dialog, container);
        String title = getArguments().getString("titulo", "Texto por defecto");
        getDialog().setTitle(title);
        return view;
    }
}
