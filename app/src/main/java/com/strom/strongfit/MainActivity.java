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
import android.util.Log;
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


public class MainActivity extends ActionBarActivity implements ListView.OnItemClickListener {

    private DrawerLayout drawerLayout; //Lo necesitamos para poner un menu lateral
    private RecyclerView recyclerView; //Nuestro recycler(Es nuestro menu)
    private ActionBarDrawerToggle drawer_toggle;
    private RecyclerView.LayoutManager mLayoutManager;
    Toolbar toolbar;  //La barrita
    private RecyclerView.Adapter drawerAdapter;
    private String[] list_titles; //Los titulos del menu
    private SessionManager sessionManager; //Para las sesiones
    //Estos valores son de prueba
    private final static String NOMBRE = "Moises de Pazzi";
    private final static String PESO = "70 kg";
    private int profileImage;
    //Los iconos que vamos a usar
    private final static int ICONOS[] = {R.drawable.ic_home, R.drawable.ic_food,
            R.drawable.ic_chat, R.drawable.ic_dietas, R.drawable.ic_articulos,
            R.drawable.ic_close};

    private final static String TAG = MainActivity.class.getSimpleName(); //Para los logs

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);
        if(sessionManager.checkLogin()){ //Checa si ya hay una sesion, si no la hay manda al login
            this.finish();
        }
        setToolBar(); //Crea la toolbar, es un metodo que esta abajo
        //Se necesitan inicializar todos nuestros elementos
        list_titles = getResources().getStringArray(R.array.list_titles);
        profileImage = R.drawable.dog;

        recyclerView = (RecyclerView) findViewById(R.id.drawer_list);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mLayoutManager = new LinearLayoutManager(this);
        drawerAdapter = new CustomRecyclerViewAdapter(list_titles, NOMBRE, PESO, profileImage, ICONOS);

        recyclerView.setHasFixedSize(true);

        Log.e(TAG, "valor " + profileImage); //Esto es como un System.out.print()

        recyclerView.setAdapter(drawerAdapter);
        recyclerView.setLayoutManager(mLayoutManager);

        drawer_toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close); //Esto agrega un icono a la barra para que se muestre el menu
        drawerLayout.setDrawerListener(drawer_toggle); //Detectamos los eventos
        setSelectItem(0); //Esto pone la pagina que se va a ver
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
    public void setSelectItem(int position){
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

        //recyclerView.setItemChecked(position, true);
        //setTitle(recyclerView.getItemAtPosition(position).toString());
        drawerLayout.closeDrawer(recyclerView);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        setSelectItem(i);
    }
}
