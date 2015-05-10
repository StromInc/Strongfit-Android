package com.strom.strongfit;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.strom.strongfit.adapters.CustomPagerAdapter;
import com.strom.strongfit.fragments.ArticulosFragment;
import com.strom.strongfit.fragments.ChatFragment;
import com.strom.strongfit.utils.SessionManager;
import com.strom.strongfit.utils.SlidingTabLayout;


public class MainMedicoActivity extends ActionBarActivity {

    private CustomPagerAdapter pagerAdapter;
    private SlidingTabLayout slidingTabLayout;
    private String[] titles;
    private Fragment[] fragments;
    private SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_medico);

        sessionManager = new SessionManager(this);
        if(sessionManager.checkLogin()){
            this.finish();
        }

        setToolBar();
        setTabsAndPager();
    }
    //Este metodo crea la barra de navegacion
    public void setToolBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }
    //Con esto hacemos la navegacion
    public void setTabsAndPager(){
        //Recuperamos los nombres de las pestañas
        titles = getResources().getStringArray(R.array.titles_medico);
        fragments = new Fragment[]{
                new ArticulosFragment(),
                new ChatFragment()
        };
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager_medico); //Obtenemos el viewpager necesario para paginar
        pagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(), fragments, titles); //se crea un adaptardor para el viewpager
        viewPager.setAdapter(pagerAdapter); //Le pasamos el adaptador (Los fragmentos)

        //Esto solo es necesario si se quiere navegacion por tabs
        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs); //Recuperamos el sliding
        slidingTabLayout.setDistributeEvenly(true); //Para que todos los titulos ocupen el mismo espacio en el menu
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.accent_color); //El color de la barrita del titulo
            }
        });
        slidingTabLayout.setViewPager(viewPager); //Le pasamos el viewpager para que haga su trabajo
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    //Este metodo cuando se selecciona cerrar sesion o perfil del OptionsMenu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        Intent intent;

        //noinspection SimplifiableIfStatement
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
}
