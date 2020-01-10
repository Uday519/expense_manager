package com.example.expenses;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import com.example.expenses.adapters.RecycleAdapter;
import com.example.expenses.adapters.StatisticsAdapter;
import com.example.expenses.database.DbManager;
import com.example.expenses.database.ReadExpenses;
import com.example.expenses.models.Expenses;
import com.example.expenses.viewModels.StatisticsViewModel;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

public class Statistics extends AppCompatActivity {

    private DbManager db;
    private StatisticsViewModel statisticsViewModel;
    private RecyclerView rv;
    private ReadExpenses readExpenses;
    private StatisticsAdapter recycleAdapter;
    private List<Expenses> expenseLists;
    private BarChart chart;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics);
        db = new DbManager(this.getApplicationContext());
        statisticsViewModel = ViewModelProviders.of(this).get(StatisticsViewModel.class);
        statisticsViewModel.init(this.getApplicationContext());
        expenseLists = statisticsViewModel.getmExpense().getValue();
        statisticsViewModel.getmExpense().observe(this, new Observer<List<Expenses>>() {
            @Override
            public void onChanged(@Nullable List<Expenses> expenses) {
                recycleAdapter.notifyDataSetChanged();
            }
        });
        chart = findViewById(R.id.barchart);
        initRecyclerView();
        initBarchart();
    }

    public void initRecyclerView(){
        db = new DbManager(this);
        rv = findViewById(R.id.total_cost_recycler);
        rv.setLayoutManager(new LinearLayoutManager(this));
        recycleAdapter = new StatisticsAdapter(expenseLists, this);
        rv.setAdapter(recycleAdapter);
        rv.setItemAnimator(new DefaultItemAnimator());
    }

    public void initBarchart(){

        ArrayList costs = new ArrayList();
        ArrayList x_axis = new ArrayList();
        for(int i=0;i<expenseLists.size();i++){
            String year = String.valueOf(expenseLists.get(i).year);
            year = year.substring(year.length() - 2);
            String date = expenseLists.get(i).day +"-"+expenseLists.get(i).month+"-"+year;
            costs.add(new BarEntry(Float.valueOf(expenseLists.get(i).price),i));
            x_axis.add(date);
        }


        BarDataSet bardataset = new BarDataSet(costs, "Daily Expense Total");
        chart.animateY(3000);
        BarData data = new BarData(x_axis, bardataset);
        bardataset.setColors(ColorTemplate.COLORFUL_COLORS);
        chart.setData(data);
    }
}
