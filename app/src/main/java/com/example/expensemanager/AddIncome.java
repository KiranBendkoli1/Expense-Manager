package com.example.expensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddIncome extends AppCompatActivity {

    Spinner incomeSpinner;
    EditText incomeFigure,incomeDescription;
    String category;
    Button saveIncome;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    DataObjectIncome dataObjectIncome;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);
        incomeSpinner = findViewById(R.id.incomeSpinner);
        incomeFigure = findViewById(R.id.incomeFigure);
        incomeDescription = findViewById(R.id.incomeDescription);
        saveIncome =  findViewById(R.id.saveIncome);

        firebaseDatabase = FirebaseDatabase.getInstance();
        reference = firebaseDatabase.getReference().child("Data");

        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(this, R.array.incomeSpinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        incomeSpinner.setAdapter(adapter);


        incomeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = parent.getItemAtPosition(position).toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AddIncome.this, "Please select category", Toast.LENGTH_SHORT).show();
            }
        });





        saveIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Float incomeAmount = Float.parseFloat(incomeFigure.getText().toString().trim());

                dataObjectIncome = new DataObjectIncome();
                dataObjectIncome.setIncomeCategory(category);
                dataObjectIncome.setIncomeDescription(incomeDescription.getText().toString().trim());

                dataObjectIncome.setIncomeFigure(incomeAmount);


                // pushing to database
                reference.push().setValue(dataObjectIncome).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Income data is saved sucessfully",Toast.LENGTH_LONG).show();
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Income data is not saved",Toast.LENGTH_LONG).show();
                        }
                    }
                });

            }
        });

    }
}