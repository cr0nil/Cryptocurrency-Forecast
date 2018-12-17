package com.forecast.app.cryptocurrencyForcast;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.databinding.DataBindingUtil;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.forecast.app.api.ApiClient;
import com.forecast.app.cryptocurrencyForcast.databinding.FragmentAlarmBinding;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class AlarmFragment extends Fragment {
    ApiClient client;
    FragmentAlarmBinding fragmentAlarmBinding;
    String text;

    public AlarmFragment() {
        // Required empty public constructor
    }

    String restoredText;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//
        fragmentAlarmBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_alarm, container, false);
        View view = fragmentAlarmBinding.getRoot();
        getBitcoinPLN();

        fragmentAlarmBinding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                text = fragmentAlarmBinding.bounds.getText().toString();
                SharedPreferences.Editor editor = getContext().getSharedPreferences("name", MODE_PRIVATE).edit();
                editor.putString("name", text);

                editor.apply();

            }
        });


        Thread timer = new Thread() {
            @Override
            public void run() {
                Log.i("Thread", "Running");
                while (true) {
                    try {
                        Thread.sleep(5000);
                        getBitcoinPLN();
                    } catch (InterruptedException e) {
                        e.printStackTrace();

                    }

                }
            }
        };


        timer.start();

        SharedPreferences prefs = getContext().getSharedPreferences("name", MODE_PRIVATE);
      restoredText = prefs.getString("name", null);
        fragmentAlarmBinding.value.setText(restoredText);
//        mainHandler.post(thread);


        return view;
    }

    public void getBitcoinPLN() {

        client = new ApiClient();
        client.getCryptocurrency("json/BTCPLN/ticker.json", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {

                    double sor = Double.valueOf(response.getString("ask"));
                    if (sor > Double.valueOf(restoredText)) {
                        startAlarm(true, false);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public boolean getUseSynchronousMode() {
                return false;
            }
        });
    }


    private void startAlarm(boolean isNotification, boolean isRepeat) {
        AlarmManager manager = (AlarmManager) getContext().getSystemService(Context.ALARM_SERVICE);
        Intent myIntent;
        PendingIntent pendingIntent;

        if (!isNotification) {
            myIntent = new Intent(getContext(), AlarmToastReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(getContext(), 0, myIntent, 0);
        } else {
            myIntent = new Intent(getContext(), AlarmNotificationReceiver.class);
            pendingIntent = PendingIntent.getBroadcast(getContext(), 0, myIntent, 0);
        }

        if (!isRepeat)
            manager.set(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 1000, pendingIntent);
        else
            manager.setRepeating(AlarmManager.RTC_WAKEUP, SystemClock.elapsedRealtime() + 3000, 60 * 1000, pendingIntent);
    }

}
