package com.strom.strongfit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.strom.strongfit.fragments.AlimentosFragment;
import com.strom.strongfit.fragments.MainFragment;
import com.strom.strongfit.utils.SessionManager;


public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout; //Lo necesitamos para poner un menu lateral
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawer_toggle;
    private Toolbar toolbar;  //La barrita
    private SessionManager sessionManager; //Para las sesiones
    private int mNavItemId;
    private static final String NAV_ITEM_ID = "navItemId";
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
        if (null == savedInstanceState) {
            mNavItemId = R.id.home_menu;
        } else {
            mNavItemId = savedInstanceState.getInt(NAV_ITEM_ID);
        }
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(mNavItemId).setChecked(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawer_toggle);
        drawer_toggle.syncState();
        navigate(mNavItemId);
    }
    public boolean navigate(int itemID){
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (itemID){
            case R.id.home_menu:
                Toast.makeText(getApplicationContext(),"Home",Toast.LENGTH_SHORT).show();
                MainFragment mainFragment = new MainFragment();
                fragmentTransaction.replace(R.id.frame_content, mainFragment);
                fragmentTransaction.commit();
                setTitle("Conteo Calorico");
                return true;
            case R.id.alimentos_menu:
                Toast.makeText(getApplicationContext(), "Alimentos", Toast.LENGTH_SHORT).show();
                AlimentosFragment alimentosFragment = new AlimentosFragment();
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame_content, alimentosFragment);
                fragmentTransaction.commit();
                setTitle("Alimentos");
                return true;
            case R.id.perfil_menu:
                Intent intent = new Intent(this, ProfileActivity.class);
                startActivity(intent);
            case R.id.salir_menu:
                Toast.makeText(getApplicationContext(), "Salir", Toast.LENGTH_SHORT).show();
                sessionManager.logOutUser();
                finish();
                return true;
            default:
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                return true;
        }
    }
    public void setToolBar(){
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem menuItem) {
        menuItem.setChecked(true);
        mNavItemId = menuItem.getItemId();
        drawerLayout.closeDrawers();
        return navigate(mNavItemId);
    }
    @Override
    protected void onSaveInstanceState(final Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(NAV_ITEM_ID, mNavItemId);
    }
}
