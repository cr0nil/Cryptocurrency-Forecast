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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.forecast.app.api.ApiClient;
import com.forecast.app.api.ApiClientUnplu;
import com.forecast.app.api.BridgeApi;
import com.forecast.app.cryptocurrencyForcast.databinding.FragmentPrognozaBinding;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cz.msebera.android.httpclient.Header;


/**
 * A simple {@link Fragment} subclass.
 */
public class PrognozaFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    ApiClient client;
    ApiClientUnplu apiClientUnplu;
    BridgeApi bridgeApi;
    ForecastDay forecastDay;
    FragmentPrognozaBinding binding;
    long spinnnerPosition = 0;

    public PrognozaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        client = new ApiClient();
        apiClientUnplu = new ApiClientUnplu();
        bridgeApi = new BridgeApi();

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_prognoza, container, false);

        View view = binding.getRoot();

        ArrayAdapter<CharSequence> sequenceArrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.days, android.R.layout.simple_spinner_item);
        sequenceArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinner.setAdapter(sequenceArrayAdapter);
        binding.spinner.setOnItemSelectedListener(this);
        binding.recycler2.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recycler2.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        binding.recycler2.setAdapter(new RecyclerViewAdapterForecast(forecastDays(), getContext()));
        getBitcoinPLN();
        return view;
    }

    ArrayList<ForecastDay> forecastDays = new ArrayList<>();
    ArrayList<ForecastDay> data = new ArrayList<>();

    private ArrayList<ForecastDay> forecastDays() {


        return forecastDays;

    }

    public void getForecast(JSONArray jsonArray) {

        JSONArray data = new JSONArray();
        final RequestParams params = new RequestParams();
        long currentTimeMillis = System.currentTimeMillis();
        String day = "1543342800";

        currentTimeMillis = (currentTimeMillis / 1000) + 259200;
        data = jsonArray;

        params.put("data", data);
        params.setUseJsonStreamer(true);
        params.put("forecast_to", Long.valueOf(currentTimeMillis));
        params.put("callback", "http://cr0nil.pythonanywhere.com/todo/api2");
        Log.i("params", String.valueOf(currentTimeMillis));

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
                        long time2 = time / 1000;
                        for (int i = 0; i < array1.length(); i++) {
                            JSONObject object = (JSONObject) array1.get(i);
                            // Log.i("time1", (time + day  )+"");
                            Date resultdate1 = new Date(object.getLong("timestamp") * 1000);
                            DateTime result = new DateTime(resultdate1);


                            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
                            DateTimeFormatter dtf = DateTimeFormat.forPattern("MMM dd,yyyy HH:mm");
                            DateTime today = new DateTime().withTimeAtStartOfDay();
                            DateTime tomorrow = today.plusDays(1).withTimeAtStartOfDay();
//

                                    object2 = (JSONObject) array1.get(i);

                                    Date resultdate = new Date(object2.getLong("timestamp") * 1000);
//                                ForecastDay forecastDay = new ForecastDay(1);
//                                    Log.i("time2", sdf.format(resultdate) + "");
                                    Date dt = new Date();

                                    DateTime today1 = new DateTime().withTimeAtStartOfDay();

//
//                                    Log.i("time3", dtf.print(today1) + "");

                                    forecastDay = new ForecastDay(object2.getDouble("value"), sdf.format(resultdate) + "");
                                    // if()
                                    data.add(forecastDay);
                            if (spinnnerPosition == 0) {
                                if (result.isBefore(tomorrow) ) {
                                    forecastDay = new ForecastDay(object2.getDouble("value"), sdf.format(resultdate) + "");
                                    // if()
                                    forecastDays.add(forecastDay);
                                    binding.recycler2.getAdapter().notifyDataSetChanged();
                                }
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {



        DateTime today = new DateTime().withTimeAtStartOfDay();
        DateTime today1 = new DateTime().withTimeAtStartOfDay();
        Log.i("format1",""+"start");
        DateTime tomorrow = today.plusDays(1).withTimeAtStartOfDay();
        today1 = today.plusHours(23);
        DateTimeFormatter dtf = DateTimeFormat.forPattern("MMM dd,yyyy HH:mm");
// DateTime dateTime = new DateTime();
        DateTimeFormatter formatter = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm:ss");
        ArrayList<ForecastDay> forecastDays2 = new ArrayList<>();

        forecastDays.clear();

        Log.i("format2", "" +forecastDays.size() );

        for (int i = 0; i < data.size(); i++) {
            DateTime dt = dtf.parseDateTime(data.get(i).getDate());
//            Log.i("format2", "" +dt );
//            Log.i("format4", "" +today );
            Log.i("today2",data.get(i).getDate().toString());
              if (dt.isAfter(today1)&& dt.isBefore(tomorrow.plusDays(1).withTimeAtStartOfDay()) &&  id ==1) {
                  Log.i("today2",today1.toString());
//            Log.i("format1",""+dt);
            Log.i("format3", data.get(1).getDate());
            forecastDays.add(data.get(i));
            //  if (time.isBefore(dateTime)) {
            binding.recycler2.getAdapter().notifyDataSetChanged();


            }
            else if( dt.isAfter(tomorrow.plusDays(1).withTimeAtStartOfDay())&& dt.isBefore(tomorrow.plusDays(2).withTimeAtStartOfDay())&& id ==2){
                Log.i("format2", data.get(1).getDate());
                forecastDays.add(data.get(i));
                //  if (time.isBefore(dateTime)) {
                binding.recycler2.getAdapter().notifyDataSetChanged();


            }    else if( dt.isAfter(tomorrow.plusDays(2).withTimeAtStartOfDay())&& dt.isBefore(tomorrow.plusDays(3).withTimeAtStartOfDay())&& id ==3){
                Log.i("format2", data.get(1).getDate());
                forecastDays.add(data.get(i));
                //  if (time.isBefore(dateTime)) {
                binding.recycler2.getAdapter().notifyDataSetChanged();


            }
        }
        binding.recycler2.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}
