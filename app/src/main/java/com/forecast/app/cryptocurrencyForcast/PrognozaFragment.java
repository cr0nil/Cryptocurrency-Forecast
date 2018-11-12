package com.forecast.app.cryptocurrencyForcast;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.forecast.app.api.ApiClient;
import com.forecast.app.api.ApiClientUnplu;
import com.forecast.app.api.BridgeApi;
import com.forecast.app.cryptocurrencyForcast.databinding.FragmentPrognozaBinding;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 */
public class PrognozaFragment extends Fragment {

    ApiClient client;
    ApiClientUnplu apiClientUnplu;
    BridgeApi bridgeApi;
    ForecastDay forecastDay;
    FragmentPrognozaBinding binding;

    public PrognozaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        client = new ApiClient();
        apiClientUnplu = new ApiClientUnplu();
        bridgeApi = new BridgeApi();
        getBitcoinPLN();
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_prognoza, container, false);

        View view = binding.getRoot();
        return view;
    }

    public void getForecast(JSONArray jsonArray) {

        JSONArray data = new JSONArray();
        final RequestParams params = new RequestParams();

        data = jsonArray;
        params.put("data", data);
        params.setUseJsonStreamer(true);
        params.put("callback", "http://cr0nil.pythonanywhere.com/todo/api2");
        Log.i("params", params.toString());

        apiClientUnplu.postDate("", params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i("response success", response.toString() + statusCode + "");

                super.onSuccess(statusCode, headers, response);
                getData();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.i("Eresponse", errorResponse.toString() + statusCode);

                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });

    }

    public void getData() {
        bridgeApi.getForecast("", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    if (response.get("tasks") == null) {
                        getData();
                    } else {
                        //forecastDay = new ForecastDay(response.getJSONObject(""))
                        JSONArray array = (JSONArray) response.get("tasks");
                        JSONObject jsonObject = (JSONObject) array.get(0);
                        JSONArray array1 = (JSONArray) jsonObject.get("forecast");
                        JSONObject object = (JSONObject) array1.get(0);
                        Log.i("response2", array + "");
                        forecastDay = new ForecastDay(object.getDouble("value"));
                        binding.setForecastDay(forecastDay);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                super.onSuccess(statusCode, headers, response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {

            }
        });
    }

    public void getBitcoinPLN() {

        final JSONArray data = new JSONArray();
        String url = "graphs/BTCPLN/7d.json";

        client.getCryptocurrency(url, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);

                long timeStep[] = new long[response.length()];
                try {
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject toSend = new JSONObject();

                        toSend.put("timestamp", response.getJSONObject(i).getLong("time"));
                        toSend.put("value", response.getJSONObject(i).getDouble("open"));
                        data.put(toSend);

                    }

                    getForecast(data);


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
