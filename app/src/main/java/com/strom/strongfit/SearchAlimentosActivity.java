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
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.strom.strongfit.adapters.AlimentosAdapter;
import com.strom.strongfit.models.Alimento;
import com.strom.strongfit.utils.RecyclerItemClickListener;

import java.util.ArrayList;


public class SearchAlimentosActivity extends AppCompatActivity {
    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter alimentosAdapter;
    private static final String TAG = SearchAlimentosActivity.class.getSimpleName();
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_alimentos);
        setToolBar();
        ArrayList<Alimento> alimentosArrayList = new ArrayList<Alimento>();

        Alimento taco = new Alimento();
        taco.setAlimentoID(1);
        taco.setName("Taco");
        taco.setCalories("70 kcal");
        alimentosArrayList.add(taco);

        Alimento melon = new Alimento();
        melon.setAlimentoID(2);
        melon.setName("Melon");
        melon.setCalories("90 kcal");
        alimentosArrayList.add(melon);

        alimentosAdapter = new AlimentosAdapter(alimentosArrayList, R.layout.row_alimento);
        mRecyclerView = (RecyclerView) findViewById(R.id.alimentos_recycler);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(alimentosAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this, new RecyclerItemClickListener.OnItemClickListener(){
            @Override
            public void onItemClick(View view, int position) {
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
        }));
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
        MenuItem searchItem = menu.findItem(R.id.action_search);

        SearchManager searchManager = (SearchManager) SearchAlimentosActivity.this.getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = null;
        if(searchManager == null){
            Log.e(TAG, "ES null el searchManager");
        }
        if(searchItem.getActionView() == null){
            Log.e(TAG, "Es null el searchView");
        }
        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
            Log.e(TAG, "No es null el searchItem");
        }
        if (searchView != null) {
            Log.e(TAG, "Es null el searchView");
            searchView.setSearchableInfo(searchManager.getSearchableInfo(SearchAlimentosActivity.this.getComponentName()));
        }
        return super.onCreateOptionsMenu(menu);
    }

}
