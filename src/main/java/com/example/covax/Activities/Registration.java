package com.example.covax.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.covax.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class Registration extends AppCompatActivity {

    private ImageView userIcon;
    static final int REQUEST_GALLERY = 1;
    Uri selectedImage;
    private boolean flag = false;

    private EditText userName, userEmail, userPassword, userPassword2;
    private Button regButton;
    private TextView clickToLogin;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        userIcon = findViewById(R.id.userIcon);
        userName = findViewById(R.id.userName);
        userEmail = findViewById(R.id.userEmail);
        userPassword = findViewById(R.id.userPassword);
        userPassword2 = findViewById(R.id.userPassword2);
        regButton = findViewById(R.id.regButton);
        clickToLogin = findViewById(R.id.clickToLogin);

        mAuth = FirebaseAuth.getInstance();

        //user wants to set a profile picture
        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { dispatchGetPictureIntent(); }
        });

        //user wants to login to their account
        clickToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginActivity = new Intent (getApplicationContext(), Login.class);
                startActivity(loginActivity);
                finish();
            }
        });

        //user wants to register
        regButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = userName.getText().toString();
                final String email = userEmail.getText().toString();
                final String password = userPassword.getText().toString();
                final String password2 = userPassword2.getText().toString();

                //registration requirements
                if (selectedImage == null || name.isEmpty() || email.isEmpty() || password.isEmpty() || password2.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please fill in all fields!", Toast.LENGTH_SHORT).show();
                }
                else if (!password.equals(password2)) {
                    Toast.makeText(getApplicationContext(), "Passwords do not match!", Toast.LENGTH_SHORT).show();
                }
                else {
                    CreateUserAccount(name, email, password);
                }
            }
        });
    }

    //create user's account through firebase authentication
    private void CreateUserAccount(String name, String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "ACCOUNT CREATED!", Toast.LENGTH_SHORT).show();
                    updateUserInfo(name, selectedImage, mAuth.getCurrentUser());
                }
                else {
                    Toast.makeText(getApplicationContext(), "ACCOUNT CREATION FAILED!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //user's information is constantly updated
    private void updateUserInfo(String name, Uri selectedImage, FirebaseUser currentUser) {
        StorageReference mStorage = FirebaseStorage.getInstance().getReference().child("users_photos");
        StorageReference imageFilePath = mStorage.child(selectedImage.getLastPathSegment());
        imageFilePath.putFile(selectedImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                imageFilePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(name).setPhotoUri(uri).build();

                        currentUser.updateProfile(profileUpdate).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(getApplicationContext(), "REGISTRATION COMPLETE!", Toast.LENGTH_SHORT).show();
                                    updateUI();
                                }
                            }
                        });
                    }
                });
            }
        });
    }

    //updates user interface to home page
    private void updateUI() {
        Intent homeActivity = new Intent(getApplicationContext(), Home.class);
        startActivity(homeActivity);
        finish();
    }

    //user selects a photo
    private void dispatchGetPictureIntent() {
        try {
            Intent getPictureIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(getPictureIntent, REQUEST_GALLERY);
        }
        catch (ActivityNotFoundException e) {
            e.printStackTrace();;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        //accesses gallery app on android
        if (resultCode == RESULT_OK && requestCode == REQUEST_GALLERY) {
            try {
                selectedImage = data.getData();
                InputStream inputStream = getContentResolver().openInputStream(selectedImage);
                Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);
                userIcon.setImageBitmap(imageBitmap);
                flag = true;
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();;
            }
        }
    }
}

