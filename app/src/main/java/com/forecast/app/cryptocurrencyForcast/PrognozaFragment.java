package com.forecast.app.cryptocurrencyForcast;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forecast.app.api.ApiClient;
import com.forecast.app.cryptocurrencyForcast.databinding.FragmentPrognozaBinding;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 */
public class PrognozaFragment extends Fragment {

    FragmentPrognozaBinding prognozaBinding;
    Cryptocurrency cryptocurrency;
    ApiClient client;


    public PrognozaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        prognozaBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_prognoza, container, false);

        View view = prognozaBinding.getRoot();

        client = new ApiClient();
        getBitcoinPLN();
        return view;
        //return inflater.inflate(R.layout.fragment_prognoza, container, false);
    }


    public void getBitcoinPLN() {
        String url = "json/BTCPLN/ticker.json";

        client.getCryptocurrency(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    cryptocurrency = new Cryptocurrency(response.getDouble("last"), response.getDouble("low"), response.getDouble("high"), response.getDouble("vwap"), response.getDouble("volume"));
                    prognozaBinding.setCrypto(cryptocurrency);
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
