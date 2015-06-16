package com.strom.strongfit;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.strom.strongfit.adapters.AlimentosAdapter;
import com.strom.strongfit.models.Alimento;
import com.strom.strongfit.utils.RecyclerItemClickListener;

import java.util.ArrayList;


public class ResultadosBusquedaActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private static final String TAG = ResultadosBusquedaActivity.class.getSimpleName();
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter alimentosAdapter;
    ArrayList<Alimento> alimentosArrayList;
    //private DBOperations dbOperations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultados_busqueda);
        setToolBar();
        alimentosArrayList = new ArrayList<Alimento>();
        //dbOperations = new DBOperations(this);
        handleIntent(getIntent());

        //Se tiene que hacer la busqueda en la base de datos o en web
        Alimento taco = new Alimento();
        taco.setAlimentoID(1);
        taco.setName("Taco");
        taco.setCalories("70");
        alimentosArrayList.add(taco);

        Alimento melon = new Alimento();
        melon.setAlimentoID(2);
        melon.setName("Melon");
        melon.setCalories("90");
        alimentosArrayList.add(melon);

        alimentosAdapter = new AlimentosAdapter(alimentosArrayList, R.layout.row_alimento);
        mRecyclerView = (RecyclerView) findViewById(R.id.alimentos_recycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(alimentosAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener(){
                    @Override
                    public void onItemClick(View view, int position) {
                        //Cuando se da click se hace la busqueda en la base de datos o en web
                        Intent intent = new Intent(getApplicationContext(), AlimentoActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("nombreAlimento", "Alimento pasado");
                        bundle.putInt("idAlimento", 1);
                        bundle.putFloat("calorias", (float) 90.5);
                        bundle.putFloat("lipidos", (float) 777.0);
                        bundle.putFloat("proteinas", (float) 666.0);
                        bundle.putFloat("carbohidratos", (float) 90.0);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                }
        ));
    }

    public void setToolBar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search_alimentos, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));

        return true;
    }

    @Override
    protected void onNewIntent(Intent intent) {
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search aqui se hace la busqueda en la base de datos o en web
            //alimentosArrayList = dbOperations.getBusquedaDeAlimentos(query);
            Log.w(TAG, query);
        }
    }

}
