package com.forecast.app.cryptocurrencyForcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
public class AlarmToastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"THIS IS MY ALARM",Toast.LENGTH_LONG).show();
    }
}