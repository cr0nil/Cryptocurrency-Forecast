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
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;

import com.forecast.app.api.ApiClient;
import com.forecast.app.api.ApiClientUnplu;
import com.forecast.app.api.BridgeApi;
import com.forecast.app.cryptocurrencyForcast.databinding.FragmentPrognozaBinding;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

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

        ArrayAdapter<CharSequence> sequenceArrayAdapter = ArrayAdapter.createFromResource(getContext(),R.array.days,android.R.layout.simple_spinner_item);
        sequenceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(sequenceArrayAdapter);
        binding.recycler2.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recycler2.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        binding.recycler2.setAdapter(new RecyclerViewAdapterForecast(forecastDays(), getContext()));
        return view;
    }
    ArrayList<ForecastDay>  forecastDays = new ArrayList<>();
    private ArrayList<ForecastDay> forecastDays() {


        return forecastDays;

    }

    public void getForecast(JSONArray jsonArray) {

        JSONArray data = new JSONArray();
        final RequestParams params = new RequestParams();
        long currentTimeMillis = System.currentTimeMillis();
        String day = "1543342800";

        currentTimeMillis = (currentTimeMillis/1000);
        data = jsonArray;
        params.put("data", data);
        params.setUseJsonStreamer(true);
        //Date d =
       // params.put("forecast_to",(long)1458136800);
//
        params.put("callback", "http://cr0nil.pythonanywhere.com/todo/api2");
      //  params.put("forecast_to",1458136800);
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
                        JSONObject object2 = null;
                        long day = 3600;
                        long day2 = 1;

                        long time = System.currentTimeMillis();
                       long  time2 = time/1000;
                        for (int i = 0; i < array1.length(); i++) {
                            JSONObject object = (JSONObject) array1.get(i);
                           // Log.i("time1", (time + day  )+"");

                            if (object.getLong("timestamp") >= (time2 + day )  ){
                                object2 = (JSONObject) array1.get(i);
                                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
                                Date resultdate = new Date(object2.getLong("timestamp")*1000);
//                                ForecastDay forecastDay = new ForecastDay(1);
                                Log.i("time2", sdf.format(resultdate)+"");
                                forecastDay = new ForecastDay(object2.getDouble("value"),sdf.format(resultdate)+"");
                                forecastDays.add(forecastDay);
                                binding.recycler2.getAdapter().notifyDataSetChanged();

                                binding.setForecastDay(forecastDay);

                            }
                        }


                       // Log.i("time", time + "");

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
