package com.example.expenses.viewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.example.expenses.database.ReadExpenses;
import com.example.expenses.models.Expenses;

import java.util.List;

public class ExpensesListViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private MutableLiveData<List<Expenses>> mExpense;
    private ReadExpenses readExpenses;

    public MutableLiveData<List<Expenses>> getmExpense() {
        return mExpense;
    }

    public void init(Context context){
        if(mExpense != null){
            return;
        }
        readExpenses = ReadExpenses.init(context);
        mExpense = readExpenses.readMutableExpenses();

    }
    public void addNewExpense(List<Expenses> expenses){
        mExpense.postValue(expenses);
    }
}
