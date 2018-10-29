package com.forecast.app.cryptocurrencyForcast;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forecast.app.api.ApiClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 */
public class PrognozaFragment extends Fragment {

    ApiClient client;

    public PrognozaFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        client = new ApiClient();
        getBitcoinPLN();
        return inflater.inflate(R.layout.fragment_prognoza, container, false);
    }


    public void getBitcoinPLN() {

        String url = "graphs/BTCPLN/7d.json";

        client.getCryptocurrency(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                JSONObject jsonObject = null;
                JSONObject toSend = null;
                JSONArray data = null;
                long timeStep [] = new long[response.length()];
                try {
                    for(int i =0;i<response.length();i++){
                        jsonObject = response.getJSONObject(i);
                        Log.i("to send",String.valueOf(jsonObject.getLong("time")));
//                        timeStep[i] = jsonObject.getLong("time");
                       // toSend.put("timestamp",timeStep[i]);
//                        toSend.put("value",jsonObject.getDouble("open"));
//                        data.put(toSend);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }




            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

    }

}
