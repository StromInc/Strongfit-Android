package com.strom.strongfit.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.strom.strongfit.R;

/**
 * Created by Alumno on 20/05/2015.
 */
public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.ViewHolder>{

    private String titles[];
    private String nombre;
    private String peso;

    public CustomRecyclerViewAdapter(String[] titles, String nombre, String peso) {
        this.titles = titles;
        this.nombre = nombre;
        this.peso = peso;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawer_list_item, viewGroup, false);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return titles.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView nombre_text;
        private TextView peso_text;
        private TextView item_text;
        public ViewHolder(View itemView) {
            super(itemView);
            nombre_text = (TextView) itemView.findViewById(R.id.nombre_text);
            peso_text = (TextView) itemView.findViewById(R.id.peso_text);
            item_text = (TextView) itemView.findViewById(R.id.item_text);
        }
    }
}
