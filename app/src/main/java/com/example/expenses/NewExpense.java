package com.example.expenses;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.expenses.database.DbManager;
import com.example.expenses.viewModels.ExpensesListViewModel;

public class NewExpense extends AppCompatActivity {

    private DbManager db;
    Boolean isupdate = false;
    int id =0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_expense_alert);
        final TextView expensename = findViewById(R.id.item);
        final TextView cost = findViewById(R.id.cost);

        Button submit = findViewById(R.id.search);
        db = new DbManager(this);
        Bundle extras = getIntent().getExtras();
        if(extras != null){
            expensename.setText(extras.get("expense").toString());
            cost.setText(extras.get("price").toString());
            id = extras.getInt("id");
            isupdate = true;
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isupdate){
                    if(db.updateNewEXpense(Double.parseDouble(cost.getText().toString()),expensename.getText().toString(),id)){
                        Toast toast = Toast.makeText(getApplicationContext(), "Expense Updated Successfully", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                }
                else{
                    if(db.addNewEXpense(Double.parseDouble(cost.getText().toString()),expensename.getText().toString())){
                        expensename.setText("");
                        cost.setText("");
                        Toast toast = Toast.makeText(getApplicationContext(), "Expense Added Successfully", Toast.LENGTH_SHORT);
                        toast.show();

                    }
                }
            }
        });
    }


}
