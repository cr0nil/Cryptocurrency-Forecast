package com.forecast.app.cryptocurrencyForcast;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.forecast.app.api.ApiClient;
import com.forecast.app.cryptocurrencyForcast.databinding.ActivityDashboardBinding;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class DashboardActivity extends AppCompatActivity {
    ActivityDashboardBinding dashboardBinding;
    Cryptocurrency cryptocurrency;
    ApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dashboardBinding = DataBindingUtil.setContentView(this, R.layout.activity_dashboard);

        setSupportActionBar(dashboardBinding.include.toolbar);
        DrawerLayout drawer = dashboardBinding.drawerLayout;

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, dashboardBinding.include.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        dashboardBinding.drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        setupNavigationView(dashboardBinding.navView);

        client = new ApiClient();
        getBitcoinPLN();

    }

    public void getBitcoinPLN() {
        String url = "json/BTCPLN/ticker.json";

        client.getCryptocurrency(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    cryptocurrency = new Cryptocurrency(response.getDouble("last"), response.getDouble("low"), response.getDouble("high"), response.getDouble("vwap"), response.getDouble("volume"));
                    dashboardBinding.setCrypto(cryptocurrency);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("data", String.valueOf(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

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

    private void switchFragmentByMenuItem(MenuItem menuItem) {
        int id = 1;

        Log.i("id", menuItem.toString());
        switch (id) {
            case 1:
                Intent intent = new Intent(this, ForecastActivity.class);
                startActivity(intent);


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
