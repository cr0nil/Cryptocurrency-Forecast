package com.forecast.app.cryptocurrencyForcast;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.forecast.app.cryptocurrencyForcast.databinding.CryptocurrencyDataBinding;

public class ViewHolder extends RecyclerView.ViewHolder {
private CryptocurrencyDataBinding mCryptocurrencyDataBinding;
    public ViewHolder(CryptocurrencyDataBinding cryptocurrencyDataBinding) {
        super(cryptocurrencyDataBinding.getRoot());
        this.mCryptocurrencyDataBinding = cryptocurrencyDataBinding;
    }

    public void bind(Cryptocurrency cryptocurrency){
        this.mCryptocurrencyDataBinding.setCrypto(cryptocurrency);
    }

    public CryptocurrencyDataBinding getmCryptocurrencyDataBinding() {
        return mCryptocurrencyDataBinding;
    }
}
