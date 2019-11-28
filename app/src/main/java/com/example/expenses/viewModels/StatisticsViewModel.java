package com.example.expenses.viewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.expenses.database.ReadExpenses;
import com.example.expenses.models.Expenses;

import java.util.List;

public class StatisticsViewModel extends ViewModel {

    private MutableLiveData<List<Expenses>> mExpense = new MutableLiveData<>();

    private ReadExpenses db;
    public void init(Context context){
        if(mExpense == null){
            return;
        }
        db = ReadExpenses.init(context);
        mExpense = db.getTotalCost();

    }


    public LiveData<List<Expenses>> getmExpense() {
        return mExpense;
    }
}
