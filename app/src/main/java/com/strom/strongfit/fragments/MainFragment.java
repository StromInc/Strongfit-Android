package com.strom.strongfit.fragments;


import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.strom.strongfit.R;
import com.strom.strongfit.adapters.ConsumidosAdapter;
import com.strom.strongfit.models.Consumido;
import com.strom.strongfit.utils.ConectarHTTP;
import com.strom.strongfit.utils.RecyclerItemClickListener;
import com.strom.strongfit.utils.SessionManager;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment{
    private static final String TAG = MainFragment.class.getSimpleName();
    ArrayList<Consumido> alimentos;
    private int idPaciente = 0;
    private SessionManager sessionManager; //Para las sesiones
    private ConectarHTTP conectarHTTP;
    private int idAlta = 0;
    int dia = 0;
    int month = 0;
    int year = 0;
    float caloriasConsumidas;
    BorrarAlimentosTask borrar;
    RecyclerView recyclerView;
    AlertDialog.Builder dialogo;
    private TextView textCaloriasConsumidas;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.v(TAG, "onResume");
        GetConsumidosTask consu = new GetConsumidosTask();
        consu.execute();
        actualizarCalorias();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView");

        View view = inflater.inflate(R.layout.fragment_main, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.i(TAG, "onActivityCreated");
        alimentos = new ArrayList<Consumido>();
        conectarHTTP = new ConectarHTTP();
        sessionManager = new SessionManager(getActivity());
        Calendar cal = Calendar.getInstance();
        dia = cal.get(Calendar.DATE);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        idPaciente = sessionManager.getIDPaciente();
        textCaloriasConsumidas = (TextView) getActivity().findViewById(R.id.text_consumo);

        recyclerView = (RecyclerView) getActivity().findViewById(R.id.consumidos_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity(),
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Consumido seleccionado = alimentos.get(position);
                        idAlta = seleccionado.getIdAlta();
                        dialogo = new AlertDialog.Builder(getActivity());
                        dialogo.setTitle(R.string.titulo_borrar_alimento);
                        dialogo.setCancelable(false);
                        dialogo.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                borrar = new BorrarAlimentosTask();
                                borrar.execute();
                            }
                        });
                        dialogo.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Log.i(TAG, "No se borro");
                            }
                        });
                        dialogo.show();
                    }
                }
        ));
    }
    public void actualizarCalorias(){
        for(Consumido consumido : alimentos){
            caloriasConsumidas += Float.parseFloat(consumido.getCalorias());
        }
        Log.i(TAG, "Calorias totales" + caloriasConsumidas);
        textCaloriasConsumidas.setText("Calorias consumidas: " + caloriasConsumidas + " kcal");
    }

    public void updateList(ArrayList<Consumido> consumidos){
        recyclerView.setAdapter(new ConsumidosAdapter(consumidos, R.layout.row_alimento));
        actualizarCalorias();
    }

    public class BorrarAlimentosTask extends AsyncTask<Object, Void, String>{
        private ProgressDialog progressDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(getActivity());
            progressDialog.setTitle("Espera un momento");
            progressDialog.setMessage("Se esta eliminado el alimento");
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Object... params) {
            String respuesta = conectarHTTP.borrarAlimento(idAlta);
            if(respuesta.equals("ok")){
                alimentos = conectarHTTP.getAlimentosFecha(dia, month, year, idPaciente);
            }
            return respuesta;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            if(s.equals("ok")){
                Toast.makeText(getActivity(), "Se elimino el alimento", Toast.LENGTH_SHORT).show();
                updateList(alimentos);
            }else{
                Toast.makeText(getActivity(), "No se pudo eliminar,\n intentelo mas tarde", Toast.LENGTH_LONG).show();
            }
        }
    }
    public class GetConsumidosTask extends AsyncTask<Object, Void, ArrayList<Consumido>>{

        @Override
        protected ArrayList<Consumido> doInBackground(Object... params) {
            alimentos = conectarHTTP.getAlimentosFecha(dia, month, year, idPaciente);
            return alimentos;
        }
        @Override
        protected void onPostExecute(ArrayList<Consumido> consumidos) {
            super.onPostExecute(consumidos);
            updateList(consumidos);
        }
    }
}
