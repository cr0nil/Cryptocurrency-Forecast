package com.forecast.app.cryptocurrencyForcast;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.forecast.app.cryptocurrencyForcast.ViewHolder;
import com.forecast.app.cryptocurrencyForcast.databinding.CryptocurrencyDataBinding;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<Cryptocurrency> mData;
    private Context mContext;
    LayoutInflater layoutInflater;


    public RecyclerViewAdapter(ArrayList<Cryptocurrency> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        if(layoutInflater == null){
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        CryptocurrencyDataBinding cryptocurrencyDataBinding = CryptocurrencyDataBinding.inflate(layoutInflater,viewGroup,false);

        ViewHolder viewHolder = new ViewHolder(cryptocurrencyDataBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder( ViewHolder viewHolder, int i) {
        final Cryptocurrency crypto = mData.get(i);
        viewHolder.bind(crypto);
        final CryptocurrencyDataBinding cryptocurrencyDataBinding = viewHolder.getmCryptocurrencyDataBinding();
        cryptocurrencyDataBinding.setHandler(new ItemHandler(){
            @Override
            public void onItemClick() {
                Toast.makeText(mContext,"kaczka",Toast.LENGTH_SHORT).show();
            }
        });
//        viewHolder.getBinding().setVariable(com.forecast.app.cryptocurrencyForcast.BR.crypto, crypto.last);
//        viewHolder.getBinding().executePendingBindings();
    }



    @Override
    public int getItemCount() {
        return mData.size();
    }




}


