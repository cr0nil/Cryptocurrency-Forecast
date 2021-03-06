package com.forecast.app.cryptocurrencyForcast;

import android.app.Dialog;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.forecast.app.cryptocurrencyForcast.databinding.ActivityDashboardBinding;

import net.danlew.android.joda.JodaTimeAndroid;

public class DashboardActivity extends AppCompatActivity {
    public static final String apiKey = "afe26b6470a607194e6de734afb1b497596211699e7704348f315da0c74ac1e6";
    ActivityDashboardBinding dashboardBinding;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dashboardBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);
        JodaTimeAndroid.init(this);
        setSupportActionBar(dashboardBinding.include.toolbar);
        DrawerLayout drawer = dashboardBinding.drawerLayout;

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, dashboardBinding.include.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dashboardBinding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        setupNavigationView(dashboardBinding.navView);

        button = findViewById(R.id.btnRefresh);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setTitle("Ulubione");
                UlubioneFragment fragment = new UlubioneFragment();
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.frame, fragment, "Ulubione");
                fragmentTransaction.commit();
                Toast.makeText(getApplication(), "Odświeżono ulubione", Toast.LENGTH_SHORT).show();
            }
        });


        setTitle("Kursy");
        KursyFragment fragment = new KursyFragment();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame, fragment, "Kursy");
        fragmentTransaction.commit();

    }


    private void setupNavigationView(NavigationView navigationView) {
        // navigationView.menu.findItem(R.id.nav_my_tracks).isEnabled = true //isLoggedIn
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Log.i("nav", menuItem.toString());
                switchFragmentByMenuItem(menuItem);
                dashboardBinding.drawerLayout.closeDrawers();
                return true;
            }
        });


    }

    public void showsplash() {

        final Dialog dialog = new Dialog(DashboardActivity.this, android.R.style.Theme_Light_NoTitleBar_Fullscreen);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_loading);
        dialog.setCancelable(true);
        dialog.show();

        final Handler handler = new Handler();
        final Runnable runnable = new Runnable() {
            @Override
            public void run() {
                {
                    dialog.dismiss();
                }
            }
        };
        handler.postDelayed(runnable, 3000);
    }

    private void switchFragmentByMenuItem(MenuItem menuItem) {
        int id = menuItem.getItemId();

        Log.i("id", menuItem.toString());
        if (id == R.id.nav_kursy) {
            showsplash();
            setTitle("Kursy");
            KursyFragment fragment = new KursyFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment, "Kursy");
            fragmentTransaction.commit();

        } else if (id == R.id.nav_prognoza) {
            setTitle("Prognoza");
            PrognozaFragment fragment = new PrognozaFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment, "Prognoza");
            fragmentTransaction.commit();
        } else if (id == R.id.nav_alarm) {
            setTitle("Alarm");
            AlarmFragment fragment = new AlarmFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment, "Alarm");
            fragmentTransaction.commit();
        } else if (id == R.id.nav_ulubione) {
            setTitle("Ulubione");
            UlubioneFragment fragment = new UlubioneFragment();
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.frame, fragment, "Ulubione");
            fragmentTransaction.commit();
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
