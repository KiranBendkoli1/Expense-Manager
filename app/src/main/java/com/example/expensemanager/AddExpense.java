package com.example.expensemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class AddExpense extends AppCompatActivity {

    Spinner expenseSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);


        expenseSpinner = findViewById(R.id.expenseSpinner);

        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(this, R.array.expenseSpinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expenseSpinner.setAdapter(adapter);


    }
}