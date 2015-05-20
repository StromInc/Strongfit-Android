package com.strom.strongfit;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.strom.strongfit.adapters.CustomRecyclerViewAdapter;
import com.strom.strongfit.fragments.ArticulosFragment;
import com.strom.strongfit.fragments.ChatFragment;
import com.strom.strongfit.fragments.MainPacienteFragment;
import com.strom.strongfit.utils.SessionManager;


public class MainPacienteActivity extends ActionBarActivity implements ListView.OnItemClickListener {

    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView; //Nuestro recycler
    private ActionBarDrawerToggle drawer_toggle;
    RecyclerView.LayoutManager mLayoutManager;
    Toolbar toolbar;
    private RecyclerView.Adapter drawerAdapter;
    private String[] list_titles;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_paciente);

        sessionManager = new SessionManager(this);
        if(sessionManager.checkLogin()){
            this.finish();
        }
        setToolBar();
        recyclerView = (RecyclerView) findViewById(R.id.drawer_list);
        mLayoutManager = new LinearLayoutManager(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        list_titles = getResources().getStringArray(R.array.list_titles);

        drawerAdapter = new CustomRecyclerViewAdapter(list_titles, "Moises", "70 kg");
        recyclerView.setAdapter(drawerAdapter);
        recyclerView.setLayoutManager(mLayoutManager);

        drawer_toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_open);
        drawerLayout.setDrawerListener(drawer_toggle);
        selectItem(0);
    }

    public void setToolBar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent;

        //noinspection SimplifiableIfStatement
        if (drawer_toggle.onOptionsItemSelected(item)) {
            return true;
        }
        if (id == R.id.action_perfil) {
            intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
            return true;
        }else{
            if(id == R.id.action_cerrar){
                sessionManager.logOutUser();
                this.finish();
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawer_toggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawer_toggle.onConfigurationChanged(newConfig);
    }
    public void selectItem(int position){
        Fragment frag;

        switch (position) {
            case 0:
                frag = new MainPacienteFragment();
                break;
            case 1:
                frag = new ArticulosFragment();
                break;
            case 2:
                frag = new ChatFragment();
                break;
            default:
                frag = new MainPacienteFragment();
        }

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_content, frag)
                .commit();

        drawer_list.setItemChecked(position, true);
        setTitle(drawer_list.getItemAtPosition(position).toString());
        drawerLayout.closeDrawer(drawer_list);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        selectItem(i);
    }
}
