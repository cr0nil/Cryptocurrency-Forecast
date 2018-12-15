package com.forecast.app.cryptocurrencyForcast;


import android.content.Context;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.forecast.app.api.ApiClient;
import com.forecast.app.cryptocurrencyForcast.databinding.FragmentUlubioneBinding;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 */
public class UlubioneFragment extends Fragment {
    FragmentUlubioneBinding fragmentUlubioneBinding;
    Cryptocurrency cryptocurrency;
    ApiClient client;
    ArrayList<Cryptocurrency> cryptocurrenciesList;
    int i;
    int j = 0;

    public UlubioneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentUlubioneBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ulubione, container, false);
        cryptocurrenciesList = new ArrayList<>();
        client = new ApiClient();

        View view = fragmentUlubioneBinding.getRoot();
        fragmentUlubioneBinding.recyclerUlu.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentUlubioneBinding.recyclerUlu.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        fragmentUlubioneBinding.recyclerUlu.setAdapter(new RecyclerViewAdapter(cryptocurrencies(), getContext()));
        getBitcoinPLN();
        return view;
    }

    public ArrayList<String> loadArray(String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);
        int size = prefs.getInt(arrayName + "_size", 0);
        ArrayList<String> array = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            if ((prefs.getString(arrayName + "_" + i, null)) != null) {
                array.add(prefs.getString(arrayName + "_" + i, null));
                System.out.println("Array " + array);
            }

        }
        return array;
    }

    private ArrayList<Cryptocurrency> cryptocurrencies() {
        Log.i("test shared", loadArray("crypto", getContext()).toString());


//       ? cryptocurrenciesList.add(new Cryptocurrency(1, 2, 3, 4, 5, "BTC/PLN"));
//       cryptocurrenciesList.add(new Cryptocurrency(6, 7, 8, 9, 10, "BTC/PLN"));
        return cryptocurrenciesList;

    }

    public void getBitcoinPLN() {


//        urlList.add("json/ETHEUR/ticker.json");


        final ArrayList<String> urlList = loadArray("crypto", getContext());

        if (urlList.isEmpty()) {
            Toast.makeText(getContext(), "Brak ulubionych", Toast.LENGTH_SHORT).show();
        } else {
            for (i = 0; i < urlList.size(); i++) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i("url", "json/" + urlList.get(i).substring(0, 3) + urlList.get(i).substring(4, 7) + "/ticker.json");
                String urlItem = "json/" + urlList.get(i).substring(0, 3) + urlList.get(i).substring(4, 7) + "/ticker.json";
                client.getCryptocurrency(urlItem, null, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);

                        try {
                            cryptocurrency = new Cryptocurrency(response.getDouble("bid"), response.getDouble("ask"), response.getDouble("high"), response.getDouble("low"), urlList.get(j));
                            cryptocurrenciesList.add(cryptocurrency);
                            fragmentUlubioneBinding.recyclerUlu.getAdapter().notifyDataSetChanged();
                            j++;
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
}
