package com.example.expenses.database;

import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.database.Cursor;

import com.example.expenses.models.Expenses;

import java.util.ArrayList;
import java.util.List;

public class ReadExpenses {

    private DbManager db;
    private static ReadExpenses instance = null;

    public static ReadExpenses init(Context context){
        if(instance == null){
            instance = new ReadExpenses(context);
        }
        return instance;
    }

    public ReadExpenses(Context context){
        this.db = new DbManager(context);
    }

    public List<Expenses> readExpenses() {
        Cursor cursor = db.getALLItems();
        List<Expenses> expenses = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Expenses tempexpense = new Expenses(cursor.getString(cursor.getColumnIndex("item")),
                        cursor.getString(cursor.getColumnIndex("price")),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex("day"))),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex("month"))),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex("year"))),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
                expenses.add(tempexpense);
            } while (cursor.moveToNext());


        }
        return expenses;
    }

    public MutableLiveData<List<Expenses>> readMutableExpenses() {
        MutableLiveData<List<Expenses>> data = new MutableLiveData<>();
        data.setValue(readExpenses());
        return data;
    }

    public MutableLiveData<List<Expenses>> searchMutableExpense(String date) {
        Cursor cursor = db.searchItem(date);
        List<Expenses> expenses = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Expenses tempexpense = new Expenses(cursor.getString(cursor.getColumnIndex("item")),
                        cursor.getString(cursor.getColumnIndex("price")),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex("day"))),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex("month"))),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex("year"))),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex("id"))));
                expenses.add(tempexpense);
            } while (cursor.moveToNext());


        }
        MutableLiveData<List<Expenses>> data  = new MutableLiveData<>();
        data.setValue(expenses);
        return data;
    }

    public MutableLiveData<List<Expenses>> getTotalCost() {
        Cursor cursor = db.getCost();
        List<Expenses> expenses = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                Expenses temp_expense = new Expenses(Integer.parseInt(cursor.getString(cursor.getColumnIndex("day"))),
                        cursor.getString(cursor.getColumnIndex("cost")),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex("month"))),
                        Integer.parseInt(cursor.getString(cursor.getColumnIndex("year"))));
                expenses.add(temp_expense);
            } while (cursor.moveToNext());


        }
        MutableLiveData<List<Expenses>> data  = new MutableLiveData<>();
        data.setValue(expenses);
        return data;
    }

}
