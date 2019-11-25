package com.example.expenses.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.expenses.R;
import com.example.expenses.models.Expenses;

import java.util.List;

public class RecycleAdapter extends RecyclerView.Adapter<RecycleAdapter.RecyclerViewHolder> {

    List<Expenses> expensesList;
    Context context;
    LayoutInflater inflater;
    private OnExpenseListener onExpenseListener;
    public RecycleAdapter(List<Expenses> readExpenses, Context context, OnExpenseListener onExpenseListener) {
        this.expensesList = readExpenses;
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.onExpenseListener = onExpenseListener;
    }

    public void setExpensesList(List<Expenses> expensesList) {
        this.expensesList = expensesList;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.expense_list_row,viewGroup,false);
        RecyclerViewHolder recyclerViewHolder = new RecyclerViewHolder(view, onExpenseListener);
        return recyclerViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder recyclerViewHolder, int i) {
        String date = expensesList.get(i).day +"-"+expensesList.get(i).month+"-"+expensesList.get(i).year;
        recyclerViewHolder.day.setText(date);
        recyclerViewHolder.item.setText(expensesList.get(i).item);
        recyclerViewHolder.price.setText(expensesList.get(i).price);


    }

    @Override
    public int getItemCount() {
        return expensesList.size();
    }

    public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView day;
        TextView item;
        TextView price;
        OnExpenseListener onExpenseListener;

        public RecyclerViewHolder(@NonNull View itemView, OnExpenseListener onExpenseListener) {
            super(itemView);
             this.day = itemView.findViewById(R.id.day);
             this.item = itemView.findViewById(R.id.item);
             this.price = itemView.findViewById(R.id.price);
             this.onExpenseListener = onExpenseListener;
             itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            onExpenseListener.onExpenseClick(getAdapterPosition());
        }
    }

    public interface OnExpenseListener{
        void onExpenseClick(int index);
    }
}
