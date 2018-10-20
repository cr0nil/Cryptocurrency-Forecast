package com.example.kbzdz.cryptocurrency_forcast;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.kbzdz.cryptocurrency_forcast.databinding.ActivityDashboardBinding;

public class DashboardActivity extends AppCompatActivity {
ActivityDashboardBinding dashboardBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dashboardBinding = DataBindingUtil.setContentView(this,R.layout.activity_dashboard);
        
    }
}
