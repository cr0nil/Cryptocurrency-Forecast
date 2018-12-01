package com.forecast.app.cryptocurrencyForcast;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.forecast.app.api.ApiClient;
import com.forecast.app.cryptocurrencyForcast.databinding.FragmentKursyBinding;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 */
public class KursyFragment extends Fragment {
    FragmentKursyBinding fragmentKursyBinding;
    Cryptocurrency cryptocurrency;
    ApiClient client;
    ArrayList<Cryptocurrency> cryptocurrenciesList;
    ArrayList<String> urlList;
    ArrayList<String> cryptoName;
    int j = 0;
    int i;


    public KursyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentKursyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_kursy, container, false);
        View view = fragmentKursyBinding.getRoot();
        cryptocurrenciesList = new ArrayList<>();
        urlList = new ArrayList<>();
        client = new ApiClient();
        urlList.add("json/BTCPLN/ticker.json");
        urlList.add("json/BTCEUR/ticker.json");
        urlList.add("json/ETHPLN/ticker.json");

        fragmentKursyBinding.recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentKursyBinding.recycler.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        getBitcoinPLN();
        fragmentKursyBinding.recycler.setAdapter(new RecyclerViewAdapter(cryptocurrencies(), getContext()));



        return view;
    }


    private ArrayList<Cryptocurrency> cryptocurrencies() {

        return cryptocurrenciesList;

    }


    public void getBitcoinPLN() {


//        urlList.add("json/ETHEUR/ticker.json");


        cryptoName = new ArrayList<>();
        cryptoName.add("BTC/PLN");
        cryptoName.add("BTC/EUR");
        cryptoName.add("ETH/PLN");
//        cryptoName.add("ETH/EUR");

        for (i = 0; i < urlList.size(); i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            client.getCryptocurrency(urlList.get(i), null, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);

                    try {
                        cryptocurrency = new Cryptocurrency(response.getDouble("last"), response.getDouble("low"), response.getDouble("high"), response.getDouble("vwap"), response.getDouble("volume"),"PLNY");
                        cryptocurrenciesList.add(cryptocurrency);
                        fragmentKursyBinding.recycler.getAdapter().notifyDataSetChanged();

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

}
