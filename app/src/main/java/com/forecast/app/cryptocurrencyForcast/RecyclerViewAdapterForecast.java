package com.forecast.app.cryptocurrencyForcast;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.forecast.app.cryptocurrencyForcast.databinding.ForecastDataBinding;

import java.util.ArrayList;

public class RecyclerViewAdapterForecast extends RecyclerView.Adapter<ViewHolderForecast> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<ForecastDay> mData;
    private Context mContext;
    LayoutInflater layoutInflater;


    public RecyclerViewAdapterForecast(ArrayList<ForecastDay> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolderForecast onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                if(layoutInflater == null){
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
       // CryptocurrencyDataBinding cryptocurrencyDataBinding = CryptocurrencyDataBinding.inflate(layoutInflater,viewGroup,false);
        ForecastDataBinding forecastDataBinding = ForecastDataBinding.inflate(layoutInflater,viewGroup,false);

        ViewHolderForecast viewHolder = new ViewHolderForecast(forecastDataBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolderForecast viewHolderForecast, int i) {
        final ForecastDay day = mData.get(i);
        viewHolderForecast.bind(day);
        final ForecastDataBinding forecastDataBinding = viewHolderForecast.getmForecastDataBinding();
        forecastDataBinding.setHandler(new ItemHandler(){
            @Override
            public void onItemClick() {
                Toast.makeText(mContext,"kaczka",Toast.LENGTH_SHORT).show();
            }
        });
//        viewHolderForecast.getmForecastDataBinding().setVariable(BR.day, day.getValue());
//        viewHolderForecast.getmForecastDataBinding().executePendingBindings();
    }






    @Override
    public int getItemCount() {
        return mData.size();
    }




}


