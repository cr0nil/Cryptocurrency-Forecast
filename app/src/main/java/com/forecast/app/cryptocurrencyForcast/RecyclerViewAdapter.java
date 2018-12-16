package com.forecast.app.cryptocurrencyForcast;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import com.forecast.app.cryptocurrencyForcast.databinding.CryptocurrencyDataBinding;

import java.util.ArrayList;
import java.util.Map;

public class RecyclerViewAdapter extends RecyclerView.Adapter<ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";

    private ArrayList<Cryptocurrency> mData;
    private Context mContext;
    LayoutInflater layoutInflater;

    String[] array;
    ArrayList<String> finalStrings = new ArrayList<>();


    public RecyclerViewAdapter(ArrayList<Cryptocurrency> mData, Context mContext) {
        this.mData = mData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(viewGroup.getContext());
        }
        CryptocurrencyDataBinding cryptocurrencyDataBinding = CryptocurrencyDataBinding.inflate(layoutInflater, viewGroup, false);

        ViewHolder viewHolder = new ViewHolder(cryptocurrencyDataBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int i) {
        final Cryptocurrency crypto = mData.get(i);
        viewHolder.bind(crypto);
        final CryptocurrencyDataBinding cryptocurrencyDataBinding = viewHolder.getmCryptocurrencyDataBinding();

        cryptocurrencyDataBinding.setHandler(new ItemHandler() {
            @Override
            public void onItemClick() {
                finalStrings = loadArray("crypto", mContext);
                String zmienna = cryptocurrencyDataBinding.getCrypto().getNameKrypto();

                if (finalStrings.contains(zmienna)) {
                    Toast.makeText(mContext, "Dodano juz do ulubionych", Toast.LENGTH_SHORT).show();
                } else {
                    finalStrings.add(zmienna);
                    saveArray(finalStrings, "crypto", viewHolder.itemView.getContext());
                    Toast.makeText(mContext, "Dodano do ulubionych " + cryptocurrencyDataBinding.getCrypto().getNameKrypto(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public boolean onLongClick() {
                String zmienna = cryptocurrencyDataBinding.getCrypto().getNameKrypto();
                SharedPreferences prefs = mContext.getSharedPreferences("preferencename", mContext.MODE_PRIVATE);

                String key = findKey(prefs, zmienna);
                prefs.edit().remove(key).apply();

                //prefs.edit().clear().apply();
                Toast.makeText(mContext, "Usunieto", Toast.LENGTH_SHORT).show();


                return true;
            }
        });
//        viewHolder.getBinding().setVariable(com.forecast.app.cryptocurrencyForcast.BR.crypto, crypto.last);
//        viewHolder.getBinding().executePendingBindings();
    }

    public boolean saveArray(ArrayList<String> array, String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(arrayName + "_size", array.size());
        for (int i = 0; i < array.size(); i++)
            editor.putString(arrayName + "_" + i, array.get(i));
        return editor.commit();
    }


    public ArrayList<String> loadArray(String arrayName, Context mContext) {
        SharedPreferences prefs = mContext.getSharedPreferences("preferencename", 0);
        int size = prefs.getInt(arrayName + "_size", 0);
        ArrayList<String> array = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            if ((prefs.getString(arrayName + "_" + i, null)) != null) {
                array.add(prefs.getString(arrayName + "_" + i, null));

            }
            System.out.println("Array " + array);
        }
        return array;
    }

    String findKey(SharedPreferences sharedPreferences, String value) {
        for (Map.Entry<String, ?> entry : sharedPreferences.getAll().entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null; // not found
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


}


