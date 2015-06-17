package com.strom.strongfit;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.strom.strongfit.adapters.AlimentosAdapter;
import com.strom.strongfit.db.DBOperations;
import com.strom.strongfit.models.Alimento;
import com.strom.strongfit.utils.RecyclerItemClickListener;

import java.util.ArrayList;


public class SearchAlimentosActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter alimentosAdapter;
    private static final String TAG = SearchAlimentosActivity.class.getSimpleName();
    private Toolbar toolbar;
    private DBOperations dbOperations;
    ArrayList<Alimento> alimentosArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_alimentos);
        setToolBar();
        alimentosArrayList = new ArrayList<Alimento>();
        dbOperations = new DBOperations(this);
        alimentosArrayList = dbOperations.getTodosAlimentos();

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
                        Alimento seleccionado = new Alimento();
                        seleccionado = alimentosArrayList.get(position);
                        Intent intent = new Intent(getApplicationContext(), AlimentoActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putInt("idAlimento", seleccionado.getAlimentoID());
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

}
