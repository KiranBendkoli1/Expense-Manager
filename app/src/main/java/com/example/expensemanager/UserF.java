package com.example.expensemanager;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class UserF extends Fragment {

    ImageView profileImage;
    TextView nameOfUser;

    FirebaseAuth auth;
    public UserF() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user2, container, false);
        profileImage = view.findViewById(R.id.profile_image_u);
        nameOfUser = view.findViewById(R.id.name_of_user_u);
        // Inflate the layout for this fragment
        loadUserInformation();
        return view;
    }

    private void loadUserInformation() {
        FirebaseUser user = null;
        try{
            user = auth.getCurrentUser();
            if(user != null){
                if(user.getPhotoUrl() != null){
                    //Glide.with(this).load(user.getPhotoUrl().toString()).into(profileImage);
                    profileImage.setImageURI(user.getPhotoUrl());
                }
                if(auth.getCurrentUser().getDisplayName() != null){
                    nameOfUser.setText(user.getDisplayName());
                }
            }
        }catch (NullPointerException e){

        }
    }


}