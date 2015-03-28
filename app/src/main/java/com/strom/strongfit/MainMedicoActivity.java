package com.strom.strongfit;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewParent;
import android.widget.Toast;

import com.strom.strongfit.adapters.CustomPagerAdapter;
import com.strom.strongfit.util.SlidingTabLayout;


public class MainMedicoActivity extends ActionBarActivity {

    CustomPagerAdapter pagerAdapter;
    SlidingTabLayout slidingTabLayout;
    private String[] titles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_medico);
        setToolBar();
        setTabsAndPager();
    }

    public void setToolBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.medico_toolbar);
        setSupportActionBar(toolbar);
    }
    public void setTabsAndPager(){
        titles = getResources().getStringArray(R.array.titles_medico);

        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager_medico);
        pagerAdapter = new CustomPagerAdapter(getSupportFragmentManager(), titles);
        viewPager.setAdapter(pagerAdapter);

        slidingTabLayout = (SlidingTabLayout) findViewById(R.id.sliding_tabs);
        slidingTabLayout.setDistributeEvenly(true);
        slidingTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.accent_color);
            }
        });
        slidingTabLayout.setViewPager(viewPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main_medico, menu);
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
        if (id == R.id.action_perfil) {
            intent = new Intent(this, ProfileActivity.class);
            startActivity(intent);
            return true;
        }else{
            if(id == R.id.action_cerrar){
                Toast.makeText(this, "Cerrar", Toast.LENGTH_SHORT).show();
                finish();
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                return true;
            }
        }

        return super.onOptionsItemSelected(item);
    }
}
