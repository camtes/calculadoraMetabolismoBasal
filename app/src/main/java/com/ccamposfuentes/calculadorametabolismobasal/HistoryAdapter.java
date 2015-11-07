package com.ccamposfuentes.calculadorametabolismobasal;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ccamposfuentes on 7/11/15.
 */
public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {
    private List<BMR> mDataset = new ArrayList<>();


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView date, bmr, calories, lostCalories, gainCalories;

        public ViewHolder(View v) {
            super(v);

            date = (TextView) v.findViewById(R.id.historyDate);
            bmr = (TextView) v.findViewById(R.id.historyMBRGenerated);
            calories = (TextView) v.findViewById(R.id.historyMBRCalorIesGenerated);
            lostCalories = (TextView) v.findViewById(R.id.historyCaloriesLostWeightGenerated);
            gainCalories = (TextView) v.findViewById(R.id.historyCaloriesGainWeightGenerated);
        }
    }

    // Constructor, puedes crear varios según el tipo de contenido.
    public HistoryAdapter(List<BMR> myDataset) {
        mDataset = myDataset;
    }

    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                   int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_history_item, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - Se recupera el elemento del vector con position.
        holder.date.setText(mDataset.get(position).getDate());
        holder.bmr.setText(String.format("%.2f", mDataset.get(position).getBMR()));
        holder.calories.setText(String.format("%.2f", mDataset.get(position).getCalories()));
        holder.lostCalories.setText(String.format("%.2f", mDataset.get(position).getLostCalories()));
        holder.gainCalories.setText(String.format("%.2f", mDataset.get(position).getGainCalories()));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}