package com.strom.strongfit.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.strom.strongfit.R;
import com.strom.strongfit.models.Alimento;

import java.util.ArrayList;

/**
 * Created by USER on 21/05/2015.
 */
//Esta cosa hace magia
public class AlimentosAdapter extends RecyclerView.Adapter<AlimentosAdapter.ViewHolder> {
    ArrayList<Alimento> alimentos;
    int itemLayout;
    private static final String TAG = AlimentosAdapter.class.getSimpleName();
    //Pasamos la data y el layout
    public AlimentosAdapter(ArrayList<Alimento> alimentos, int itemLayout) {
        this.alimentos = alimentos;
        this.itemLayout = itemLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(view);
    }
    //Esto mete los datos
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Alimento alimento = alimentos.get(position);
        Log.d(TAG, "Nombre: " + alimento.getName());
        holder.alimento_nombre.setText(alimento.getName());
        holder.alimento_calorias.setText(alimento.getCalories() + " kcal");
    }

    @Override
    public int getItemCount() {
        return alimentos.size();
    }
    //Esta madre es para no tener que hacer un chingo de inicializaciones para cada campo, solo
    //Se hace una y se le meten datos
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView alimento_nombre;
        private TextView alimento_calorias;

        public ViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "-----------Entro al constructor Holder------------");
            alimento_nombre = (TextView) itemView.findViewById(R.id.alimento_nombre);
            alimento_calorias = (TextView) itemView.findViewById(R.id.alimento_calorias);
        }
    }
}
