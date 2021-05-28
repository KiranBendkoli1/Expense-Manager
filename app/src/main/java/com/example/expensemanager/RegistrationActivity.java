package com.example.expensemanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.SingleLineTransformationMethod;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class RegistrationActivity extends AppCompatActivity {

    EditText email,password;
    Button regBtn;

    TextView signIn;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        email = findViewById(R.id.email_reg);
        password = findViewById(R.id.password_reg);
        regBtn = findViewById(R.id.register);
        signIn = findViewById(R.id.signInHere);

        firebaseAuth = FirebaseAuth.getInstance();

        regBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailS = email.getText().toString();
                String passwordS = password.getText().toString();
                if(emailS.isEmpty()){
                    email.setError("Email Required");
                    return;
                }
                if(!Patterns.EMAIL_ADDRESS.matcher(emailS).matches()){
                    email.setError("Please enter a valid email");
                    return;
                }
                if(passwordS.isEmpty()){
                    password.setError("Password Required");
                    return;
                }
                if(passwordS.length() <8){
                    password.setError("Minimum length of password should be 8");
                    return;
                }
                firebaseAuth.createUserWithEmailAndPassword(emailS,passwordS).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            finish();
                            Toast.makeText(getApplicationContext(),"Registration Complete",Toast.LENGTH_SHORT);
                            Intent i = new Intent(getApplicationContext(),Home.class);
                            startActivity(i);

                        }else{
                            Toast.makeText(getApplicationContext(),"Registration Failed",Toast.LENGTH_SHORT);
                        }
                    }
                });
            }
        });

    }





}