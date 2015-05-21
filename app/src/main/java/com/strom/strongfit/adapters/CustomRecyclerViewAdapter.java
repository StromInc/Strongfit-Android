package com.strom.strongfit.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.strom.strongfit.R;

/**
 * Created by Alumno on 20/05/2015.
 */
public class CustomRecyclerViewAdapter extends RecyclerView.Adapter<CustomRecyclerViewAdapter.ViewHolder>{

    private String titles[];
    private int iconos[];
    private String nombre;
    private int perfilImagen;
    private String peso;
    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    static final String TAG = CustomRecyclerViewAdapter.class.getSimpleName();
    private Context _context;
    //Primero recivimos los datos
    public CustomRecyclerViewAdapter(String[] titles, String nombre, String peso, int perfilImagen, int[] iconos, Context mContext) {
        this.titles = titles;
        this.nombre = nombre;
        this.peso = peso;
        this.perfilImagen = perfilImagen;
        this.iconos = iconos;
        this._context = mContext;
    }
    //Este metodo se ejecuta para crear la vista
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        if (i == TYPE_ITEM) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.drawer_list_item, viewGroup, false); //Infla la lista
            ViewHolder vhItem = new ViewHolder(view, i, _context); //Le pasamos los valores a nuestro Holder (la vista y el tipo)

            return vhItem;
        } else if (i == TYPE_HEADER) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.header_paciente, viewGroup, false); //Infla el header
            ViewHolder vhHeader = new ViewHolder(view, i, _context);

            return vhHeader;
        }
        return null;
    }
    //Este se ejecuta cuando se va a mostar los elementos
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        if(viewHolder.holderID == 1) {
            viewHolder.drawer_item_text.setText(titles[i - 1]); // Titulos
            viewHolder.drawer_item_icon.setImageResource(iconos[i -1]);// Iconos
        }
        else{
            // Estos son los elemtos del header
            viewHolder.nombre_text.setText(nombre);
            viewHolder.peso_text.setText(peso);
            viewHolder.profile_image.setImageResource(perfilImagen);
        }
    }

    @Override
    public int getItemCount() {
        return titles.length + 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(isPositionHeader(position)){
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position){
        return position == TYPE_HEADER;
    }
    //Usamos un Holder para solo inicializar una vez cada elemento
    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        int holderID;
        private TextView nombre_text;
        private TextView peso_text;
        private TextView drawer_item_text;
        private ImageView profile_image;
        private ImageView drawer_item_icon;
        private Context contexto;

        public ViewHolder(View itemView, int viewType, Context c) {
            super(itemView);
            this.contexto = c;
            if(viewType == TYPE_ITEM){
                drawer_item_text = (TextView) itemView.findViewById(R.id.drawer_item_text);
                drawer_item_icon = (ImageView) itemView.findViewById(R.id.drawer_item_icon);
                holderID = 1;
            }else{
                nombre_text = (TextView) itemView.findViewById(R.id.nombre_text);
                peso_text = (TextView) itemView.findViewById(R.id.peso_text);
                profile_image = (ImageView) itemView.findViewById(R.id.profile_image);
                holderID = 0;
            }
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(contexto, "Apretaste: " + getPosition(), Toast.LENGTH_SHORT).show();
        }
    }
}
