package com.forecast.app.cryptocurrencyForcast;

import android.content.Context;
import android.content.SharedPreferences;
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

String [] array;
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
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        ArrayList<String> strings;
        final Cryptocurrency crypto = mData.get(i);
        viewHolder.bind(crypto);
        final CryptocurrencyDataBinding cryptocurrencyDataBinding = viewHolder.getmCryptocurrencyDataBinding();
        final ArrayList<String> finalStrings= new ArrayList<>();
        cryptocurrencyDataBinding.setHandler(new ItemHandler(){
            @Override
            public void onItemClick() {
                finalStrings.add(cryptocurrencyDataBinding.getCrypto().getNameKrypto());
//                finalStrings.add("BTC/PLN");

                saveArray(finalStrings,"crypto",viewHolder.itemView.getContext());
                Toast.makeText(mContext,cryptocurrencyDataBinding.getCrypto().getNameKrypto(),Toast.LENGTH_SHORT).show();
            }
        });
//        viewHolder.getBinding().setVariable(com.forecast.app.cryptocurrencyForcast.BR.crypto, crypto.last);
//        viewHolder.getBinding().executePendingBindings();
    }

    public boolean saveArray(ArrayList<String> array, String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(arrayName +"_size", array.size());
        for(int i=0;i<array.size();i++)
            editor.putString(arrayName + "_" + i, array.get(i));
        return editor.commit();
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }




}


