package com.forecast.app.cryptocurrencyForcast;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forecast.app.cryptocurrencyForcast.databinding.FragmentUlubioneBinding;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class UlubioneFragment extends Fragment {
    FragmentUlubioneBinding fragmentUlubioneBinding;

    public UlubioneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        fragmentUlubioneBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ulubione, container, false);
        View view = fragmentUlubioneBinding.getRoot();
        fragmentUlubioneBinding.recycler.setLayoutManager(new LinearLayoutManager(getContext()));
        fragmentUlubioneBinding.recycler.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        fragmentUlubioneBinding.recycler.setAdapter(new RecyclerViewAdapter(cryptocurrencies(), getContext()));
        return view;
    }


    private ArrayList<Cryptocurrency> cryptocurrencies() {
        ArrayList<Cryptocurrency> cryptocurrenciesList = new ArrayList<>();
//       ? cryptocurrenciesList.add(new Cryptocurrency(1, 2, 3, 4, 5, "BTC/PLN"));
//       cryptocurrenciesList.add(new Cryptocurrency(6, 7, 8, 9, 10, "BTC/PLN"));
        return cryptocurrenciesList;

    }

}
