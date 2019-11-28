package com.example.expenses;

import android.app.Dialog;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.expenses.adapters.RecycleAdapter;
import com.example.expenses.database.DbManager;
import com.example.expenses.database.ReadExpenses;
import com.example.expenses.models.Expenses;
import com.example.expenses.viewModels.ExpensesListViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity implements RecycleAdapter.OnExpenseListener {


    private DbManager db;
    private ReadExpenses readExpenses;
    private ExpensesListViewModel mViewModel;
    private RecyclerView rv;
    private RecycleAdapter recycleAdapter;
    private Dialog dialog;
    private EditText expense_edittext;
    private EditText price_edittext;
    boolean isUpdate;
    LinearLayout data;
    RelativeLayout no_data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        no_data = findViewById(R.id.no_data);
        data = findViewById(R.id.data);
        dialog = new Dialog(this);
        db = new DbManager(this.getApplicationContext());
        readExpenses = ReadExpenses.init(getApplicationContext());
        mViewModel = ViewModelProviders.of(this).get(ExpensesListViewModel.class);
        mViewModel.init(getApplicationContext());
        mViewModel.getmExpense().observe(this, new Observer<List<Expenses>>() {
            @Override
            public void onChanged(@Nullable List<Expenses> expenses) {
                if(expenses.size() == 0){
                    data.setVisibility(View.GONE);
                    no_data.setVisibility(View.VISIBLE);
                }
                else {
                    data.setVisibility(View.VISIBLE);
                    no_data.setVisibility(View.GONE);
                    recycleAdapter.setExpensesList(expenses);
                    recycleAdapter.notifyDataSetChanged();
                }

            }
        });
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                isUpdate = false;
                addNewExpense("","",0);
            }
        });
        Button show_stats = findViewById(R.id.show_stats);
        show_stats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),Statistics.class);
                startActivity(intent);
            }
        });
        initRecyclerView();
    }

    @Override
    public void onExpenseClick(int index) {
        isUpdate = true;
        List<Expenses> expenseLists = mViewModel.getmExpense().getValue();
        addNewExpense(expenseLists.get(index).item,expenseLists.get(index).price,expenseLists.get(index).id);
    }

    public void initRecyclerView(){
        db = new DbManager(this);
        rv = findViewById(R.id.expenserecycleview);
        rv.setLayoutManager(new LinearLayoutManager(this));
        recycleAdapter = new RecycleAdapter(mViewModel.getmExpense().getValue(), this, this);
        rv.setAdapter(recycleAdapter);
        rv.setItemAnimator(new DefaultItemAnimator());

        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
                List<Expenses> expenses_temp = mViewModel.getmExpense().getValue();
                int id = expenses_temp.get(viewHolder.getAdapterPosition()).id;
                db.deleteEexpense(id);
                mViewModel.addNewExpense(readExpenses.readExpenses());
                recycleAdapter.notifyItemRemoved(i);
                recycleAdapter.notifyItemRangeChanged(i,expenses_temp.size()-1);

            }
        };

        new ItemTouchHelper(simpleCallback).attachToRecyclerView(rv);
    }

    public void addNewExpense(final String expense, final String price, final int id){
        Button close_dialog, submit;
        dialog.setContentView(R.layout.custom_pop_up);
        expense_edittext = dialog.findViewById(R.id.item);
        price_edittext =dialog.findViewById(R.id.cost);
        submit = dialog.findViewById(R.id.submit);
        expense_edittext.setText(expense);
        price_edittext.setText(price);
        expense_edittext.requestFocus();
        close_dialog = dialog.findViewById(R.id.close_dialog);
        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isUpdate){
                    if(db.updateNewEXpense(Double.parseDouble(price_edittext.getText().toString()),expense_edittext.getText().toString(),id)){
                        mViewModel.addNewExpense(readExpenses.readExpenses());
                        Toast toast = Toast.makeText(getApplicationContext(), "Expense Updated Successfully", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                else{
                    if(db.addNewEXpense(Double.parseDouble(price_edittext.getText().toString()),expense_edittext.getText().toString())){
                        expense_edittext.setText("");
                        price_edittext.setText("");
                        expense_edittext.requestFocus();
                        mViewModel.addNewExpense(readExpenses.readExpenses());
                        Toast toast = Toast.makeText(getApplicationContext(), "Expense Added Successfully", Toast.LENGTH_SHORT);
                        toast.show();

                    }
                }
            }
        });
        dialog.show();

    }
}





