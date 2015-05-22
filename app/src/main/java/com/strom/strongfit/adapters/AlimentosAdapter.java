package com.strom.strongfit.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.strom.strongfit.R;
import com.strom.strongfit.models.Alimento;

import java.util.ArrayList;

/**
 * Created by USER on 21/05/2015.
 */
public class AlimentosAdapter extends RecyclerView.Adapter<AlimentosAdapter.ViewHolder> {
    ArrayList<Alimento> alimentos;
    int itemLayout;
    private static final String TAG = AlimentosAdapter.class.getSimpleName();
    private static final int TYPE_ADD = 0;
    private static final int TYPE_DELETE = 1;

    public AlimentosAdapter(ArrayList<Alimento> alimentos, int itemLayout) {
        this.alimentos = alimentos;
        this.itemLayout = itemLayout;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Alimento alimento = alimentos.get(position);
        Log.d(TAG, "-----------Entro al Bind------------");
        Log.d(TAG, "Nombre: " + alimento.getName());
        holder.alimento_nombre.setText(alimento.getName());
        holder.alimento_calorias.setText(alimento.getCalories());
        holder.alimento_id.setText(String.valueOf(alimento.getAlimentoID()));
    }

    @Override
    public int getItemCount() {
        return alimentos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView alimento_nombre;
        private TextView alimento_calorias;
        private TextView alimento_id;

        public ViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "-----------Entro al constructor Holder------------");
            alimento_nombre = (TextView) itemView.findViewById(R.id.alimento_nombre);
            alimento_calorias = (TextView) itemView.findViewById(R.id.alimento_calorias);
            alimento_id = (TextView) itemView.findViewById(R.id.alimento_id);
        }
    }
}
