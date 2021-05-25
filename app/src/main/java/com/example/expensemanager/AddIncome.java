package com.example.expensemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

public class AddIncome extends AppCompatActivity {

    Spinner incomeSpinner;
    EditText incomeFigure,incomeDescription;
    SpinnerAdapter iSpinnerAdapter;
    String type;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);
        String[]  arr ={"Salary", "Loan", "Sales"};

        incomeSpinner = findViewById(R.id.incomeSpinner);
        incomeFigure = findViewById(R.id.incomeFigure);
        incomeDescription = findViewById(R.id.incomeDescription);



        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(this, R.array.incomeSpinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incomeSpinner.setAdapter(adapter);


        incomeSpinner.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                type = parent.getItemAtPosition(position).toString();
            }
        });

    }
}