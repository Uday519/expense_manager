package com.example.expenses.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.expenses.R;
import com.example.expenses.models.Expenses;

import java.util.List;

public class StatisticsAdapter extends RecyclerView.Adapter<StatisticsAdapter.RecyclerViewHolder> {

    List<Expenses> expensesList;
    Context context;
    LayoutInflater inflater;
    public StatisticsAdapter(List<Expenses> readExpenses, Context context) {
        this.expensesList = readExpenses;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.statistics_row,viewGroup,false);
        RecyclerViewHolder rv = new RecyclerViewHolder(view);
        return rv;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        String date = expensesList.get(i).day +"-"+expensesList.get(i).month+"-"+expensesList.get(i).year;
        recyclerViewHolder.day.setText(date);
        recyclerViewHolder.cost.setText(expensesList.get(i).price);
    }

    @Override
    public int getItemCount() {
        return expensesList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder{

        TextView day;
        TextView cost;
        TextView price;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            this.day = itemView.findViewById(R.id.day);
            this.cost = itemView.findViewById(R.id.total_cost);
        }
    }
}