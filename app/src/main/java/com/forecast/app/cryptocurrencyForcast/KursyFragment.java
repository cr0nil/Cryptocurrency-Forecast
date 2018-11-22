package com.forecast.app.cryptocurrencyForcast;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

   // RecyclerViewAdapter mRecyclerViewAdapter;
    //RecyclerView recyclerView;


    public KursyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentKursyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_kursy, container, false);
        View view = fragmentKursyBinding.getRoot();

        client = new ApiClient();
        getBitcoinPLN();


      //  View view =  inflater.inflate(R.layout.fragment_kursy, container, false);

       // recyclerView = view.findViewById(R.id.recyclerView);
        //initRecyclerView();

      //  initializeAdapter();

        return view;
    }


//    public void initRecyclerView(){
//
//        RecyclerViewAdapter adapter = new RecyclerViewAdapter(getContext(), mNames);
//        recyclerView.setAdapter(adapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//    }
//
//    public void initializeAdapter() {
//        mRecyclerViewAdapter = new RecyclerViewAdapter(new ArrayList<Cryptocurrency>(), getContext());
//        recyclerView.setAdapter(mRecyclerViewAdapter);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//    }


    public void getBitcoinPLN() {
        String url = "json/BTCPLN/ticker.json";

        client.getCryptocurrency(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    cryptocurrency = new Cryptocurrency(response.getDouble("last"), response.getDouble("low"), response.getDouble("high"), response.getDouble("vwap"), response.getDouble("volume"));
                    //kursyBinding.setCrypto(cryptocurrency);
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
