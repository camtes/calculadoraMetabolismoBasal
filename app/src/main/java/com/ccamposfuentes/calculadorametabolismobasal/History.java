package com.ccamposfuentes.calculadorametabolismobasal;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class History extends Fragment {


    private RecyclerView mRecyclerView;
    private HistoryAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_history, container, false);

        BMRHelperAdapter bmrDB = new BMRHelperAdapter(getActivity());
        List<BMR> aux;
        aux = bmrDB.getHistory();

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.rvHistory);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Adaptador
        mAdapter = new HistoryAdapter(aux);
        mRecyclerView.setAdapter(mAdapter);


        return rootView;
    }


}
