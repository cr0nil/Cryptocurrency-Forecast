package com.forecast.app.cryptocurrencyForcast;

import android.support.v7.widget.RecyclerView;

import com.forecast.app.cryptocurrencyForcast.databinding.ForecastDataBinding;


public class ViewHolderForecast extends RecyclerView.ViewHolder {
private ForecastDataBinding mForecastDataBinding;
    public ViewHolderForecast(ForecastDataBinding forecastDataBinding) {
        super(forecastDataBinding.getRoot());
        this.mForecastDataBinding = forecastDataBinding;
    }

    public void bind(ForecastDay forecastDay){
        this.mForecastDataBinding.setDay(forecastDay);
    }

    public ForecastDataBinding getmForecastDataBinding() {
        return mForecastDataBinding;
    }


}
