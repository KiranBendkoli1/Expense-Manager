package com.example.expensemanager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class ProfileActivity extends AppCompatActivity {
    private static final int CHOOSE_IMAGE = 101;
    ImageView profileImage;
    Button btnSave;
    EditText nameOfUser;
    Uri uriProfile;
    String profileImageUrl;
    FirebaseAuth firebaseAuth;

    FirebaseUser user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileImage = findViewById(R.id.profile_image);
        btnSave = findViewById(R.id.save_btn);
        nameOfUser = findViewById(R.id.name_of_user);


        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageChooser();

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInformation();
            }
        });

    }

    private void saveUserInformation() {
        String displayName = nameOfUser.getText().toString();
        if(displayName.isEmpty()) {
            nameOfUser.setError("Name Required");
            return;
        }
        FirebaseUser user = null;
        try{
            user = FirebaseAuth.getInstance().getCurrentUser();
            if(user != null && profileImageUrl != null){
                UserProfileChangeRequest profile = new UserProfileChangeRequest
                        .Builder().setDisplayName(displayName).setPhotoUri(Uri.parse(profileImageUrl)).build();

                user.updateProfile(profile).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(getApplicationContext(),"Updated",Toast.LENGTH_LONG).show();
                        finish();


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull  Exception e) {
                        Toast.makeText(getApplicationContext(),"Failed to Update",Toast.LENGTH_LONG).show();
                        finish();
                    }
                });
                finish();
                startActivity(new Intent(getApplicationContext(),Home.class));

            }
        }catch (NullPointerException e){

        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == CHOOSE_IMAGE && resultCode == RESULT_OK && data != null && data.getData() != null){
            uriProfile = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uriProfile);
                profileImage.setImageBitmap(bitmap);

                uploadImageToFirebaseStorage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImageToFirebaseStorage() {

        StorageReference profileImageReference =  FirebaseStorage.getInstance().getReference(
                "profilepics/"+System.currentTimeMillis()+".jpg");
        if(uriProfile != null){
            profileImageReference.putFile(uriProfile)
            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                 profileImageUrl = taskSnapshot.getUploadSessionUri().toString();
                }
            })
            .addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull  Exception e) {

                }
            });
        }
    }

    private void showImageChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Profile image"), CHOOSE_IMAGE);
    }























}