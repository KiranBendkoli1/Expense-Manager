package com.example.expensemanager;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeF extends Fragment {

    ImageButton addIncome, addExpense;
    Button calculate;
    TextView incomeHome,expenseHome,balanceHome;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference reference;
    TextView displayComment;
    String email, identifier;
    Float incomeT = 0.0f, expenseT = 0.0f, balanceT =00.f ;

    public HomeF() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home2, container, false);

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


        addExpense = view.findViewById(R.id.addExpense);
        addIncome = view.findViewById(R.id.addIncome);
        incomeHome = view.findViewById(R.id.income_home);
        expenseHome = view.findViewById(R.id.expense_home);
        balanceHome = view.findViewById(R.id.balance_home);

        incomeHome.setText("000.00");
        expenseHome.setText("000.00");
        balanceHome.setText("000.00");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull  DataSnapshot snapshot) {
                for(DataSnapshot dataSnapshot: snapshot.getChildren()){
                    Model model;
                    model = dataSnapshot.getValue(Model.class);
                    String transaction = model.getTransaction();
                    String amount = model.getAmount();
                    if(transaction.trim().equals("Income")){
                        incomeT += Float.parseFloat(amount);
                        balanceT += Float.parseFloat(amount);
                    }
                    else{
                        expenseT += Float.parseFloat(amount);
                        balanceT -= Float.parseFloat(amount);
                    }
                    incomeHome.setText(String.valueOf(incomeT));
                    expenseHome.setText(String.valueOf(expenseT));
                    balanceHome.setText(String.valueOf(balanceT));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        addExpense.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddExpense.class));
            }
        });


        addIncome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AddIncome.class));
            }
        });




        return view;
    }
}