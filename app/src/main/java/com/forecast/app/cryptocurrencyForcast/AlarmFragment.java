package com.forecast.app.cryptocurrencyForcast;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.RadioButton;
import android.widget.Toast;

import com.forecast.app.api.ApiClient;
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

    public AlarmFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        getBitcoinPLN();

        SharedPreferences.Editor editor = getContext().getSharedPreferences("name", MODE_PRIVATE).edit();
        editor.putString("name", "Elena");
        editor.putInt("idName", 12);
        editor.apply();

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


//        mainHandler.post(thread);


        return inflater.inflate(R.layout.fragment_alarm, container, false);
    }

    public void getBitcoinPLN() {

        client = new ApiClient();
        client.getCryptocurrency("json/BTCPLN/ticker.json", null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {

                    double sor = Double.valueOf(response.getString("ask"));
                    if (sor > 12150) {
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
