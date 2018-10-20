package com.forecast.app.cryptocurrencyForcast;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

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
}
