package com.strom.strongfit.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.strom.strongfit.R;
import com.strom.strongfit.models.Alimento;
import com.strom.strongfit.models.Consumido;

import java.util.ArrayList;

/**
 * Created by USER on 16/06/2015.
 */
public class ConsumidosAdapter extends RecyclerView.Adapter<ConsumidosAdapter.ViewHolder> {
    ArrayList<Consumido> alimentosArrayList;
    int itemLayout;
    private static final String TAG = ConsumidosAdapter.class.getSimpleName();

    public ConsumidosAdapter(ArrayList<Consumido> data, int itemLayout){
        this.alimentosArrayList = data;
        this.itemLayout = itemLayout;
    }

    @Override
    public ConsumidosAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemLayoutView = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemLayoutView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ConsumidosAdapter.ViewHolder holder, int position) {
        Consumido alimento = alimentosArrayList.get(position);
        Log.d(TAG, "Nombre: " + alimento.getNombre());
        holder.alimento_nombre.setText(alimento.getNombre());
        holder.alimento_calorias.setText(alimento.getCalorias() + " kcal");
    }

    @Override
    public int getItemCount() {
        return alimentosArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView alimento_nombre;
        private TextView alimento_calorias;
        public ViewHolder(View itemView) {
            super(itemView);
            alimento_nombre = (TextView) itemView.findViewById(R.id.alimento_nombre);
            alimento_calorias = (TextView) itemView.findViewById(R.id.alimento_calorias);
        }
    }
}
