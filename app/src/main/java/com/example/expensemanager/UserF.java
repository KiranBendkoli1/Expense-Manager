package com.example.expensemanager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.internal.api.FirebaseNoSignedInUserException;


public class UserF extends Fragment {

    ImageView profileImage;
    TextView nameOfUser, emailIdUser;
    Button logout,aboutus;
    String username;
    FirebaseAuth auth;
    public UserF() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user2, container, false);
        logout = view.findViewById(R.id.logout);
        aboutus = view.findViewById(R.id.about_us);

        emailIdUser = view.findViewById(R.id.emailIdUser);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        emailIdUser.setText(user.getEmail());
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(getContext(), "User Logged out", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getContext(), MainActivity.class));
            }
        });
        aboutus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(),AboutUs.class));
            }
        });
        return view;
    }
}