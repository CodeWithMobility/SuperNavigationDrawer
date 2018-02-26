package com.manu.scalablenavigationdrawer;


import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;


public class MainActivity extends AppCompatActivity {
    private SuperDrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer =  findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.open_drawer, R.string.close_drawer);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        drawer.customAdjustment(10.0f, 0.8f,10.0f, 10f);
        NavigationView mNavigationView =  findViewById(R.id.navigation_view);

        mNavigationView.setNavigationItemSelectedListener(
                item -> {
                    drawer.closeDrawer(GravityCompat.START);
                    switch (item.getItemId()) {
                        case R.id.nav_item_about:

                            return true;
                        case R.id.nav_item_rate_us:

                            return true;
                        case R.id.nav_item_feed:

                            return true;
                        case R.id.nav_item_logout:
                            return true;
                        default:
                            return false;
                    }
                });
    }
}
