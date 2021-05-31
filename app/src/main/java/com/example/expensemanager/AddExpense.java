package com.example.expensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class AddExpense extends AppCompatActivity {

    Spinner expenseSpinner;
    EditText expenseFigure, expenseDescription;
    String category;
    TextView dateTimeDisplay;
    Calendar calendar;
    SimpleDateFormat dateFormat;
    String date,email;
    String identifier;
    DataObjectExpense   dataObjectExpense;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    Button saveExpense, clearExpense;

    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expense);

        // get current time
        dateTimeDisplay =findViewById(R.id.text_date_display);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy.MMMM.dd hh:mm aaa");
        date = dateFormat.format(calendar.getTime());
        dateTimeDisplay.setText(date);

        expenseFigure = findViewById(R.id.expenseFigure);
        expenseDescription =findViewById(R.id.expenseDescription);
        saveExpense = findViewById(R.id.saveExpense);
        clearExpense = findViewById(R.id.clearExpense);

        firebaseDatabase = FirebaseDatabase.getInstance();

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        email = user.getEmail();
        identifier = "";
        for(char ch : email.toCharArray()){
            if(ch == '.' || ch =='[' || ch ==']' || ch =='#' || ch=='$' || ch =='@'){
                continue;
            }else{
                identifier += ch;
            }
        }
        reference = firebaseDatabase.getReference().child(identifier);
        expenseSpinner = findViewById(R.id.expenseSpinner);

        ArrayAdapter<CharSequence> adapter;
        adapter = ArrayAdapter.createFromResource(this, R.array.expenseSpinner, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        expenseSpinner.setAdapter(adapter);

        expenseSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = parent.getItemAtPosition(position).toString().trim();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Toast.makeText(AddExpense.this, "Please select category", Toast.LENGTH_SHORT).show();
            }
        });

        saveExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Float expenseAmount = Float.parseFloat(expenseFigure.getText().toString().trim());

                dataObjectExpense = new DataObjectExpense();
                dataObjectExpense.setExpenseCategory(category);
                dataObjectExpense.setExpenseDescription(expenseDescription.getText().toString().trim());
                dataObjectExpense.setExpenseFigure(expenseAmount);


                // using alternative method to save data without using class object
                String description = expenseDescription.getText().toString().trim();
                String amount = String.valueOf(expenseAmount);

                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("transaction","Expense");
                hashMap.put("amount",amount);
                hashMap.put("category",category);
                hashMap.put("description",description);
                hashMap.put("datetime",date);

                // pushing to database
                reference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(),"Expense data is saved sucessfully",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),Home.class));
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Expense data is not saved",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

        clearExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expenseFigure.setText("");
                expenseDescription.setText("");
            }
        });
    }
}