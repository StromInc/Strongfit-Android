package com.strom.strongfit;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.strom.strongfit.fragments.MainFragment;
import com.strom.strongfit.fragments.SalirDialogFragment;
import com.strom.strongfit.utils.BitmapManager;
import com.strom.strongfit.utils.SessionManager;


public class MainActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, SalirDialogFragment.DialogListener {

    private DrawerLayout drawerLayout; //Lo necesitamos para poner un menu lateral
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawer_toggle;
    private Toolbar toolbar;  //La barrita
    private SessionManager sessionManager; //Para las sesiones
    private int mNavItemId;
    private static final String NAV_ITEM_ID = "navItemId";
    private final static String TAG = MainActivity.class.getSimpleName(); //Para los logs
    private String nombre = "";
    private String correo = "";
    private String avatar = "";
    private static ImageView imagenAvatar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sessionManager = new SessionManager(this);
        if (sessionManager.checkLogin()) { //Checa si ya hay una sesion, si no la hay manda al login
            this.finish();
        }
        setToolBar(); //Crea la toolbar, es un metodo que esta abajo
        if (null == savedInstanceState) {
            mNavItemId = R.id.home_menu;
        } else {
            mNavItemId = savedInstanceState.getInt(NAV_ITEM_ID);

        }
        nombre = sessionManager.getName();
        correo = sessionManager.getCorreo();
        avatar = sessionManager.getAvatar();
        Log.i(TAG, "El valor es: " + mNavItemId);
        //le metemos el correo, el nombre y la imagen de perfil
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        ((TextView) findViewById(R.id.nombre_text)).setText(nombre);
        ((TextView) findViewById(R.id.email_text)).setText(correo);
        imagenAvatar = (ImageView) findViewById(R.id.profile_image);
        BitmapManager.getInstance().loadBitmap(avatar, imagenAvatar);
        //Esto solo controla el menu lateral
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.getMenu().findItem(mNavItemId).setChecked(true);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer_toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close) {
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

    //Con esto cambiamos de pantalla
    public boolean navigate(int itemID) {
        Log.i(TAG, "el id " + itemID);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (itemID) {
            case R.id.home_menu:
                MainFragment mainFragment = new MainFragment();
                fragmentTransaction.replace(R.id.frame_content, mainFragment);
                fragmentTransaction.commit();
                setTitle("Conteo Calorico");
                return true;
            case R.id.alimentos_menu:
                mNavItemId = R.id.home_menu;
                Intent intent = new Intent(this, SearchAlimentosActivity.class);
                startActivity(intent);
                return true;
            case R.id.perfil_menu:
                mNavItemId = R.id.home_menu;
                Intent intent2 = new Intent(this, ProfileActivity.class);
                startActivity(intent2);
                return true;
            case R.id.salir_menu:
                showDialog();
                return true;
            default:
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                return true;
        }
    }

    public void setToolBar() {
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

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(TAG, "el valor onresume es: " + mNavItemId);
        navigationView.getMenu().findItem(mNavItemId).setChecked(true);
    }

    //Con este metodo manejamos la entrada y salida de la aplicacion
    public void showDialog() {
        SalirDialogFragment newFragment = new SalirDialogFragment();
        newFragment.setTitulo(R.string.titulo_salir);
        newFragment.show(getSupportFragmentManager(), "Dialogo");
    }

    //SI elige salir
    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Log.e(TAG, "Positivo");
        sessionManager.logOutUser();
        finish();
    }

    //Si le da hueva cerrar sesion
    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        mNavItemId = R.id.home_menu;
        navigationView.getMenu().findItem(mNavItemId).setChecked(true);
        Log.e(TAG, "Negativo");
    }
}
