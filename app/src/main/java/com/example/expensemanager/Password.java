package com.example.expensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class Password extends AppCompatActivity {

    Button resetButton;
    EditText mailId;
    String emailId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password);

        resetButton = findViewById(R.id.reset_password);
        mailId = findViewById(R.id.main_id);




        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().setLanguageCode("en");
                emailId = mailId.getText().toString().trim();
                FirebaseAuth.getInstance().sendPasswordResetEmail(emailId).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(Password.this, "Password reset link send on your mail id ", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Password.this, "Some error occurs please try again later", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}