package com.example.expensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

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

public class AddIncome extends AppCompatActivity {

    Spinner incomeSpinner;
    EditText incomeFigure,incomeDescription;
    String category;
    Button saveIncome, clearIncome;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    TextView dateTimeDisplay;
    Calendar calendar;
    SimpleDateFormat dateFormat;
    String date;
    String email,identifier;
    @SuppressLint("SimpleDateFormat")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_income);
        incomeSpinner = findViewById(R.id.incomeSpinner);
        incomeFigure = findViewById(R.id.incomeFigure);
        incomeDescription = findViewById(R.id.incomeDescription);
        saveIncome =  findViewById(R.id.saveIncome);
        clearIncome = findViewById(R.id.clearIncome);
        // get current time
        dateTimeDisplay =findViewById(R.id.text_date_display);
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("yyyy.MMMM.dd hh:mm aaa");
        date = dateFormat.format(calendar.getTime());
        dateTimeDisplay.setText(date);
        // to goto fragment HomeF
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        HomeF homeF = new HomeF();

        firebaseDatabase = FirebaseDatabase.getInstance();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        email = user.getEmail();

        identifier ="";
        for(char ch : email.toCharArray()){
            if(ch == '.' || ch =='[' || ch ==']' || ch =='#' || ch=='$' || ch =='@'){
                continue;
            }else{
                identifier += ch;
            }
        }

        reference = firebaseDatabase.getReference().child(identifier);

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

                String description = incomeDescription.getText().toString().trim();
                String amount = String.valueOf(incomeAmount);


                // creation of HashMap
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("transaction","Income");
                hashMap.put("amount",amount);
                hashMap.put("category",category);
                hashMap.put("description",description);
                hashMap.put("datetime",date);

                // pushing to database
                reference.push().setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            finish();
                            Toast.makeText(getApplicationContext(),"Income data is saved sucessfully",Toast.LENGTH_LONG).show();
                            startActivity(new Intent(getApplicationContext(),Home.class));
                        }
                        else{
                            Toast.makeText(getApplicationContext(),"Income data is not saved",Toast.LENGTH_LONG).show();
                        }
                    }
                });



            }
        });

        clearIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                incomeFigure.setText("");
                incomeDescription.setText("");
            }
        });

    }
}