package com.strom.strongfit;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.strom.strongfit.adapters.CustomRecyclerViewAdapter;
import com.strom.strongfit.fragments.AlimentosFragment;
import com.strom.strongfit.fragments.ArticulosFragment;
import com.strom.strongfit.fragments.ChatFragment;
import com.strom.strongfit.fragments.DietasFragment;
import com.strom.strongfit.fragments.MainFragment;
import com.strom.strongfit.utils.SessionManager;


public class MainActivity extends ActionBarActivity{

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
        final GestureDetector mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });

        recyclerView.setHasFixedSize(true);

        recyclerView.setAdapter(drawerAdapter);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {
                View child = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());
                if(child != null && mGestureDetector.onTouchEvent(motionEvent)){
                    drawerLayout.closeDrawers();
                    setSelectItem(recyclerView.getChildPosition(child));
                    return true;
                }
                return false;
            }

            @Override
            public void onTouchEvent(RecyclerView recyclerView, MotionEvent motionEvent) {

            }
        });
        drawer_toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open,
                R.string.drawer_close); //Esto agrega un icono a la barra para que se muestre el menu
        drawerLayout.setDrawerListener(drawer_toggle); //Detectamos los eventos
        setSelectItem(1); //Esto pone la pagina que se va a ver
    }

    public void setToolBar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
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
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
                frag = new MainFragment(); //Solo lo puse aqui para que no me marque error
                break;
            case 1:
                frag = new MainFragment();
                break;
            case 2:
                frag = new AlimentosFragment();
                break;
            case 3:
                frag = new ChatFragment();
                break;
            case 4:
                frag = new DietasFragment();
                break;
            case 5:
                frag = new ArticulosFragment();
                break;
            case 6:
                sessionManager.logOutUser();
                this.finish();
                frag = new MainFragment(); //Solo lo puse aqui para que no me marque error
                break;
            default:
                frag = new MainFragment();
        }
        if(position != 6 && position != 0){
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame_content, frag).commit();

            setTitle(list_titles[position - 1]);
            drawerLayout.closeDrawer(recyclerView);
        }
    }
}
