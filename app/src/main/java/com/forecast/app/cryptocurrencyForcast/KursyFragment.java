package com.forecast.app.cryptocurrencyForcast;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forecast.app.api.ApiClient;
import com.forecast.app.cryptocurrencyForcast.databinding.FragmentKursyBinding;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 */
public class KursyFragment extends Fragment {
    FragmentKursyBinding kursyBinding;
    Cryptocurrency cryptocurrency;
    Cryptocurrency cryptocurrency1;
    ApiClient client;


    public KursyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        kursyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_kursy, container, false);

        View view = kursyBinding.getRoot();

        client = new ApiClient();
        getBitcoinPLN();
        getBitcoinEUR();
        getLiteCoinPLN();
        getEthereumPLN();
        getBTGoldPLN();
        return view;

        //return inflater.inflate(R.layout.fragment_kursy, container, false);
    }


    public void getBitcoinPLN() {
        String url = "json/BTCPLN/ticker.json";

        client.getCryptocurrency(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    cryptocurrency = new Cryptocurrency(response.getDouble("last"), response.getDouble("low"), response.getDouble("high"), response.getDouble("vwap"), response.getDouble("volume"));
                    kursyBinding.setBTCPLN(cryptocurrency);
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

    public void getBitcoinEUR() {
        String url = "json/BTCEUR/ticker.json";

        client.getCryptocurrency(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    cryptocurrency = new Cryptocurrency(response.getDouble("last"), response.getDouble("low"), response.getDouble("high"), response.getDouble("vwap"), response.getDouble("volume"));
                    kursyBinding.setBTCEUR(cryptocurrency);
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

    public void getLiteCoinPLN() {
        String url = "json/LTCPLN/ticker.json";

        client.getCryptocurrency(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    cryptocurrency = new Cryptocurrency(response.getDouble("last"), response.getDouble("low"), response.getDouble("high"), response.getDouble("vwap"), response.getDouble("volume"));
                    kursyBinding.setLTCPLN(cryptocurrency);
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

    public void getEthereumPLN() {
        String url = "json/ETHPLN/ticker.json";

        client.getCryptocurrency(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    cryptocurrency = new Cryptocurrency(response.getDouble("last"), response.getDouble("low"), response.getDouble("high"), response.getDouble("vwap"), response.getDouble("volume"));
                    kursyBinding.setETHPLN(cryptocurrency);
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

    public void getBTGoldPLN() {
        String url = "json/BTGPLN/ticker.json";

        client.getCryptocurrency(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);

                try {
                    cryptocurrency = new Cryptocurrency(response.getDouble("last"), response.getDouble("low"), response.getDouble("high"), response.getDouble("vwap"), response.getDouble("volume"));
                    kursyBinding.setBTGPLN(cryptocurrency);
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
